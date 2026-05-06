import React, { useState } from 'react';
import '../styles/Home.css';

const Home = ({ onNavigate, history = [], favorites = [] }) => {
  const [searchInput, setSearchInput] = useState('');

  const handleSearch = () => {
    if (searchInput.trim()) {
      const query = encodeURIComponent(searchInput);
      onNavigate(`https://google.com/search?q=${query}`);
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
  };

  const quickShortcuts = [
    { name: 'Google', url: 'https://google.com', icon: '🔍', color: '#4285F4' },
    { name: 'GitHub', url: 'https://github.com', icon: '🐙', color: '#333' },
    { name: 'YouTube', url: 'https://youtube.com', icon: '▶️', color: '#FF0000' },
    { name:  'Wikipedia', url: 'https://wikipedia.org', icon: '📚', color: '#3366CC' },
    { name: 'Twitter', url: 'https://twitter.com', icon: '𝕏', color: '#000' },
    { name: 'LinkedIn', url: 'https://linkedin.com', icon: '💼', color: '#0A66C2' },
    { name: 'Stack Overflow', url: 'https://stackoverflow.com', icon: '💻', color: '#F48024' },
    { name: 'Medium', url: 'https://medium.com', icon: '✍️', color: '#000' },
  ];

  const suggestedSites = [
    { name: 'OpenAI', url: 'https://openai.com', description: 'AI Research' },
    { name: 'Anthropic', url: 'https://anthropic.com', description: 'AI Safety' },
    { name: 'HackerNews', url: 'https://news.ycombinator.com', description: 'Tech News' },
    { name: 'Dev.to', url: 'https://dev.to', description: 'Developer Community' },
  ];

  const recentItems = history.slice(0, 6);

  return (
    <div className="home-container">
      {/* Background with gradient */}
      <div className="home-background"></div>

      {/* Main content */}
      <div className="home-content">
        {/* Logo/Title */}
        <div className="home-header">
          <div className="logo">🤖</div>
          <h1>AI Browser</h1>
          <p className="tagline">Intelligent browsing, powered by AI</p>
        </div>

        {/* Search Bar */}
        <div className="search-section">
          <div className="search-bar-wrapper">
            <input
              type="text"
              value={searchInput}
              onChange={(e) => setSearchInput(e.target.value)}
              onKeyPress={handleKeyPress}
              placeholder="Search the web or enter URL..."
              className="search-bar"
              autoFocus
            />
            <button onClick={handleSearch} className="search-button">
              🔍 Search
            </button>
          </div>
          <div className="search-options">
            <button className="search-option" onClick={() => setSearchInput('')}>
              Clear
            </button>
          </div>
        </div>

        {/* Main sections */}
        <div className="home-sections">
          {/* Quick Shortcuts */}
          <section className="home-section shortcuts-section">
            <h2 className="section-title">Quick Shortcuts</h2>
            <div className="shortcuts-grid">
              {quickShortcuts.map((shortcut, idx) => (
                <button
                  key={idx}
                  className="shortcut-item"
                  onClick={() => onNavigate(shortcut.url)}
                  title={shortcut.name}
                  style={{ '--accent-color': shortcut.color }}
                >
                  <span className="shortcut-icon">{shortcut.icon}</span>
                  <span className="shortcut-name">{shortcut.name}</span>
                </button>
              ))}
            </div>
          </section>

          {/* Two column layout for history and suggestions */}
          <div className="home-columns">
            {/* Recent History */}
            {recentItems.length > 0 && (
              <section className="home-section history-section">
                <h2 className="section-title">Recent History</h2>
                <div className="history-list">
                  {recentItems.map((item, idx) => (
                    <button
                      key={idx}
                      className="history-item"
                      onClick={() => onNavigate(item.url)}
                      title={item.url}
                    >
                      <span className="history-icon">🕐</span>
                      <div className="history-info">
                        <div className="history-domain">
                          {new URL(item.url).hostname}
                        </div>
                        <div className="history-time">
                          {item.timestamp?. toLocaleTimeString() || 'Recently'}
                        </div>
                      </div>
                      <span className="history-arrow">→</span>
                    </button>
                  ))}
                </div>
              </section>
            )}

            {/* Suggested Sites */}
            <section className="home-section suggestions-section">
              <h2 className="section-title">Suggested Sites</h2>
              <div className="suggestions-list">
                {suggestedSites.map((site, idx) => (
                  <button
                    key={idx}
                    className="suggestion-item"
                    onClick={() => onNavigate(site.url)}
                  >
                    <div className="suggestion-header">
                      <span className="suggestion-name">{site.name}</span>
                      <span className="suggestion-arrow">→</span>
                    </div>
                    <div className="suggestion-description">{site.description}</div>
                  </button>
                ))}
              </div>
            </section>
          </div>
        </div>

        {/* Footer tips */}
        <div className="home-footer">
          <p className="tip">💡 Tip: Press Ctrl+T to open a new tab</p>
        </div>
      </div>
    </div>
  );
};

export default Home;