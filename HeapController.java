package com.heapvortex.controller;

import com.heapvortex.dto.request.AnalyzeHeapRequestDTO;
import com.heapvortex.dto.request.HeapDumpRequestDTO;
import com.heapvortex.dto.response.*;
import com.heapvortex.service.HeapAnalysisService;
import com.heapvortex.service.HeapDumpService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/heap")
public class HeapController {

    private final HeapAnalysisService heapAnalysisService;
    private final HeapDumpService heapDumpService;

    public HeapController(HeapAnalysisService heapAnalysisService, HeapDumpService heapDumpService) {
        this.heapAnalysisService = heapAnalysisService;
        this.heapDumpService = heapDumpService;
    }

    @GetMapping("/graph")
    public ApiResponseDTO<GraphDTO> graph() {
        return ApiResponseDTO.ok(heapAnalysisService.analyzeHeap());
    }

    @GetMapping("/suspects")
    public ApiResponseDTO<List<ObjectNodeDTO>> suspects(@RequestParam(defaultValue = "10") int limit) {
        return ApiResponseDTO.ok(heapAnalysisService.topSuspects(limit));
    }

    @PostMapping("/dump")
    public ApiResponseDTO<HeapDumpResponseDTO> dump(@Valid @RequestBody HeapDumpRequestDTO request) {
        HeapDumpResponseDTO result = heapDumpService.generateHeapDump(request);
        return "COMPLETED".equals(result.getStatus())
                ? ApiResponseDTO.ok("Heap dump generated", result)
                : ApiResponseDTO.error("Heap dump generation failed");
    }

    @PostMapping("/analyze")
    public ApiResponseDTO<HeapAnalysisDTO> analyze(@Valid @RequestBody AnalyzeHeapRequestDTO request) {
        // request.getHeapDumpFile() would be passed to a real MAT-based parser here;
        // the simulated analyzer is used for now (see HeapAnalysisService for why).
        return ApiResponseDTO.ok(heapAnalysisService.analyze());
    }
}
