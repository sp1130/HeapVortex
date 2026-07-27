package com.heapvortex.dto.request;

import jakarta.validation.constraints.NotBlank;

/** Sent by the frontend to ask the backend to generate a .hprof heap dump. */
public class HeapDumpRequestDTO {

    @NotBlank
    private String processId;

    private String outputLocation;

    public HeapDumpRequestDTO() {}

    public String getProcessId() { return processId; }
    public void setProcessId(String processId) { this.processId = processId; }

    public String getOutputLocation() { return outputLocation; }
    public void setOutputLocation(String outputLocation) { this.outputLocation = outputLocation; }
}
