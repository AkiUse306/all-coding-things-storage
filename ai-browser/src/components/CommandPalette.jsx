import React, { useState } from 'react';
import '../styles/CommandPalette.css';

const COMMANDS = [
  { label: 'Search Google', action: (q) => `https://google.com/search?q=${q}` },
  { label: 'Search Wikipedia', action: (q) => `https://wikipedia.org/search?search=${q}` },
  { label: 'Ask AI', action: (q) => q },
];

export default function CommandPalette({ onCommand, onClose }) {
  const [input, setInput] = useState('');

  const handleCommand = (cmd) => {
    onCommand(input);
    onClose();
  };

  return (
    <div className="command-palette-overlay" onClick={onClose}>
      <div className="command-palette" onClick={(e) => e.stopPropagation()}>
        <input
          autoFocus
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyPress={(e) => e.key === 'Escape' && onClose()}
          placeholder="Type command or query..."
          className="command-input"
        />
        <div className="command-list">
          {COMMANDS.map((cmd, idx) => (
            <div
              key={idx}
              className="command-item"
              onClick={() => handleCommand(cmd)}
            >
              {cmd.label}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
