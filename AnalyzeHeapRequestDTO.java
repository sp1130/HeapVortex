package com.heapvortex.dto.request;

import jakarta.validation.constraints.NotBlank;

/** Sent by the frontend to ask the backend to analyze a previously generated heap dump. */
public class AnalyzeHeapRequestDTO {

    @NotBlank
    private String heapDumpFile;

    public AnalyzeHeapRequestDTO() {}

    public String getHeapDumpFile() { return heapDumpFile; }
    public void setHeapDumpFile(String heapDumpFile) { this.heapDumpFile = heapDumpFile; }
}
