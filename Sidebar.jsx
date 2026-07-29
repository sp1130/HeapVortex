import { formatBytes } from '../utils/format';

export default function Sidebar({ graph }) {
  const nodes = graph?.nodes ?? [];
  const suspects = [...nodes]
    .sort((a, b) => b.retainedSize - a.retainedSize)
    .slice(0, 8);

  return (
    <aside className="w-64 shrink-0 border-r border-vortex-border bg-vortex-panel/60 p-4 overflow-y-auto">
      <h3 className="text-xs font-mono uppercase tracking-widest text-gray-500 mb-3">
        Top Suspects
      </h3>
      <ul className="space-y-2">
        {suspects.length === 0 && (
          <li className="text-sm text-gray-600 italic">Waiting for heap data…</li>
        )}
        {suspects.map((node) => (
          <li
            key={node.id}
            className="rounded-md border border-vortex-border bg-black/20 px-3 py-2 text-xs font-mono"
          >
            <div className="flex items-center justify-between gap-2">
              <span className="truncate text-gray-300">{node.className}</span>
              <span
                className={`h-1.5 w-1.5 rounded-full shrink-0 ${
                  node.status === 'LEAK'
                    ? 'bg-vortex-leak'
                    : node.status === 'WARNING'
                    ? 'bg-vortex-warning'
                    : 'bg-vortex-healthy'
                }`}
              />
            </div>
            <div className="text-gray-500 mt-0.5">{formatBytes(node.retainedSize)}</div>
          </li>
        ))}
      </ul>
    </aside>
  );
}
