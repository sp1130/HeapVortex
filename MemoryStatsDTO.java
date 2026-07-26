package com.heapvortex.dto.response;

/** JVM memory statistics, nested inside LiveTelemetryDTO and used stand-alone too. */
public class MemoryStatsDTO {

    private long heapUsed;
    private long heapCommitted;
    private long heapMax;
    private long nonHeapUsed;
    private long nonHeapCommitted;
    private double heapUsagePercentage;

    public MemoryStatsDTO() {}

    public MemoryStatsDTO(long heapUsed, long heapCommitted, long heapMax,
                           long nonHeapUsed, long nonHeapCommitted, double heapUsagePercentage) {
        this.heapUsed = heapUsed;
        this.heapCommitted = heapCommitted;
        this.heapMax = heapMax;
        this.nonHeapUsed = nonHeapUsed;
        this.nonHeapCommitted = nonHeapCommitted;
        this.heapUsagePercentage = heapUsagePercentage;
    }

    public long getHeapUsed() { return heapUsed; }
    public void setHeapUsed(long heapUsed) { this.heapUsed = heapUsed; }

    public long getHeapCommitted() { return heapCommitted; }
    public void setHeapCommitted(long heapCommitted) { this.heapCommitted = heapCommitted; }

    public long getHeapMax() { return heapMax; }
    public void setHeapMax(long heapMax) { this.heapMax = heapMax; }

    public long getNonHeapUsed() { return nonHeapUsed; }
    public void setNonHeapUsed(long nonHeapUsed) { this.nonHeapUsed = nonHeapUsed; }

    public long getNonHeapCommitted() { return nonHeapCommitted; }
    public void setNonHeapCommitted(long nonHeapCommitted) { this.nonHeapCommitted = nonHeapCommitted; }

    public double getHeapUsagePercentage() { return heapUsagePercentage; }
    public void setHeapUsagePercentage(double heapUsagePercentage) { this.heapUsagePercentage = heapUsagePercentage; }
}
