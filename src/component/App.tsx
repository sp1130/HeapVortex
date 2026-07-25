import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard';
import Visualizer from './pages/Visualizer';
import Settings from './pages/Settings';
import About from './pages/About';
import { useTelemetrySocket } from './hooks/useTelemetrySocket';

export default function App() {
  // A single shared socket connection just for the navbar's LIVE/DISCONNECTED
  // indicator — each page opens (and cleans up) its own socket for data.
  const { connected } = useTelemetrySocket();

  return (
    <div className="h-screen flex flex-col bg-vortex-bg text-gray-200">
      <Navbar connected={connected} />
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/visualizer" element={<Visualizer />} />
        <Route path="/settings" element={<Settings />} />
        <Route path="/about" element={<About />} />
      </Routes>
    </div>
  );
}
