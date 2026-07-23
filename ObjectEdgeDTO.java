package com.heapvortex.dto.response;

/** A single reference edge between two objects in the heap graph. */
public class ObjectEdgeDTO {

    private String source;
    private String target;

    // Visualization-only extra: what kind of reference this is
    private String referenceType; // FIELD, ARRAY_ELEMENT, STATIC

    public ObjectEdgeDTO() {}

    public ObjectEdgeDTO(String source, String target, String referenceType) {
        this.source = source;
        this.target = target;
        this.referenceType = referenceType;
    }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }

    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }
}
