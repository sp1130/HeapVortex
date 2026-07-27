package com.heapvortex.dto.response;

import java.util.List;

/** Summary of running BFS-from-GC-roots + retained-size analysis over a heap graph. */
public class HeapAnalysisDTO {

    /** Human-readable summary of which GC roots were used as BFS starting points. */
    private String gcRoot;

    private long retainedMemory;
    private int leakedObjectCount;
    private List<ObjectNodeDTO> leakedObjects;

    public HeapAnalysisDTO() {}

    public HeapAnalysisDTO(String gcRoot, long retainedMemory, int leakedObjectCount,
                            List<ObjectNodeDTO> leakedObjects) {
        this.gcRoot = gcRoot;
        this.retainedMemory = retainedMemory;
        this.leakedObjectCount = leakedObjectCount;
        this.leakedObjects = leakedObjects;
    }

    public String getGcRoot() { return gcRoot; }
    public void setGcRoot(String gcRoot) { this.gcRoot = gcRoot; }

    public long getRetainedMemory() { return retainedMemory; }
    public void setRetainedMemory(long retainedMemory) { this.retainedMemory = retainedMemory; }

    public int getLeakedObjectCount() { return leakedObjectCount; }
    public void setLeakedObjectCount(int leakedObjectCount) { this.leakedObjectCount = leakedObjectCount; }

    public List<ObjectNodeDTO> getLeakedObjects() { return leakedObjects; }
    public void setLeakedObjects(List<ObjectNodeDTO> leakedObjects) { this.leakedObjects = leakedObjects; }
}
