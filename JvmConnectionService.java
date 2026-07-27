package com.heapvortex.jmx;

import com.heapvortex.dto.request.ConnectJvmRequestDTO;
import com.heapvortex.dto.response.JvmProcessDTO;
import com.sun.tools.attach.VirtualMachine;
import org.springframework.stereotype.Service;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JvmConnectionService {
    private final Map<String, TargetConnection> connections = new ConcurrentHashMap<>();
    private volatile String activeProcessId;

    public JvmProcessDTO connect(ConnectJvmRequestDTO request) throws Exception {
        String pid = request.getProcessId();
        if (pid == null || pid.isBlank()) throw new IllegalArgumentException("processId is required");

        if (isCurrentJvm(pid) && request.getHost() == null) {
            activeProcessId = pid;
            return new JvmProcessDTO(pid, "heapvortex-backend", "HeapVortex Backend (self)", "local", true);
        }

        if (request.getHost() != null && request.getPort() != null) {
            String url = String.format("service:jmx:rmi:///jndi/rmi://%s:%d/jmxrmi", request.getHost(), request.getPort());
            JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(url), credentials(request));
            connections.compute(pid, (k, old) -> {
                closeQuietly(old);
                return new TargetConnection(pid, connector, null);
            });
            activeProcessId = pid;
            return new JvmProcessDTO(pid, "remote-jvm", request.getHost() + ":" + request.getPort(), url, true);
        }

        VirtualMachine vm = VirtualMachine.attach(pid);
        try {
            Properties agentProps = vm.getAgentProperties();
            String address = agentProps.getProperty("com.sun.management.jmxremote.localConnectorAddress");
            if (address == null) {
                address = vm.startLocalManagementAgent();
            }
            JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(address));
            VirtualMachine attachedVm = vm;
            connections.compute(pid, (k, old) -> {
                closeQuietly(old);
                return new TargetConnection(pid, connector, attachedVm);
            });
            activeProcessId = pid;
            return new JvmProcessDTO(pid, vm.getSystemProperties().getProperty("sun.java.command", "java"),
                    "Local JVM " + pid, address, true);
        } catch (Exception e) {
            try { vm.detach(); } catch (Exception ignored) { }
            throw e;
        }
    }

    public MBeanServerConnection getActiveMBeanServer() throws IOException {
        String pid = activeProcessId;
        if (pid == null) return ManagementFactory.getPlatformMBeanServer();
        TargetConnection connection = connections.get(pid);
        if (connection == null) {
            if (isCurrentJvm(pid)) return ManagementFactory.getPlatformMBeanServer();
            throw new IllegalStateException("No active JVM connection for process " + pid);
        }
        return connection.connector().getMBeanServerConnection();
    }

    public String getActiveProcessId() { return activeProcessId; }

    public boolean isCurrentJvm(String pid) {
        String self = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        return self.equals(pid);
    }

    public void disconnect(String pid) {
        TargetConnection connection = connections.remove(pid);
        closeQuietly(connection);
        if (pid != null && pid.equals(activeProcessId)) activeProcessId = null;
    }

    private Map<String, ?> credentials(ConnectJvmRequestDTO request) {
        if (request.getUsername() == null || request.getUsername().isBlank()) return Map.of();
        return Map.of(JMXConnector.CREDENTIALS, new String[]{request.getUsername(), request.getPassword() == null ? "" : request.getPassword()});
    }

    private void closeQuietly(TargetConnection c) {
        if (c == null) return;
        try { c.connector().close(); } catch (Exception ignored) { }
        try { if (c.vm() != null) c.vm().detach(); } catch (Exception ignored) { }
    }

    private record TargetConnection(String pid, JMXConnector connector, VirtualMachine vm) { }
}
