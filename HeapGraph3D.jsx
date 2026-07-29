import { useMemo, useRef, useState } from 'react';
import { Canvas } from '@react-three/fiber';
import { OrbitControls, Line, Html } from '@react-three/drei';
import * as THREE from 'three';

const STATUS_COLOR = {
  HEALTHY: '#22c55e',
  WARNING: '#eab308',
  LEAK: '#ef4444',
};

// Deterministic layout: spread nodes on rings by depth so the graph
// stays visually stable between refreshes instead of jittering.
function layoutNodes(nodes) {
  const byDepth = new Map();
  for (const node of nodes) {
    const list = byDepth.get(node.depth) ?? [];
    list.push(node);
    byDepth.set(node.depth, list);
  }

  const positions = new Map();
  for (const [depth, list] of byDepth) {
    const radius = depth === 0 ? 0 : 2.2 * depth;
    list.forEach((node, i) => {
      const angle = (i / Math.max(1, list.length)) * Math.PI * 2;
      const y = (Math.sin(i * 12.9898 + depth) * 0.5) * (depth === 0 ? 0 : 1);
      positions.set(node.id, [
        Math.cos(angle) * radius,
        y + depth * 0.15,
        Math.sin(angle) * radius,
      ]);
    });
  }
  return positions;
}

function nodeRadius(node) {
  const size = Math.log10(Math.max(10, node.retainedSize));
  return 0.08 + size * 0.06;
}

function Node({ node, position, onSelect, isSelected }) {
  const meshRef = useRef();
  const [hovered, setHovered] = useState(false);
  const color = STATUS_COLOR[node.status] ?? STATUS_COLOR.HEALTHY;

  return (
    <group position={position}>
      <mesh
        ref={meshRef}
        onClick={(e) => {
          e.stopPropagation();
          onSelect(node);
        }}
        onPointerOver={(e) => {
          e.stopPropagation();
          setHovered(true);
        }}
        onPointerOut={() => setHovered(false)}
      >
        <sphereGeometry args={[nodeRadius(node), 16, 16]} />
        <meshStandardMaterial
          color={color}
          emissive={color}
          emissiveIntensity={isSelected ? 1.2 : node.status === 'LEAK' ? 0.6 : 0.15}
          roughness={0.4}
        />
      </mesh>
      {(hovered || isSelected) && (
        <Html distanceFactor={10} position={[0, nodeRadius(node) + 0.15, 0]}>
          <div className="pointer-events-none whitespace-nowrap rounded bg-black/85 border border-vortex-border px-2 py-1 text-[10px] font-mono text-gray-200">
            {node.className}
          </div>
        </Html>
      )}
    </group>
  );
}

function Edges({ edges, positions }) {
  const lines = useMemo(() => {
    return edges
      .map((edge) => {
        const from = positions.get(edge.source);
        const to = positions.get(edge.target);
        if (!from || !to) return null;
        return { key: `${edge.source}-${edge.target}`, points: [from, to] };
      })
      .filter(Boolean);
  }, [edges, positions]);

  return (
    <>
      {lines.map((line) => (
        <Line key={line.key} points={line.points} color="#334155" lineWidth={0.6} transparent opacity={0.5} />
      ))}
    </>
  );
}

export default function HeapGraph3D({ graph, selectedNode, onSelect }) {
  const positions = useMemo(() => layoutNodes(graph.nodes ?? []), [graph.nodes]);

  return (
    <div className="w-full h-full rounded-lg border border-vortex-border bg-black/40 overflow-hidden">
      <Canvas camera={{ position: [10, 8, 10], fov: 50 }} onPointerMissed={() => onSelect(null)}>
        <ambientLight intensity={0.6} />
        <pointLight position={[10, 10, 10]} intensity={1.2} />
        <pointLight position={[-10, -5, -10]} intensity={0.4} color="#7c3aed" />

        <Edges edges={graph.edges ?? []} positions={positions} />

        {(graph.nodes ?? []).map((node) => (
          <Node
            key={node.id}
            node={node}
            position={positions.get(node.id) ?? [0, 0, 0]}
            onSelect={onSelect}
            isSelected={selectedNode?.id === node.id}
          />
        ))}

        <OrbitControls enableDamping dampingFactor={0.08} makeDefault />
      </Canvas>
    </div>
  );
}
