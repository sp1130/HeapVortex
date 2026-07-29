import { useState } from 'react';
import HeapGraph3D from '../components/HeapGraph3D';
import { formatBytes } from '../utils/format';
import { useTelemetrySocket } from '../hooks/useTelemetrySocket';

const STATUS_LABEL = {
  HEALTHY: 'Healthy',
  WARNING: 'Warning — large retained size',
  LEAK: 'Unreachable — likely leak',
};

function NodeDetailPanel({ node, onClose }) {
  if (!node) {
    return (
      <div className="w-72 shrink-0 border-l border-vortex-border bg-vortex-panel/60 p-4 text-sm text-gray-500">
        Click a node in the graph to inspect it — class, shallow/retained size, GC-root status,
        and where it sits in the reference tree.
      </div>
    );
  }

  return (
    <div className="w-72 shrink-0 border-l border-vortex-border bg-vortex-panel/60 p-4 space-y-3">
      <div className="flex items-start justify-between">
        <h3 className="text-sm font-mono text-gray-100 break-all">{node.className}</h3>
        <button onClick={onClose} className="text-gray-500 hover:text-gray-300 text-xs">
          ✕
        </button>
      </div>

      <div
        className="text-xs font-mono px-2 py-1 rounded inline-block"
        style={{
          color:
            node.status === 'LEAK' ? '#ef4444' : node.status === 'WARNING' ? '#eab308' : '#22c55e',
          backgroundColor:
            node.status === 'LEAK'
              ? 'rgba(239,68,68,0.1)'
              : node.status === 'WARNING'
              ? 'rgba(234,179,8,0.1)'
              : 'rgba(34,197,94,0.1)',
        }}
      >
        {STATUS_LABEL[node.status] ?? node.status}
      </div>

      <dl className="text-xs font-mono space-y-2 text-gray-400">
        <div className="flex justify-between">
          <dt>Object ID</dt>
          <dd className="text-gray-200">{node.id}</dd>
        </div>
        <div className="flex justify-between">
          <dt>Shallow size</dt>
          <dd className="text-gray-200">{formatBytes(node.shallowSize)}</dd>
        </div>
        <div className="flex justify-between">
          <dt>Retained size</dt>
          <dd className="text-gray-200">{formatBytes(node.retainedSize)}</dd>
        </div>
        <div className="flex justify-between">
          <dt>GC Root</dt>
          <dd className="text-gray-200">{node.gcRoot ? 'Yes' : 'No'}</dd>
        </div>
        <div className="flex justify-between">
          <dt>Depth from root</dt>
          <dd className="text-gray-200">{node.depth}</dd>
        </div>
      </dl>
    </div>
  );
}

export default function Visualizer() {
  const { graph } = useTelemetrySocket();
  const [selectedNode, setSelectedNode] = useState(null);

  return (
    <div className="flex flex-1 min-h-0">
      <main className="flex-1 min-w-0 p-4">
        <div className="h-full">
          <HeapGraph3D graph={graph} selectedNode={selectedNode} onSelect={setSelectedNode} />
        </div>
      </main>
      <NodeDetailPanel node={selectedNode} onClose={() => setSelectedNode(null)} />
    </div>
  );
}
