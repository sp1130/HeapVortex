package com.heapvortex.dto.response;

import java.util.List;

/** The complete object graph sent to the 3D visualizer. */
public class GraphDTO {

    private List<ObjectNodeDTO> nodes;
    private List<ObjectEdgeDTO> edges;

    public GraphDTO() {}

    public GraphDTO(List<ObjectNodeDTO> nodes, List<ObjectEdgeDTO> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public List<ObjectNodeDTO> getNodes() { return nodes; }
    public void setNodes(List<ObjectNodeDTO> nodes) { this.nodes = nodes; }

    public List<ObjectEdgeDTO> getEdges() { return edges; }
    public void setEdges(List<ObjectEdgeDTO> edges) { this.edges = edges; }
}
