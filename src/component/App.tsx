import React, { Suspense } from 'react';
import { Canvas } from '@react-three/fiber';
import { OrbitControls, Stars, PerspectiveCamera, Loader } from '@react-three/drei';
import HeapScene from './features/3d-view/components/HeapScene';
import TelemetrySidebar from './features/telemetry/components/TelemetrySidebar';

/**
 * Main Application Component
 * 
 * Layout Strategy:
 * - The 3D Canvas occupies the full background (absolute positioned).
 * - The 2D UI (Sidebar, Overlays) sits on top using z-index.
 */
const App: React.FC = () => {
  return (
    <div className="relative w-screen h-screen bg-[#050505] overflow-hidden text-white font-sans">
      
      {/* 1. 3D Visualization Layer */}
      <div className="absolute inset-0 z-0">
        <Suspense fallback={null}>
          <Canvas shadows>
            <PerspectiveCamera makeDefault position={[0, 5, 15]} fov={75} />
            
            {/* Navigation & Environment */}
            <OrbitControls enableDamping dampingFactor={0.05} />
            <Stars radius={100} depth={50} count={5000} factor={4} saturation={0} fade speed={1} />
            
            {/* Lighting */}
            <ambientLight intensity={0.4} />
            <pointLight position={[10, 10, 10]} intensity={1.5} castShadow />
            <spotLight position={[-10, 20, 10]} angle={0.15} penumbra={1} intensity={2} />

            {/* The Main Heap Scene (Nodes and Edges) */}
            <HeapScene />
          </Canvas>
        </Suspense>
      </div>

      {/* 2. 2D UI Overlay Layer */}
      <div className="relative z-10 flex h-full pointer-events-none">
        
        {/* Left Sidebar: Telemetry & Metrics */}
        <aside className="w-80 h-full bg-black/40 backdrop-blur-md border-r border-white/10 p-6 pointer-events-auto">
          <header className="mb-8">
            <h1 className="text-2xl font-bold tracking-tighter text-blue-400">
              HEAP<span className="text-white">VORTEX</span>
            </h1>
            <p className="text-xs text-gray-400 uppercase tracking-widest mt-1">
              3D JVM Memory Profiler
            </p>
          </header>

          <TelemetrySidebar />
        </aside>

        {/* Main Content Area: Status Overlays */}
        <main className="flex-1 p-6 flex flex-col justify-between">
          <div className="flex justify-end space-x-4 pointer-events-auto">
            <div className="bg-green-500/20 border border-green-500/50 px-3 py-1 rounded-full text-xs font-medium text-green-400 flex items-center">
              <span className="w-2 h-2 bg-green-500 rounded-full mr-2 animate-pulse" />
              Agent Connected: PID 12408
            </div>
          </div>

          {/* Bottom HUD: Quick Stats */}
          <div className="bg-black/40 backdrop-blur-sm border border-white/10 p-4 rounded-xl self-center pointer-events-auto">
            <div className="flex space-x-8">
              <StatItem label="Objects" value="12,402" />
              <StatItem label="Heap Used" value="412 MB" />
              <StatItem label="GC Pauses" value="12ms" />
            </div>
          </div>
        </main>
      </div>

      {/* 3. Loading Indicator */}
      <Loader />
    </div>
  );
};

/**
 * Reusable Stat Component for the HUD
 */
const StatItem: React.FC<{ label: string; value: string }> = ({ label, value }) => (
  <div className="text-center">
    <div className="text-[10px] text-gray-400 uppercase font-bold tracking-wider">{label}</div>
    <div className="text-xl font-mono font-bold text-blue-300">{value}</div>
  </div>
);

export default App;
