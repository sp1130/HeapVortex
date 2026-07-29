import { NavLink } from 'react-router-dom';

const links = [
  { to: '/', label: 'Dashboard' },
  { to: '/visualizer', label: 'Visualizer' },
  { to: '/settings', label: 'Settings' },
  { to: '/about', label: 'About' },
];

export default function Navbar({ connected }) {
  return (
    <header className="h-14 border-b border-vortex-border bg-vortex-panel/80 backdrop-blur flex items-center px-5 gap-8 shrink-0">
      <div className="flex items-center gap-2">
        <svg width="22" height="22" viewBox="0 0 24 24" fill="none">
          <path
            d="M12 2C7 2 4 6 4 10c0 5 4 8 8 12 4-4 8-7 8-12 0-4-3-8-8-8Z"
            stroke="#7c3aed"
            strokeWidth="1.4"
            fill="none"
          />
          <path
            d="M12 6c-2.5 0-4.2 2-4.2 4.2 0 2.8 2.2 4.4 4.2 6.4 2-2 4.2-3.6 4.2-6.4C16.2 8 14.5 6 12 6Z"
            fill="#22d3ee"
            fillOpacity="0.25"
          />
          <circle cx="12" cy="10.5" r="1.6" fill="#22d3ee" />
        </svg>
        <span className="font-mono text-sm font-semibold tracking-wide text-gray-100">
          HeapVortex
        </span>
      </div>

      <nav className="flex items-center gap-1">
        {links.map((link) => (
          <NavLink
            key={link.to}
            to={link.to}
            end={link.to === '/'}
            className={({ isActive }) =>
              `px-3 py-1.5 rounded-md text-sm transition-colors ${
                isActive
                  ? 'bg-vortex-accent/15 text-vortex-accent2'
                  : 'text-gray-400 hover:text-gray-200 hover:bg-white/5'
              }`
            }
          >
            {link.label}
          </NavLink>
        ))}
      </nav>

      <div className="ml-auto flex items-center gap-2 text-xs font-mono">
        <span
          className={`h-2 w-2 rounded-full ${
            connected ? 'bg-vortex-healthy shadow-[0_0_8px_2px_rgba(34,197,94,0.6)]' : 'bg-vortex-leak'
          }`}
        />
        <span className="text-gray-400">{connected ? 'LIVE' : 'DISCONNECTED'}</span>
      </div>
    </header>
  );
}
