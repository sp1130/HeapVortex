import { formatBytes, formatPercent } from '../utils/format';

function Card({ label, value, sub, accent }) {
  return (
    <div className="rounded-lg border border-vortex-border bg-vortex-panel/60 p-4 flex flex-col gap-1">
      <span className="text-xs font-mono uppercase tracking-widest text-gray-500">{label}</span>
      <span className="text-2xl font-semibold tabular-nums" style={{ color: accent }}>
        {value}
      </span>
      {sub && <span className="text-xs text-gray-500">{sub}</span>}
    </div>
  );
}

export default function MetricsCards({ telemetry, graph }) {
  const heapPct = telemetry ? telemetry.heapUsagePercentage : 0;

  return (
    <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
      <Card
        label="Heap Used"
        value={telemetry ? formatBytes(telemetry.heapUsed) : '—'}
        sub={telemetry ? `${formatPercent(heapPct)} of ${formatBytes(telemetry.heapMax)}` : ''}
        accent="#22d3ee"
      />
      <Card
        label="CPU Load"
        value={telemetry ? formatPercent(telemetry.cpuUsage) : '—'}
        accent="#7c3aed"
      />
      <Card
        label="Threads"
        value={telemetry ? telemetry.threadCount : '—'}
        sub={telemetry?.gcEvent ? `last GC: ${telemetry.gcEvent.gcName}` : ''}
        accent="#eab308"
      />
      <Card
        label="Leaks Detected"
        value={graph ? graph.leakCount : '—'}
        sub={graph ? `${graph.nodes?.length ?? 0} objects tracked` : ''}
        accent={graph?.leakCount > 0 ? '#ef4444' : '#22c55e'}
      />
    </div>
  );
}
