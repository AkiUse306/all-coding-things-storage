onst [activeTab, setActiveTab] = useState('favorites');

  return (
    <div className="sidebar-container">
      {/* Sidebar Header */}
      <div className="sidebar-header">
        <h2>Navigation</h2>
      </div>

      {/* Sidebar Tabs */}
      <div className="sidebar-tabs">
        <button
          className={`sidebar-tab ${activeTab === 'favorites' ? 'active' :  ''}`}
          onClick={() => setActiveTab('favorites')}
        >
          ⭐ Favorites
        </button>
        <button
          className={`sidebar-tab ${activeTab === 'history' ?  'active' : ''}`}
          onClick={() => setActiveTab('history')}
        >
          🕐 History
        </button>
      </div>

      <div className="sidebar-messages">
        {messages.map((msg, idx) => (
          <div key={idx} className={`message ${msg.role}`}>
            {msg.content}
          </div>
        ))}
        {aiResponse && (
          <div className="message ai">
            {aiResponse}
          </div>
        )}
      </div>

      <div className="sidebar-input">
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target. value)}
          onKeyPress={(e) => e.key === 'Enter' && handleSubmit()}
          placeholder="Ask AI anything..."
          className="sidebar-input-field"
        />
        <button onClick={handleSubmit} className="sidebar-send">⬆</button>
      </div>
    </div>
  );