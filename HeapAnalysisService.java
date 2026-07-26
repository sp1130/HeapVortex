package com.heapvortex.service;

import com.heapvortex.dto.response.GraphDTO;
import com.heapvortex.dto.response.HeapAnalysisDTO;
import com.heapvortex.dto.response.ObjectEdgeDTO;
import com.heapvortex.dto.response.ObjectNodeDTO;
import com.heapvortex.util.GraphUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Produces the object graph shown in the 3D visualizer, and the
 * HeapAnalysisDTO summary shown in the leak-detection panel.
 *
 * NOTE: parsing a real .hprof heap dump requires the Eclipse MAT parser
 * (an Eclipse-plugin-only API not published to Maven Central), so this
 * service simulates a structurally realistic heap: a handful of GC roots,
 * a tree of everyday classes, and a small number of deliberately
 * unreachable/growing chains standing in for real leaks. All graph
 * algorithms below (BFS reachability, DFS retained-size, leak ranking)
 * are real and run against whatever ObjectNodeDTO/ObjectEdgeDTO list you
 * feed them — swap this service out for a real MAT-based parser (fed by
 * HeapDumpService's generated .hprof file) without touching GraphUtil,
 * the controllers, or the frontend.
 */
@Service
public class HeapAnalysisService {

    private static final String[] CLASS_POOL = {
            "java.lang.String", "java.util.HashMap$Node", "java.util.ArrayList",
            "java.util.LinkedList$Node", "byte[]", "char[]", "java.util.HashMap",
            "com.heapvortex.service.SessionCache", "java.util.concurrent.ConcurrentHashMap$Node",
            "com.heapvortex.model.UserSession", "java.io.ByteArrayOutputStream",
            "java.util.TreeMap$Entry", "javax.servlet.http.HttpSession",
            "com.heapvortex.listener.EventListener", "java.lang.Thread",
            "java.util.Timer$TimerImpl", "com.heapvortex.cache.ObjectPool"
    };

    private static final String[] GC_ROOT_LABELS = {
            "Thread[main]", "Thread[http-nio-8080-exec-1]", "StaticField:AppCache.INSTANCE",
            "JNIGlobalRef", "Thread[scheduler-1]"
    };

    @Value("${heapvortex.graph.node-count:180}")
    private int nodeCount;

    @Value("${heapvortex.graph.leak-probability:0.12}")
    private double leakProbability;

    private final Random random = new Random();

    public GraphDTO analyzeHeap() {
        List<ObjectNodeDTO> nodes = new ArrayList<>();
        List<ObjectEdgeDTO> edges = new ArrayList<>();
        Map<String, ObjectNodeDTO> nodesById = new HashMap<>();

        // 1. Create GC roots
        List<String> rootIds = new ArrayList<>();
        for (String label : GC_ROOT_LABELS) {
            String id = "root-" + rootIds.size();
            ObjectNodeDTO root = new ObjectNodeDTO(id, label, 32, 0, true, "HEALTHY", 0);
            nodes.add(root);
            nodesById.put(id, root);
            rootIds.add(id);
        }

        // 2. Build a random tree hanging off the roots (the "live", reachable object graph)
        int liveCount = (int) (nodeCount * 0.75);
        List<String> createdIds = new ArrayList<>(rootIds);
        for (int i = 0; i < liveCount; i++) {
            String id = "obj-" + i;
            String parentId = createdIds.get(random.nextInt(createdIds.size()));
            String className = CLASS_POOL[random.nextInt(CLASS_POOL.length)];
            long shallowSize = 16 + random.nextInt(2048);

            ObjectNodeDTO node = new ObjectNodeDTO(id, className, shallowSize, 0, false, "HEALTHY", 0);
            nodes.add(node);
            nodesById.put(id, node);
            edges.add(new ObjectEdgeDTO(parentId, id, random.nextBoolean() ? "FIELD" : "ARRAY_ELEMENT"));
            createdIds.add(id);
        }

        // 3. Build deliberately unreachable chains: objects that reference each other
        //    (so they look "alive" locally) but are never reachable from any GC root —
        //    these are the leaks BFS-from-roots will expose.
        int leakChains = Math.max(1, (int) (nodeCount * leakProbability / 6));
        for (int c = 0; c < leakChains; c++) {
            int chainLength = 4 + random.nextInt(10);
            String prevId = null;
            for (int i = 0; i < chainLength; i++) {
                String id = "leak-" + c + "-" + i;
                String className = CLASS_POOL[random.nextInt(CLASS_POOL.length)];
                long shallowSize = 512 + random.nextInt(8192); // leaked chains tend to be beefier
                ObjectNodeDTO node = new ObjectNodeDTO(id, className, shallowSize, 0, false, "HEALTHY", 0);
                nodes.add(node);
                nodesById.put(id, node);
                if (prevId != null) {
                    edges.add(new ObjectEdgeDTO(prevId, id, "FIELD"));
                }
                prevId = id;
            }
            // close the loop so the chain keeps itself artificially "referenced" —
            // classic leak pattern (e.g. a listener registered but never removed)
            edges.add(new ObjectEdgeDTO(prevId, "leak-" + c + "-0", "FIELD"));
        }

        // 4. Run real graph algorithms
        Map<String, List<String>> adjacency = GraphUtil.buildAdjacency(edges);
        Set<String> reachable = GraphUtil.bfsReachableFromRoots(nodes, adjacency);

        Map<String, Integer> depthMap = new HashMap<>();
        Map<String, Long> retainedMap = new HashMap<>();
        for (String rootId : rootIds) {
            GraphUtil.dfsAssignDepthAndRetainedSize(rootId, adjacency, nodesById, depthMap, retainedMap);
        }

        for (ObjectNodeDTO node : nodes) {
            boolean isReachable = reachable.contains(node.getId());
            int depth = depthMap.getOrDefault(node.getId(), -1);
            long retained = retainedMap.getOrDefault(node.getId(), node.getShallowSize());

            node.setDepth(Math.max(depth, 0));
            node.setRetainedSize(retained);

            if (!node.isGcRoot() && !isReachable) {
                node.setStatus("LEAK");
            } else if (retained > 20_000) {
                node.setStatus("WARNING");
            } else {
                node.setStatus("HEALTHY");
            }
        }

        return new GraphDTO(nodes, edges);
    }

    /** Runs analyzeHeap() and reduces it to the HeapAnalysisDTO summary shape. */
    public HeapAnalysisDTO analyze() {
        GraphDTO graph = analyzeHeap();

        List<ObjectNodeDTO> leaked = graph.getNodes().stream()
                .filter(n -> "LEAK".equals(n.getStatus()))
                .collect(Collectors.toList());

        long totalRetained = graph.getNodes().stream()
                .mapToLong(ObjectNodeDTO::getShallowSize)
                .sum();

        String gcRootSummary = graph.getNodes().stream()
                .filter(ObjectNodeDTO::isGcRoot)
                .map(ObjectNodeDTO::getClassName)
                .collect(Collectors.joining(", "));

        return new HeapAnalysisDTO(gcRootSummary, totalRetained, leaked.size(), leaked);
    }

    public List<ObjectNodeDTO> topSuspects(int limit) {
        return GraphUtil.topRetainedSizeNodes(analyzeHeap().getNodes(), limit);
    }
}
