export default function LiveChart({ history, dataKey, label, color, maxValue }) {
  const width = 600;
  const height = 140;
  const padding = 8;

  const values = history.map((h) => h[dataKey] ?? 0);
  const max = maxValue ?? Math.max(1, ...values);

  const points = values.map((v, i) => {
    const x = padding + (i / Math.max(1, values.length - 1)) * (width - padding * 2);
    const y = height - padding - (v / max) * (height - padding * 2);
    return `${x},${y}`;
  });

  const path = points.length > 1 ? `M${points.join(' L')}` : '';
  const areaPath = points.length > 1 ? `${path} L${width - padding},${height - padding} L${padding},${height - padding} Z` : '';
  const latest = values[values.length - 1];

  return (
    <div className="rounded-lg border border-vortex-border bg-vortex-panel/60 p-4">
      <div className="flex items-center justify-between mb-2">
        <span className="text-xs font-mono uppercase tracking-widest text-gray-500">{label}</span>
        <span className="text-sm font-mono tabular-nums" style={{ color }}>
          {latest !== undefined ? latest.toFixed(1) : '—'}
        </span>
      </div>
      <svg viewBox={`0 0 ${width} ${height}`} className="w-full h-28">
        <defs>
          <linearGradient id={`grad-${dataKey}`} x1="0" y1="0" x2="0" y2="1">
            <stop offset="0%" stopColor={color} stopOpacity="0.35" />
            <stop offset="100%" stopColor={color} stopOpacity="0" />
          </linearGradient>
        </defs>
        {areaPath && <path d={areaPath} fill={`url(#grad-${dataKey})`} />}
        {path && <path d={path} fill="none" stroke={color} strokeWidth="1.6" />}
      </svg>
    </div>
  );
}
