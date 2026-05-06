# 🌐 Create Rivals - Multi-Language Architecture Overview

## System Components

### 1. **Backend Server** (C# ASP.NET Core 8.0)
**Location:** `/workspaces/rg-Create_Rivals/backend/`  
**Port:** 5000  
**Language:** C#  
**Purpose:** Game server with matchmaking and player stats APIs

```
Health Check:         GET  http://localhost:5000/health
Get Player:           GET  http://localhost:5000/player/{id}
Add Player Score:     POST http://localhost:5000/player/{id}/score
Update Player Stats:  POST http://localhost:5000/player/{id}/stats
Create Match:         POST http://localhost:5000/matchmake
Get Queue:            GET  http://localhost:5000/matchmake/queue
```

### 2. **C# Dashboard Server** (ASP.NET Core 8.0)
**Location:** `/workspaces/rg-Create_Rivals/local/`  
**Port:** 3000  
**Language:** C#  
**Purpose:** Proxy + serves web UI

```
Static Files:    /wwwroot/* served at http://localhost:3000/
Proxy Endpoints: /api/* forwarded to http://localhost:5000/*
Fallback:        All unmatched routes serve index.html
```

**Proxy Layer (Program.cs):**
- Receives request on :3000
- Forwards to backend on :5000
- Returns JSON response
- Handles CORS headers

