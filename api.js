import axios from 'axios';

const api = axios.create({ baseURL: '/' });

// Every REST endpoint now returns ApiResponseDTO { success, message, data },
// so every call here unwraps `.data.data` and surfaces `message` on failure.
async function unwrap(promise) {
  const { data: envelope } = await promise;
  if (!envelope.success) {
    throw new Error(envelope.message || 'Request failed');
  }
  return envelope.data;
}

export const fetchTelemetrySnapshot = () => unwrap(api.get('/jmx/telemetry'));

export const fetchHeapGraph = () => unwrap(api.get('/heap/graph'));

export const fetchSuspects = (limit = 10) =>
  unwrap(api.get('/heap/suspects', { params: { limit } }));

export const fetchJvmProcesses = () => unwrap(api.get('/jvm/processes'));

export const connectJvm = (payload) => unwrap(api.post('/jvm/connect', payload));

export const generateHeapDump = (payload) => unwrap(api.post('/heap/dump', payload));

export const analyzeHeapDump = (payload) => unwrap(api.post('/heap/analyze', payload));

export default api;
