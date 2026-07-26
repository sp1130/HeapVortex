package com.heapvortex.service;

import com.heapvortex.dto.request.ConnectJvmRequestDTO;
import com.heapvortex.dto.response.JvmProcessDTO;
import com.heapvortex.jmx.JvmConnectionService;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class JvmProcessService {
    private final JvmConnectionService connectionService;

    public JvmProcessService(JvmConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public List<JvmProcessDTO> listProcesses() {
        List<JvmProcessDTO> result = new ArrayList<>();
        String selfPid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        try {
            for (VirtualMachineDescriptor d : VirtualMachine.list()) {
                String display = d.displayName() == null || d.displayName().isBlank() ? "Java process " + d.id() : d.displayName();
                result.add(new JvmProcessDTO(d.id(), "java", display, "", d.id().equals(selfPid)));
            }
        } catch (Exception ignored) { }
        if (result.stream().noneMatch(p -> p.getProcessId().equals(selfPid))) {
            result.add(new JvmProcessDTO(selfPid, "java", "HeapVortex Backend (self)", "local", true));
        }
        return result.stream().sorted(Comparator.comparing(JvmProcessDTO::getProcessId)).toList();
    }

    public JvmProcessDTO connect(ConnectJvmRequestDTO request) throws Exception {
        return connectionService.connect(request);
    }
}
