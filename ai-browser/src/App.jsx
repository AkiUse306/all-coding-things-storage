import React, { useState, useRef, useEffect } from 'react';
import './App.css';
import Navbar from './components/Navbar';
import Browser from './components/Browser';
import Sidebar from './components/Sidebar';
import CommandPalette from './components/CommandPalette';

function App() {
  const [tabs, setTabs] = useState([
    { id: 1, title: 'Google', url: 'https://google.com', active: true }
  ]);
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [commandPaletteOpen, setCommandPaletteOpen] = useState(false);
  const [aiResponse, setAiResponse] = useState(null);

  const activeTab = tabs.find(t => t.active);

  const addTab = () => {
    const newId = Math.max(...tabs.map(t => t.id), 0) + 1;
    setTabs([...tabs, {
      id: newId,
      title: 'New Tab',
      url:  'about:blank',
      active: true
    }]);
    tabs.forEach(t => t.active = false);
  };

  const closeTab = (id) => {
    const newTabs = tabs.filter(t => t.id !== id);
    if (newTabs.length === 0) {
      addTab();
    } else if (tabs.find(t => t.id === id)?.active) {
      newTabs[newTabs.length - 1].active = true;
    }
    setTabs(newTabs);
  };

  const switchTab = (id) => {
    setTabs(tabs.map(t => ({
      ...t,
      active: t.id === id
    })));
  };

  const navigateTab = (url) => {
    setTabs(tabs.map(t => t.active ?  { ...t, url } : t));
  };

  const handleAiQuery = async (query) => {
    setAiResponse(`Processing:  "${query}"... `);
    // AI integration here
    setTimeout(() => {
      setAiResponse(`AI Answer for "${query}":\n\nThis is a placeholder response.  Integrate OpenAI/Claude API here. `);
    }, 1000);
  };

  return (
    <div className="app">
      <Navbar 
        tabs={tabs}
        activeTab={activeTab}
        onAddTab={addTab}
        onCloseTab={closeTab}
        onSwitchTab={switchTab}
        onNavigate={navigateTab}
        onToggleSidebar={() => setSidebarOpen(!sidebarOpen)}
        onToggleCommandPalette={() => setCommandPaletteOpen(!commandPaletteOpen)}
      />
      
      <div className="main-container">
        <div className="browser-wrapper">
          {activeTab && (
            <Browser
              url={activeTab.url}
              onNavigate={navigateTab}
            />
          )}
        </div>

        {sidebarOpen && (
          <Sidebar
            aiResponse={aiResponse}
            onQuery={handleAiQuery}
            onClose={() => setSidebarOpen(false)}
          />
        )}
      </div>

      {commandPaletteOpen && (
        <CommandPalette
          onCommand={handleAiQuery}
          onClose={() => setCommandPaletteOpen(false)}
        />
      )}
    </div>
  );
}

export default App;
