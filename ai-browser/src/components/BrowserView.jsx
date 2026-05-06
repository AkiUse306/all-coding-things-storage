import React, { useState, useRef, useEffect } from 'react';
import '../styles/BrowserView.css';
import Home from './Home';

const BrowserView = ({ tab, onUrlChange, onTitleChange, onAddFavorite }) => {
  const [urlInput, setUrlInput] = useState(tab.url);
  const [isLoading, setIsLoading] = useState(false);
  const webviewRef = useRef(null);

  useEffect(() => {
    setUrlInput(tab.url);
  }, [tab.url]);

  const handleNavigate = () => {
    let url = urlInput;
    if (! url.includes('://')) {
      if (url.includes('. ') && ! url.includes(' ')) {
        url = 'https://' + url;
      } else {
        url = 'https://google.com/search?q=' + encodeURIComponent(url);
      }
    }
    setIsLoading(true);
    onUrlChange(url);
    setTimeout(() => setIsLoading(false), 1500);
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleNavigate();
    }
  };

  const handleAddToFavorites = () => {
    const title = prompt('Favorite name:', tab.title);
    if (title) {
      onAddFavorite(tab.url, title);
    }
  };

  return (
    <div className="browser-view-container">
      {/* Address Bar */}
      <div className="address-bar">
        <input
          type="text"
          value={urlInput}
          onChange={(e) => setUrlInput(e.target.value)}
          onKeyPress={handleKeyPress}
          placeholder="Enter URL or search term..."
          className="address-input"
        />
        <button onClick={handleNavigate} className="address-go-btn">
          GO
        </button>
        <button onClick={handleAddToFavorites} className="favorite-btn" title="Add to favorites">
          ⭐
        </button>
      </div>

      {/* Loading Bar */}
      {isLoading && <div className="loading-bar"></div>}

      {/* Web View Container */}
      <div className="webview-container">
        {tab.url === 'about:blank' ? (
          <Home 
            onNavigate={handleNavigate}
          />
        ) : (
          <iframe
            ref={webviewRef}
            src={tab.url}
            className="webview"
            title="Web Content"
            sandbox="allow-same-origin allow-scripts allow-forms allow-popups allow-presentation"
          />
        )}
      </div>
    </div>
  );
};

export default BrowserView;