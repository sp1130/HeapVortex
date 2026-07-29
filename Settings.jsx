import { useEffect, useState } from 'react';
import { fetchJvmProcesses, connectJvm, generateHeapDump } from '../services/api';
import { formatBytes } from '../utils/format';

function Section({ title, children }) {
  return (
    <div className="rounded-lg border border-vortex-border bg-vortex-panel/60 p-4 space-y-3">
      <h2 className="text-xs font-mono uppercase tracking-widest text-gray-500">{title}</h2>
      {children}
    </div>
  );
}

export default function Settings() {
  const [processes, setProcesses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [connectStatus, setConnectStatus] = useState(null);
  const [dumpStatus, setDumpStatus] = useState(null);
  const [dumpBusy, setDumpBusy] = useState(false);

  useEffect(() => {
    fetchJvmProcesses()
      .then(setProcesses)
      .catch(() => setProcesses([]))
      .finally(() => setLoading(false));
  }, []);

  const handleConnect = async (processId) => {
    setConnectStatus('connecting');
    try {
      const result = await connectJvm({ processId });
      setConnectStatus(`connected: ${result.jmxUrl}`);
    } catch (err) {
      setConnectStatus(`failed: ${err.message}`);
    }
  };

  const handleDump = async () => {
    setDumpBusy(true);
    setDumpStatus(null);
    try {
      const result = await generateHeapDump({ processId: processes[0]?.processId ?? 'self' });
      setDumpStatus(
        result.status === 'COMPLETED'
          ? `Saved ${result.fileName} (${formatBytes(result.fileSize)})`
          : `Failed: ${result.status}`
      );
    } catch (err) {
      setDumpStatus(`Error: ${err.message}`);
    } finally {
      setDumpBusy(false);
    }
  };

  return (
    <main className="flex-1 min-w-0 overflow-y-auto p-6 space-y-6 max-w-2xl">
      <div>
        <h1 className="text-lg font-semibold text-gray-100">Settings</h1>
        <p className="text-sm text-gray-500">Manage JVM connections and heap dumps.</p>
      </div>

      <Section title="Attachable JVM Processes">
        {loading && <p className="text-sm text-gray-500">Loading…</p>}
        {!loading && processes.length === 0 && (
          <p className="text-sm text-gray-500">No processes found.</p>
        )}
        <ul className="space-y-2">
          {processes.map((p) => (
            <li
              key={p.processId}
              className="flex items-center justify-between rounded-md border border-vortex-border bg-black/20 px-3 py-2 text-sm"
            >
              <div>
                <div className="text-gray-200 font-mono">{p.displayName}</div>
                <div className="text-xs text-gray-500">pid {p.processId}</div>
              </div>
              <button
                onClick={() => handleConnect(p.processId)}
                className="text-xs font-mono px-3 py-1 rounded-md bg-vortex-accent/20 text-vortex-accent2 hover:bg-vortex-accent/30 transition-colors"
              >
                Connect
              </button>
            </li>
          ))}
        </ul>
        {connectStatus && (
          <p className="text-xs font-mono text-gray-400 break-all">{connectStatus}</p>
        )}
      </Section>

      <Section title="Heap Dump">
        <p className="text-sm text-gray-400">
          Generates a real .hprof file on the backend host via HotSpotDiagnosticMXBean.
        </p>
        <button
          onClick={handleDump}
          disabled={dumpBusy}
          className="text-xs font-mono px-3 py-1.5 rounded-md bg-vortex-accent text-white hover:bg-vortex-accent/90 transition-colors disabled:opacity-50"
        >
          {dumpBusy ? 'Generating…' : 'Generate Heap Dump'}
        </button>
        {dumpStatus && <p className="text-xs font-mono text-gray-400">{dumpStatus}</p>}
      </Section>
    </main>
  );
}
