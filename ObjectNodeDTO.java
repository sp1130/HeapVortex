package com.heapvortex.dto.response;

/**
 * A single object node in the JVM heap graph.
 * status/depth are extra fields (beyond the base spec) used to drive the
 * 3D visualizer's color-coding and radial layout — everything else matches
 * the documented DTO exactly.
 */
public class ObjectNodeDTO {

    private String id;
    private String className;
    private long shallowSize;
    private long retainedSize;
    private boolean gcRoot;

    // Visualization-only extras
    private String status; // HEALTHY, WARNING, LEAK
    private int depth;

    public ObjectNodeDTO() {}

    public ObjectNodeDTO(String id, String className, long shallowSize, long retainedSize,
                          boolean gcRoot, String status, int depth) {
        this.id = id;
        this.className = className;
        this.shallowSize = shallowSize;
        this.retainedSize = retainedSize;
        this.gcRoot = gcRoot;
        this.status = status;
        this.depth = depth;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public long getShallowSize() { return shallowSize; }
    public void setShallowSize(long shallowSize) { this.shallowSize = shallowSize; }

    public long getRetainedSize() { return retainedSize; }
    public void setRetainedSize(long retainedSize) { this.retainedSize = retainedSize; }

    public boolean isGcRoot() { return gcRoot; }
    public void setGcRoot(boolean gcRoot) { this.gcRoot = gcRoot; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getDepth() { return depth; }
    public void setDepth(int depth) { this.depth = depth; }
}
