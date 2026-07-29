import { useEffect, useRef, useState, useCallback } from 'react';

const WS_URL = import.meta.env.VITE_WS_URL || `${window.location.protocol === 'https:' ? 'wss' : 'ws'}://${window.location.host}/websocket`;
const MAX_HISTORY = 60;

function deriveGraphStats(graph) {
  const nodes = graph?.nodes ?? [];
  const leakCount = nodes.filter((n) => n.status === 'LEAK').length;
  const totalRetainedSize = nodes.reduce((sum, n) => sum + (n.retainedSize ?? 0), 0);
  return { ...graph, leakCount, totalRetainedSize };
}

function flattenTelemetry(dto) {
  return {
    heapUsed: dto.memory?.heapUsed ?? 0,
    heapCommitted: dto.memory?.heapCommitted ?? 0,
    heapMax: dto.memory?.heapMax ?? 1,
    nonHeapUsed: dto.memory?.nonHeapUsed ?? 0,
    heapUsagePercentage: dto.memory?.heapUsagePercentage ?? 0,
    cpuUsage: dto.cpuUsage ?? 0,
    threadCount: dto.threadCount ?? 0,
    uptime: dto.uptime ?? 0,
    gcEvent: dto.gcEvent ?? null,
  };
}

export function useTelemetrySocket() {
  const [connected, setConnected] = useState(false);
  const [telemetry, setTelemetry] = useState(null);
  const [history, setHistory] = useState([]);
  const [graph, setGraph] = useState({ nodes: [], edges: [], leakCount: 0, totalRetainedSize: 0 });
  const socketRef = useRef(null);
  const reconnectTimer = useRef(null);
  const stoppedRef = useRef(false);

  const connect = useCallback(() => {
    if (stoppedRef.current) return;
    const socket = new WebSocket(WS_URL);
    socketRef.current = socket;
    socket.onopen = () => setConnected(true);
    socket.onmessage = (event) => {
      try {
        const { type, data } = JSON.parse(event.data);
        if (type === 'TELEMETRY') {
          const flat = flattenTelemetry(data);
          setTelemetry(flat);
          setHistory((prev) => {
            const next = [...prev, { ...flat, t: Date.now() }];
            return next.length > MAX_HISTORY ? next.slice(-MAX_HISTORY) : next;
          });
        } else if (type === 'GRAPH') {
          setGraph(deriveGraphStats(data));
        }
      } catch { /* ignore malformed frames */ }
    };
    socket.onclose = () => {
      setConnected(false);
      if (!stoppedRef.current) reconnectTimer.current = setTimeout(connect, 2000);
    };
    socket.onerror = () => socket.close();
  }, []);

  useEffect(() => {
    stoppedRef.current = false;
    connect();
    return () => {
      stoppedRef.current = true;
      clearTimeout(reconnectTimer.current);
      socketRef.current?.close();
    };
  }, [connect]);

  return { connected, telemetry, history, graph };
}
