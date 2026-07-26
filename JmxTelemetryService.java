package com.heapvortex.jmx;

import com.heapvortex.dto.response.GCEventDTO;
import com.heapvortex.dto.response.LiveTelemetryDTO;
import com.heapvortex.dto.response.MemoryStatsDTO;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Service;

import javax.management.MBeanServerConnection;
import java.lang.management.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class JmxTelemetryService {
    private final JvmConnectionService connectionService;

    public JmxTelemetryService(JvmConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public LiveTelemetryDTO getTelemetry() {
        try {
            MBeanServerConnection server = connectionService.getActiveMBeanServer();
            MemoryMXBean memory = ManagementFactory.newPlatformMXBeanProxy(server, ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
            ThreadMXBean threads = ManagementFactory.newPlatformMXBeanProxy(server, ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
            RuntimeMXBean runtime = ManagementFactory.newPlatformMXBeanProxy(server, ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
            OperatingSystemMXBean os = ManagementFactory.newPlatformMXBeanProxy(server, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
            List<GarbageCollectorMXBean> gcs = new ArrayList<>();
            gcs.addAll(ManagementFactory.getPlatformMXBeans(server, GarbageCollectorMXBean.class));
            MemoryUsage heap = memory.getHeapMemoryUsage();
            MemoryUsage nonHeap = memory.getNonHeapMemoryUsage();
            long max = heap.getMax() > 0 ? heap.getMax() : heap.getCommitted();
            double pct = max > 0 ? heap.getUsed() * 100.0 / max : 0;
            GarbageCollectorMXBean latest = gcs.stream().max(java.util.Comparator.comparingLong(GarbageCollectorMXBean::getCollectionTime)).orElse(null);
            GCEventDTO gc = latest == null ? new GCEventDTO("—", "NONE", 0, System.currentTimeMillis()) :
                    new GCEventDTO(latest.getName(), "COLLECTION", latest.getCollectionTime(), System.currentTimeMillis());
            double cpu = Math.max(0, os.getProcessCpuLoad()) * 100.0;
            return new LiveTelemetryDTO(new MemoryStatsDTO(heap.getUsed(), heap.getCommitted(), max, nonHeap.getUsed(), nonHeap.getCommitted(), pct), gc, cpu, threads.getThreadCount(), runtime.getUptime());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to read JVM telemetry: " + e.getMessage(), e);
        }
    }
}