### 3. **Browser Dashboard** (HTML/CSS/JavaScript)
**Location:** `/workspaces/rg-Create_Rivals/local/wwwroot/`  
**Port:** 3000 (served by C# server)  
**Language:** HTML, CSS, JavaScript  
**Purpose:** Admin panel for game management

**Files:**
- `index.html` - UI template with 4 tabs
- `style.css` - Dark neon theme
- `api.js` - JavaScript fetch wrappers for /api/* endpoints
- `app.js` - UI logic, event handlers, auto-refresh timers

**Features:**
- 📊 Dashboard: Server status, connection indicator, queue count
- 👥 Players: Search, view stats, add score, update level
- 🎮 Matchmaking: Create matches, view queue
- 📈 Stats: Direct player stat updates

### 4. **C++ CLI Client** (C++17)
**Location:** `/workspaces/rg-Create_Rivals/local/main.cpp`  
**Port:** 3000 (calls dashboard proxy)  
**Language:** C++17  
**Purpose:** Command-line interface for backend testing

**Dependencies:**
- libcurl (HTTP requests)
- nlohmann/json (JSON parsing, optional)

**Build:**
```bash
cd /workspaces/rg-Create_Rivals/local/build
cmake .. && make
./rivals-client
```

**Features:**
- Interactive menu system
- Get player stats
- Add score to player
- Update player stats
- Create matchmaking request
- View queue
- Health check

---

## Data Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    Browser Dashboard                         │
│  ┌─────────────────────────────────────────────────────┐    │
│  │  HTML/CSS/JS (index.html, style.css, app.js)       │    │
│  │  ✓ 4 Tabs: Dashboard, Players, Matchmaking, Stats  │    │
│  │  ✓ Forms for player search, score add, match make  │    │
│  │  ✓ Auto-refresh timers (5s status, 10s queue)      │    │
│  └────────────────────┬────────────────────────────────┘    │
│                       │ HTTP GET/POST                        │
│                       ▼ fetch() calls                        │
│  ┌─────────────────────────────────────────────────────┐    │
│  │  api.js - JavaScript API Wrapper Layer             │    │
│  │  ✓ getPlayer(id)                                   │    │
│  │  ✓ addScore(id, amount)                            │    │
│  │  ✓ updateStats(id, score, level)                   │    │
│  │  ✓ createMatch(playerId)                           │    │
│  │  ✓ getQueue()                                      │    │
│  │  ✓ healthCheck()                                   │    │
│  └────────────────────┬────────────────────────────────┘    │
│                       │ Calls: http://localhost:3000/api/*   │
└───────────────┬──────────────────────────────────────────────┘
                │
                │ HTTP Requests
                │ Reverse Proxy
                ▼
┌──────────────────────────────────────────────────────────────┐
│        C# Dashboard Server (Program.cs)                       │
│        Port 3000                                              │
│  ┌────────────────────────────────────────────────────────┐  │
│  │ Static File Serving:                                  │  │
│  │ • Serves /wwwroot/* as root                           │  │
│  │ • Falls back to index.html for routes                 │  │
│  │ • Handles CORS for cross-origin requests              │  │
│  └────────────────────────────────────────────────────────┘  │
│  ┌────────────────────────────────────────────────────────┐  │
│  │ API Proxy Layer:                                      │  │
│  │ • GET/POST /api/* routes                              │  │
│  │ • Forwards requests to http://localhost:5000/*        │  │
│  │ • Returns JSON responses                              │  │
│  │ • Error handling with fallback messages               │  │
│  └────────────────────┬─────────────────────────────────┘  │
│                       │ HTTP GET/POST                       │
│                       │ Proxy to :5000                      │
│                       ▼                                     │
│           ┌──────────────────────┐                         │
│           │  HttpClient          │                         │
│           │  (System.Net.Http)   │                         │
│           └──────────────────────┘                         │
└──────────────┬───────────────────────────────────────────────┘
               │
               │ HTTP GET/POST
               │ Backend Calls
               ▼
┌──────────────────────────────────────────────────────────────┐
│        C# Backend Server (Matchmaker)                         │
│        Port 5000                                              │
│  ┌────────────────────────────────────────────────────────┐  │
│  │ Controllers:                                          │  │
│  │ • PlayerController (GET/POST player routes)           │  │
│  │ • MatchmakingController (GET/POST match routes)       │  │
│  └────────────────────────────────────────────────────────┘  │
│  ┌────────────────────────────────────────────────────────┐  │
│  │ Services:                                             │  │
│  │ • PlayerStatsService (in-memory player data)          │  │
│  │ • MatchmakingService (queue + persistent matchIds)    │  │
│  └────────────────────────────────────────────────────────┘  │
└──────────────────────────────────────────────────────────────┘

Parallel Access:
    C++ CLI Client (main.cpp)
    ├─ Direct API calls to :3000 dashboard proxy
    ├─ Calls: getPlayerStats, addScore, createMatch, getQueue
    └─ Interactive menu interface for testing
```

---

## Request Flow Examples

### Example 1: User searches for player via Dashboard

```
1. Browser (JavaScript)
   api.js: getPlayer("alice")
   
2. Network
   HTTP GET http://localhost:3000/api/player/alice
   
3. C# Dashboard (Program.cs)
   app.MapGet("/api/player/{id}", ...)
   Creates HttpClient request to backend
   
4. Network
   HTTP GET http://localhost:5000/player/alice
   
5. C# Backend
   PlayerController.GetPlayer(id)
   Returns: { id: "alice", score: 150, level: 5 }
   
6. Network
   Response back to Dashboard with JSON
   
7. Dashboard (Program.cs)
   Deserializes JSON and returns to browser
   
8. Network
   JSON response to browser
   
9. Browser (JavaScript)
   app.js: displayPlayer(data)
   Updates UI with player info
```

### Example 2: User creates match via C++ CLI

```
1. Terminal (C++)
   main.cpp: Interactive menu
   User selects: "4. Create Match"
   User enters: "bob"
   
2. C++ Code
   Makes HTTP POST request using libcurl
   POST http://localhost:3000/api/matchmake
   Body: {"playerId": "bob"}
   
3. Network
   Request to C# Dashboard on :3000
   
4. C# Dashboard (Program.cs)
   app.MapPost("/api/matchmake", ...)
   Creates HttpClient request to backend
   
5. Network
   HTTP POST http://localhost:5000/matchmake
   
6. C# Backend
   MatchmakingController.Matchmake(req)
   Adds "bob" to queue
   Assigns matchId if needed
   Returns: { matchId: "match123", playerId: "bob", position: 1 }
   
7. Network
   Response back to Dashboard
   
8. Dashboard (Program.cs)
   Deserializes and returns JSON
   
9. Network
   JSON response to C++ client
   
10. C++ Code
    Parses JSON with nlohmann/json
    Displays: "Match created! ID: match123"
```

---

## Language Integration Matrix

| Feature | C# (Backend) | C# (Dashboard) | JavaScript | C++ |
|---------|--------------|----------------|------------|-----|
| HTTP Server | ✅ Kestrel | ✅ Kestrel | ✅ Browser | ❌ |
| HTTP Client | ✅ HttpClient | ✅ HttpClient | ✅ fetch() | ✅ libcurl |
| JSON Parse | ✅ System.Text.Json | ✅ System.Text.Json | ✅ JSON.parse() | ✅ nlohmann/json |
| Routing | ✅ MapGet/MapPost | ✅ MapGet/MapPost | ❌ | ❌ |
| Static Files | ❌ | ✅ UseStaticFiles | ❌ | ❌ |
| CORS | ❌ | ✅ AddCors | ✅ (browser) | ❌ |
| CLI Interface | ❌ | ❌ | ❌ | ✅ interactive menu |
| Data Persistence | ✅ in-memory | ❌ | ❌ | ❌ |
| Real-time Events | ❌ | ❌ | ✅ polling | ✅ polling |

---

## Deployment Checklist

### Pre-Deployment Tests

- [ ] Backend compiles: `cd backend && dotnet build Matchmaker.csproj`
- [ ] Dashboard compiles: `cd local && dotnet build LocalDashboard.csproj`
- [ ] C++ builds: `cd local && mkdir build && cd build && cmake .. && make`
- [ ] Backend runs on :5000 without errors
- [ ] Dashboard runs on :3000 without errors
- [ ] Browser loads at http://localhost:3000
- [ ] Dashboard tabs load correctly
- [ ] Player search works
- [ ] Matchmaking creates games
- [ ] C++ CLI connects and runs
- [ ] All multi-language flows tested

### Production Considerations

**For WebSocket real-time updates (future):**
- Replace polling in app.js with WebSocket.onmessage
- Add SignalR to C# backend for server push

**For authentication:**
- Add JWT tokens to dashboard
- Require auth header in C# proxy
- Pass auth to backend API calls

**For load balancing:**
- Dashboard can proxy to multiple backend instances
- Round-robin in HttpClient requests

**For database:**
- Replace in-memory services with Entity Framework Core
- Add connection string to appsettings.json

---

## Files Summary

```
Create Rivals Multi-Language System
├── Backend (C# ASP.NET Core)
│   ├── Matchmaker.csproj
│   ├── Program.cs - Server + routes
│   ├── Controllers/
│   │   ├── PlayerController.cs
│   │   └── MatchmakingController.cs
│   ├── Services/
│   │   ├── PlayerStatsService.cs
│   │   └── MatchmakingService.cs
│   └── Models/ - Data classes
│
├── Dashboard (C# ASP.NET Core)
│   ├── LocalDashboard.csproj
│   ├── Program.cs - Proxy + static files
│   └── wwwroot/
│       ├── index.html - UI
│       ├── style.css - Styling
│       ├── api.js - JS API wrapper
│       └── app.js - UI logic
│
├── CLI Client (C++)
│   ├── main.cpp - Interactive menu
│   └── CMakeLists.txt - Build config
│
└── Documentation
    ├── README.md - Architecture overview
    ├── TESTING_GUIDE.md - How to test
    ├── START_DASHBOARD.sh - Quick startup
    └── ARCHITECTURE.md - This file
```

---

## Getting Started

### 1. Build Everything
```bash
# Build backend
cd /workspaces/rg-Create_Rivals/backend
dotnet build Matchmaker.csproj

# Build dashboard
cd /workspaces/rg-Create_Rivals/local
dotnet build LocalDashboard.csproj

# Build C++ (optional, requires libcurl)
mkdir -p build
cd build
cmake .. && make
```

### 2. Start Servers
```bash
# Terminal 1: Backend
cd /workspaces/rg-Create_Rivals/backend
dotnet run --project Matchmaker.csproj

# Terminal 2: Dashboard
cd /workspaces/rg-Create_Rivals/local
dotnet run --project LocalDashboard.csproj
```

### 3. Access Dashboard
```
Browser: http://localhost:3000
```

### 4. Test with C++ (Optional)
```bash
cd /workspaces/rg-Create_Rivals/local/build
./rivals-client
```

---

**All three languages working together to manage your game! 🎮**
