package com.heapvortex.backend.util;

import com.heapvortex.backend.dto.response.ObjectEdgeDTO;
import com.heapvortex.backend.dto.response.ObjectNodeDTO;

import java.util.*;

/**
 * Real graph algorithms operating on the heap object graph:
 * - BFS from GC roots to find every node reachable from a root (i.e. NOT garbage).
 * - Any node NOT reachable from a GC root but still present in the dump is
 *   flagged as an unreachable / leaked object (a "lost" object still occupying heap).
 * - DFS-based retained-size accumulation along the first-touch parent tree
 *   (a simplified approximation of MAT's dominator tree).
 */
public class GraphUtil {

    /** Builds an adjacency list source -> targets from the edge list. */
    public static Map<String, List<String>> buildAdjacency(List<ObjectEdgeDTO> edges) {
        Map<String, List<String>> adjacency = new HashMap<>();
        for (ObjectEdgeDTO edge : edges) {
            adjacency.computeIfAbsent(edge.getSource(), k -> new ArrayList<>()).add(edge.getTarget());
        }
        return adjacency;
    }

    /** BFS from all GC root nodes. Returns the set of node IDs reachable from a root. */
    public static Set<String> bfsReachableFromRoots(List<ObjectNodeDTO> nodes, Map<String, List<String>> adjacency) {
        Set<String> reachable = new HashSet<>();
        Deque<String> queue = new ArrayDeque<>();

        for (ObjectNodeDTO node : nodes) {
            if (node.isGcRoot()) {
                queue.add(node.getId());
                reachable.add(node.getId());
            }
        }

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String neighbor : adjacency.getOrDefault(current, Collections.emptyList())) {
                if (reachable.add(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }
        return reachable;
    }

    /**
     * DFS from a given root, computing a depth map and a retained-size map.
     * Retained size of a node = its own shallow size + the shallow size of every
     * descendant reached exclusively through it in this DFS tree.
     */
    public static void dfsAssignDepthAndRetainedSize(
            String rootId,
            Map<String, List<String>> adjacency,
            Map<String, ObjectNodeDTO> nodesById,
            Map<String, Integer> depthOut,
            Map<String, Long> retainedSizeOut) {

        Set<String> visited = new HashSet<>();
        dfsVisit(rootId, 0, adjacency, nodesById, depthOut, retainedSizeOut, visited);
    }

    private static long dfsVisit(
            String nodeId,
            int depth,
            Map<String, List<String>> adjacency,
            Map<String, ObjectNodeDTO> nodesById,
            Map<String, Integer> depthOut,
            Map<String, Long> retainedSizeOut,
            Set<String> visited) {

        if (!visited.add(nodeId)) {
            return 0L;
        }

        depthOut.merge(nodeId, depth, Math::min);
        ObjectNodeDTO node = nodesById.get(nodeId);
        long total = node != null ? node.getShallowSize() : 0L;

        for (String child : adjacency.getOrDefault(nodeId, Collections.emptyList())) {
            total += dfsVisit(child, depth + 1, adjacency, nodesById, depthOut, retainedSizeOut, visited);
        }

        retainedSizeOut.merge(nodeId, total, Long::sum);
        return total;
    }

    /** Finds the top N nodes by retained size — the classic "biggest suspects" list. */
    public static List<ObjectNodeDTO> topRetainedSizeNodes(List<ObjectNodeDTO> nodes, int limit) {
        List<ObjectNodeDTO> sorted = new ArrayList<>(nodes);
        sorted.sort((a, b) -> Long.compare(b.getRetainedSize(), a.getRetainedSize()));
        return sorted.subList(0, Math.min(limit, sorted.size()));
    }
}
