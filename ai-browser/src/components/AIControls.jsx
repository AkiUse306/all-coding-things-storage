import React, { useEffect, useRef } from 'react';
import '../styles/AIControls. css';

const AIControls = ({
  model,
  onModelChange,
  inputValue,
  onInputChange,
  onSendRequest,
  onClearHistory,
  responses,
  isLoading,
  onToggleAI,
}) => {
  const messagesEndRef = useRef(null);

  // Auto-scroll to bottom of messages
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [responses]);

  const handleKeyPress = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      onSendRequest(inputValue);
    }
  };

  return (
    <div className="ai-controls-container">
      {/* AI Header */}
      <div className="ai-header">
        <div className="ai-title">
          <span className="ai-icon">🤖</span>
          <span>AI Assistant</span>
        </div>
        <div className="ai-controls-buttons">
          <select
            value={model}
            onChange={(e) => onModelChange(e. target.value)}
            className="model-selector"
          >
            <option value="gpt-4">GPT-4</option>
            <option value="gpt-3.5">GPT-3.5</option>
            <option value="claude-3">Claude 3</option>
            <option value="local-llm">Local LLM</option>
          </select>
          <button
            className="close-ai-btn"
            onClick={onToggleAI}
            title="Close AI controls"
          >
            ✕
          </button>
        </div>
      </div>

      {/* Messages Container */}
      <div className="ai-messages-container">
        {responses.length === 0 ? (
          <div className="ai-welcome">
            <h3>Welcome to AI Assistant</h3>
            <p>Ask me anything about the current page or any topic! </p>
            <div className="ai-suggestions">
              <button onClick={() => onSendRequest('Summarize this page')}>
                Summarize Page
              </button>
              <button onClick={() => onSendRequest('Extract key information')}>
                Extract Info
              </button>
              <button onClick={() => onSendRequest('Translate to Spanish')}>
                Translate
              </button>
            </div>
          </div>
        ) : (
          <div className="messages-list">
            {responses.map((msg, idx) => (
              <div key={idx} className={`message ${msg.role}`}>
                <div className="message-avatar">
                  {msg.role === 'user' ? '👤' : '🤖'}
                </div>
                <div className={`message-content ${msg.isError ? 'error' : ''}`}>
                  {msg.content}
                </div>
                <span className="message-time">
                  {msg.timestamp.toLocaleTimeString()}
                </span>
              </div>
            ))}
            {isLoading && (
              <div className="message assistant loading">
                <div className="message-avatar">🤖</div>
                <div className="loading-dots">
                  <span></span>
                  <span></span>
                  <span></span>
                </div>
              </div>
            )}
            <div ref={messagesEndRef} />
          </div>
        )}
      </div>

      {/* Input Area */}
      <div className="ai-input-area">
        <textarea
          value={inputValue}
          onChange={(e) => onInputChange(e.target.value)}
          onKeyPress={handleKeyPress}
          placeholder="Ask AI anything...  (Shift+Enter for new line)"
          className="ai-input"
          rows="3"
          disabled={isLoading}
        />
        <div className="ai-input-controls">
          <button
            onClick={() => onSendRequest(inputValue)}
            disabled={! inputValue.trim() || isLoading}
            className="send-btn"
          >
            {isLoading ? 'Sending...' : 'Send'}
          </button>
          {responses.length > 0 && (
            <button
              onClick={onClearHistory}
              className="clear-history-btn"
              title="Clear conversation"
            >
              Clear
            </button>
          )}
        </div>
      </div>
    </div>
  );
};

export default AIControls;