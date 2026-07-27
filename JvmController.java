package com.heapvortex.controller;

import com.heapvortex.dto.request.ConnectJvmRequestDTO;
import com.heapvortex.dto.response.ApiResponseDTO;
import com.heapvortex.dto.response.JvmProcessDTO;
import com.heapvortex.service.JvmProcessService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jvm")
public class JvmController {
    private final JvmProcessService service;
    public JvmController(JvmProcessService service) { this.service = service; }

    @GetMapping("/processes")
    public ApiResponseDTO<List<JvmProcessDTO>> processes() { return ApiResponseDTO.ok(service.listProcesses()); }

    @PostMapping("/connect")
    public ApiResponseDTO<JvmProcessDTO> connect(@Valid @RequestBody ConnectJvmRequestDTO request) throws Exception {
        return ApiResponseDTO.ok("JVM connected", service.connect(request));
    }
}
