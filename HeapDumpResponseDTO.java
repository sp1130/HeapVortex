package com.heapvortex.dto.response;

import java.time.Instant;

/** Returned after a heap dump has been generated on disk. */
public class HeapDumpResponseDTO {

    private String id;
    private String fileName;
    private long fileSize;
    private String status; // PENDING, COMPLETED, FAILED
    private Instant createdAt;

    public HeapDumpResponseDTO() {}

    public HeapDumpResponseDTO(String id, String fileName, long fileSize, String status, Instant createdAt) {
        this.id = id;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public long getFileSize() { return fileSize; }
    public void setFileSize(long fileSize) { this.fileSize = fileSize; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
