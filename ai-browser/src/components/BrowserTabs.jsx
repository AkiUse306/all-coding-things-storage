import React from 'react';
import '../styles/BrowserTabs.css';

const BrowserTabs = ({ tabs, activeTabId, onNewTab, onCloseTab, onSwitchTab, onToggleSidebar }) => {
  return (
    <div className="browser-tabs-container">
      {/* Window controls for Electron */}
      <div className="window-controls">
        <button className="window-control minimize" title="Minimize">−</button>
        <button className="window-control maximize" title="Maximize">□</button>
        <button className="window-control close" title="Close">✕</button>
      </div>

      {/* Navigation buttons */}
      <div className="nav-buttons">
        <button className="nav-button" title="Back">
          ← Back
        </button>
        <button className="nav-button" title="Forward">
          Forward →
        </button>
        <button className="nav-button refresh" title="Refresh">
          ⟲ Refresh
        </button>
      </div>

      {/* Tabs */}
      <div className="tabs-list">
        {tabs.map((tab) => (
          <div
            key={tab.id}
            className={`tab ${activeTabId === tab.id ?  'active' : ''}`}
            onClick={() => onSwitchTab(tab.id)}
          >
            <span className="tab-icon">📄</span>
            <span className="tab-title">{tab. title}</span>
            <button
              className="tab-close-btn"
              onClick={(e) => {
                e. stopPropagation();
                onCloseTab(tab.id);
              }}
            >
              ✕
            </button>
          </div>
        ))}
        <button className="add-tab-btn" onClick={onNewTab} title="New Tab (Ctrl+T)">
          +
        </button>
      </div>

      {/* Right side buttons */}
      <div className="right-controls">
        <button
          className="icon-button sidebar-toggle"
          onClick={onToggleSidebar}
          title="Toggle Sidebar"
        >
          ☰
        </button>
      </div>
    </div>
  );
};

export default BrowserTabs;