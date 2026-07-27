package com.heapvortex.dto.response;

/** The payload broadcast over /websocket roughly once per second. */
public class LiveTelemetryDTO {

    private MemoryStatsDTO memory;
    private GCEventDTO gcEvent;
    private double cpuUsage;
    private int threadCount;
    private long uptime;

    public LiveTelemetryDTO() {}

    public LiveTelemetryDTO(MemoryStatsDTO memory, GCEventDTO gcEvent, double cpuUsage,
                             int threadCount, long uptime) {
        this.memory = memory;
        this.gcEvent = gcEvent;
        this.cpuUsage = cpuUsage;
        this.threadCount = threadCount;
        this.uptime = uptime;
    }

    public MemoryStatsDTO getMemory() { return memory; }
    public void setMemory(MemoryStatsDTO memory) { this.memory = memory; }

    public GCEventDTO getGcEvent() { return gcEvent; }
    public void setGcEvent(GCEventDTO gcEvent) { this.gcEvent = gcEvent; }

    public double getCpuUsage() { return cpuUsage; }
    public void setCpuUsage(double cpuUsage) { this.cpuUsage = cpuUsage; }

    public int getThreadCount() { return threadCount; }
    public void setThreadCount(int threadCount) { this.threadCount = threadCount; }

    public long getUptime() { return uptime; }
    public void setUptime(long uptime) { this.uptime = uptime; }
}
