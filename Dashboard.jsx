import MetricsCards from '../components/MetricsCards';
import LiveChart from '../components/LiveChart';
import Sidebar from '../components/Sidebar';
import HeapGraph3D from '../components/HeapGraph3D';
import { useTelemetrySocket } from '../hooks/useTelemetrySocket';

export default function Dashboard() {
  const { connected, telemetry, history, graph } = useTelemetrySocket();

  return (
    <div className="flex flex-1 min-h-0">
      <Sidebar graph={graph} />

      <main className="flex-1 min-w-0 overflow-y-auto p-6 space-y-6">
        <div>
          <h1 className="text-lg font-semibold text-gray-100">Dashboard</h1>
          <p className="text-sm text-gray-500">
            {connected ? 'Streaming live JVM telemetry.' : 'Connecting to backend…'}
          </p>
        </div>

        <MetricsCards telemetry={telemetry} graph={graph} />

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <LiveChart history={history} dataKey="heapUsed" label="Heap Used (bytes)" color="#22d3ee" />
          <LiveChart
            history={history}
            dataKey="cpuUsage"
            label="CPU Load (%)"
            color="#7c3aed"
            maxValue={100}
          />
        </div>

        <div>
          <h2 className="text-xs font-mono uppercase tracking-widest text-gray-500 mb-2">
            Heap Preview
          </h2>
          <div className="h-80">
            <HeapGraph3D graph={graph} selectedNode={null} onSelect={() => {}} />
          </div>
        </div>
      </main>
    </div>
  );
}
