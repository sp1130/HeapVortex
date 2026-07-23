package com.heapvortex.controller;

import com.heapvortex.dto.response.ApiResponseDTO;
import com.heapvortex.dto.response.LiveTelemetryDTO;
import com.heapvortex.jmx.JmxTelemetryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jmx")
public class JmxController {

    private final JmxTelemetryService jmxTelemetryService;

    public JmxController(JmxTelemetryService jmxTelemetryService) {
        this.jmxTelemetryService = jmxTelemetryService;
    }

    @GetMapping("/telemetry")
    public ApiResponseDTO<LiveTelemetryDTO> telemetry() {
        return ApiResponseDTO.ok(jmxTelemetryService.getTelemetry());
    }
}
