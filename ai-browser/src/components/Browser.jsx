import React, { useEffect, useRef } from 'react';
import '../styles/Browser.css';

export default function Browser({ url, onNavigate }) {
  const iframeRef = useRef(null);

  useEffect(() => {
    if (iframeRef.current && url && url !== 'about:blank') {
      iframeRef.current.src = url;
    }
  }, [url]);

  return (
    <div className="browser-container">
      <iframe
        ref={iframeRef}
        src={url}
        className="browser-frame"
        title="Web Content"
        sandbox="allow-same-origin allow-scripts allow-forms allow-popups"
      />
    </div>
  );
}
