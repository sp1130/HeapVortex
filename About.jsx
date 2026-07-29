const STACK = [
  { group: 'Backend', items: ['Java 21', 'Spring Boot', 'JMX', 'Spring WebSocket', 'Spring Actuator'] },
  { group: 'Frontend', items: ['React', 'Vite', 'Three.js', 'React Three Fiber', 'Tailwind CSS'] },
  { group: 'Algorithms', items: ['BFS (GC-root reachability)', 'DFS (retained-size + depth)'] },
];

export default function About() {
  return (
    <main className="flex-1 min-w-0 overflow-y-auto p-6 max-w-2xl space-y-6">
      <div>
        <h1 className="text-lg font-semibold text-gray-100">About HeapVortex</h1>
        <p className="text-sm text-gray-400 mt-2 leading-relaxed">
          HeapVortex is a 3D JVM memory leak profiler. Instead of paging through a wall of
          text in a heap-dump report, it streams live JVM telemetry over a WebSocket and
          renders the object reference graph as an interactive 3D scene — objects unreachable
          from any GC root (real leaks) glow red, large retained-size objects glow amber, and
          everything healthy stays green.
        </p>
      </div>

      <div className="rounded-lg border border-vortex-border bg-vortex-panel/60 p-4 space-y-4">
        <h2 className="text-xs font-mono uppercase tracking-widest text-gray-500">Tech Stack</h2>
        {STACK.map((s) => (
          <div key={s.group}>
            <div className="text-xs text-gray-500 mb-1">{s.group}</div>
            <div className="flex flex-wrap gap-2">
              {s.items.map((item) => (
                <span
                  key={item}
                  className="text-xs font-mono px-2 py-1 rounded bg-black/30 border border-vortex-border text-gray-300"
                >
                  {item}
                </span>
              ))}
            </div>
          </div>
        ))}
      </div>
    </main>
  );
}
