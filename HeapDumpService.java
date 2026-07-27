package com.heapvortex.service;

import com.heapvortex.dto.request.HeapDumpRequestDTO;
import com.heapvortex.dto.response.HeapDumpResponseDTO;
import com.heapvortex.jmx.JvmConnectionService;
import com.sun.management.HotSpotDiagnosticMXBean;
import org.springframework.stereotype.Service;

import javax.management.MBeanServerConnection;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.UUID;

@Service
public class HeapDumpService {
    private static final String DUMP_DIR = System.getProperty("java.io.tmpdir") + "/heapvortex-dumps";
    private final JvmConnectionService connectionService;

    public HeapDumpService(JvmConnectionService connectionService) { this.connectionService = connectionService; }

    public HeapDumpResponseDTO generateHeapDump(HeapDumpRequestDTO request) {
        String id = UUID.randomUUID().toString();
        String fileName = "heap-" + id + ".hprof";
        Path directory = Path.of(request.getOutputLocation() == null || request.getOutputLocation().isBlank() ? DUMP_DIR : request.getOutputLocation()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(directory);
            Path dump = directory.resolve(fileName);
            MBeanServerConnection server = connectionService.getActiveMBeanServer();
            HotSpotDiagnosticMXBean bean = ManagementFactory.newPlatformMXBeanProxy(server, "com.sun.management:type=HotSpotDiagnostic", HotSpotDiagnosticMXBean.class);
            bean.dumpHeap(dump.toString(), false);
            return new HeapDumpResponseDTO(id, dump.toString(), Files.size(dump), "COMPLETED", Instant.now());
        } catch (Exception e) {
            return new HeapDumpResponseDTO(id, fileName, 0, "FAILED: " + e.getMessage(), Instant.now());
        }
    }
}
