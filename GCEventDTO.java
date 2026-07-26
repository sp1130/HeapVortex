package com.heapvortex.dto.response;

/** One garbage-collection event/cycle, as reported by a GarbageCollectorMXBean. */
public class GCEventDTO {

    private String gcName;
    private String action;
    private long duration;
    private long timestamp;

    public GCEventDTO() {}

    public GCEventDTO(String gcName, String action, long duration, long timestamp) {
        this.gcName = gcName;
        this.action = action;
        this.duration = duration;
        this.timestamp = timestamp;
    }

    public String getGcName() { return gcName; }
    public void setGcName(String gcName) { this.gcName = gcName; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public long getDuration() { return duration; }
    public void setDuration(long duration) { this.duration = duration; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
