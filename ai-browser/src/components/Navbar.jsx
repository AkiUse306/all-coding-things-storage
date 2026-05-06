import React, { useState } from 'react';
import '../styles/Navbar.css';

export default function Navbar({
  tabs,
  activeTab,
  onAddTab,
  onCloseTab,
  onSwitchTab,
  onNavigate,
  onToggleSidebar,
  onToggleCommandPalette
}) {
  const [addressInput, setAddressInput] = useState(activeTab?. url || '');

  const handleNavigate = () => {
    let url = addressInput;
    if (! url.startsWith('http')) {
      url = `https://${url}`;
    }
    onNavigate(url);
  };

  return (
    <div className="navbar">
      {/* Window Controls */}
      <div className="window-controls">
        <button onClick={() => window.electronAPI?. minimizeWindow()} className="control-btn">−</button>
        <button onClick={() => window.electronAPI?.maximizeWindow()} className="control-btn">□</button>
        <button onClick={() => window.electronAPI?.closeWindow()} className="control-btn close">✕</button>
      </div>

      {/* Navigation Bar */}
      <div className="nav-section">
        <button className="nav-btn" title="Back">←</button>
        <button className="nav-btn" title="Forward">→</button>
        <button className="nav-btn" title="Reload">⟲</button>
      </div>

      {/* Address Bar */}
      <div className="address-bar">
        <input
          type="text"
          value={addressInput}
          onChange={(e) => setAddressInput(e.target.value)}
          onKeyPress={(e) => e.key === 'Enter' && handleNavigate()}
          placeholder="Enter URL or search..."
          className="address-input"
        />
        <button onClick={handleNavigate} className="go-btn">GO</button>
      </div>

      {/* Tabs */}
      <div className="tabs-container">
        {tabs.map(tab => (
          <div
            key={tab.id}
            className={`tab ${tab.active ? 'active' :  ''}`}
            onClick={() => onSwitchTab(tab. id)}
          >
            <span className="tab-title">{tab.title}</span>
            <button
              className="tab-close"
              onClick={(e) => {
                e. stopPropagation();
                onCloseTab(tab.id);
              }}
            >✕</button>
          </div>
        ))}
        <button className="add-tab-btn" onClick={onAddTab}>+</button>
      </div>

      {/* Action Buttons */}
      <div className="action-buttons">
        <button 
          className="action-btn sidebar-btn"
          onClick={onToggleSidebar}
          title="AI Sidebar"
        >
          ⚡
        </button>
        <button 
          className="action-btn palette-btn"
          onClick={onToggleCommandPalette}
          title="Command Palette (Ctrl+Shift+P)"
        >
          ⌘
        </button>
      </div>
    </div>
  );
}
