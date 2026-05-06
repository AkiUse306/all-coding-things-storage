# 🎮 Create Rivals - Multi-Language Local Dashboard

A comprehensive local admin panel for the Create Rivals game backend, built with **C#**, **C++**, and **JavaScript** working together.

---

## 🚀 Quick Start (30 seconds)

```bash
# Navigate to local directory
cd /workspaces/rg-Create_Rivals/local

# Make startup script executable
chmod +x START_DASHBOARD.sh

# Run everything at once
bash START_DASHBOARD.sh

# Then open browser → http://localhost:3000
```

**Done!** You now have:
- ✅ Backend on port 5000 (C# ASP.NET Core)
- ✅ Dashboard on port 3000 (C# proxy + HTML/JS UI)
- ✅ C++ CLI tool ready for testing

---

## 🏗️ Architecture

```
┌──────────────────────────────────────────────┐
│         BROWSER INTERFACE (:3000)            │
│  ✓ Dashboard, Players, Matchmaking, Stats    │
│  ✓ Dark neon theme, real-time updates        │
└────────────────────┬─────────────────────────┘
                     │ fetch() via api.js
                     ▼
┌──────────────────────────────────────────────┐
│    C# DASHBOARD (Program.cs) (:3000)         │
│  ✓ Serves HTML/CSS/JS from wwwroot/          │
│  ✓ Proxies /api/* to :5000 backend           │
│  ✓ Handles CORS for browser requests         │
└────────────────────┬─────────────────────────┘
                     │ HttpClient proxy
                     ▼
┌──────────────────────────────────────────────┐
│    C# BACKEND (Matchmaker) (:5000)           │
│  ✓ PlayerController & MatchmakingController  │
│  ✓ PlayerStatsService & MatchmakingService  │
└──────────────────────────────────────────────┘

OPTIONAL:
┌──────────────────────────────────────────────┐
│    C++ CLI CLIENT (main.cpp)                 │
│  ✓ Interactive menu for testing              │
│  ✓ Direct API calls via libcurl              │
└──────────────────────────────────────────────┘
```

---

## 🌐 Web Dashboard Features

**Dashboard Tab** - Server status & metrics
- Connection indicator (green/red)
- Real-time queue count
- Auto-refresh every 5 seconds

**Players Tab** - Manage game players
- Search player by ID
- View stats (score, level, kills)
- Add score to player
- Update player level

**Matchmaking Tab** - Create & monitor matches
- Queue a player for matchmaking
- View all players in queue
- Match ID tracking
- Real-time updates

**Stats Tab** - Direct data manipulation
- Set player score and level
- Bulk stat updates for testing

---

## 🔧 Manual Setup

### Option 1: Automatic (Recommended)
```bash
cd /workspaces/rg-Create_Rivals/local
bash START_DASHBOARD.sh
```

### Option 2: Separate Terminals (Manual)

**Terminal 1 - Backend:**
```bash
cd /workspaces/rg-Create_Rivals/backend
dotnet run --project Matchmaker.csproj
# Listens on http://localhost:5000
```

**Terminal 2 - Dashboard:**
```bash
cd /workspaces/rg-Create_Rivals/local
dotnet run --project LocalDashboard.csproj
# Listens on http://localhost:3000
```

**Terminal 3 - Browser:**
```bash
# Open http://localhost:3000
# Or use: $BROWSER http://localhost:3000
```

---

## 🖥️ C++ CLI Client (Optional)

### Build
```bash
cd /workspaces/rg-Create_Rivals/local/build
cmake ..
make
```

### Run
```bash
./rivals-client
```

### Menu Options
```
1. Get Player Stats
2. Add Player Score
3. Update Player Stats
4. Create Match
5. Get Queue
6. Health Check
0. Exit
```

---

## 📊 Testing Workflows

### Scenario: Test Player Lifecycle

1. **Create player (C++ CLI):**
   ```
   Option: 2
   Player ID: alice
   Score to add: 100
   ```

2. **Verify in browser:**
   - Dashboard → Players tab
   - Search: alice
   - Should show score 100

3. **Add more points (Browser):**
   - Add Score: 50
   - Verify alice = 150 points

4. **Check again (C++ CLI):**
   - Option: 1
   - Should confirm 150 points

---

## 🔗 API Endpoints

All routed through dashboard at `:3000`:

```bash
# Health check
curl http://localhost:3000/api/health

# Get player
curl http://localhost:3000/api/player/alice

# Add score
curl -X POST http://localhost:3000/api/player/alice/score \
  -H "Content-Type: application/json" -d '{"add": 50}'

# Update stats
curl -X POST http://localhost:3000/api/player/alice/stats \
  -H "Content-Type: application/json" -d '{"score": 200, "level": 10}'

# Create match
curl -X POST http://localhost:3000/api/matchmake \
  -H "Content-Type: application/json" -d '{"playerId": "alice"}'

# Get queue
curl http://localhost:3000/api/matchmake/queue
```

---

## 📁 Files & Purposes

| File | Language | Purpose |
|------|----------|---------|
| `Program.cs` | C# | ASP.NET Core server + proxy |
| `LocalDashboard.csproj` | C# | .NET project file |
| `main.cpp` | C++17 | CLI client tool |
| `CMakeLists.txt` | CMake | C++ build config |
| `wwwroot/index.html` | HTML | Dashboard UI |
| `wwwroot/style.css` | CSS | Dark neon theme |
| `wwwroot/api.js` | JavaScript | API wrapper |
| `wwwroot/app.js` | JavaScript | UI logic |
| `START_DASHBOARD.sh` | Bash | Auto-start script |

---

## 🐛 Troubleshooting

```bash
# Port already in use?
lsof -i :3000  # Check port 3000
lsof -i :5000  # Check port 5000

# Backend not responding?
curl http://localhost:5000/health

# C++ build issues?
sudo apt install libcurl4-openssl-dev
```

See **TESTING_GUIDE.md** for detailed troubleshooting.

---

## 📚 Documentation

- **README.md** ← Overview (this file)
- **TESTING_GUIDE.md** - Complete testing procedures
- **ARCHITECTURE.md** - Deep technical breakdown

---

## 🎯 Next Steps

1. Run: `bash START_DASHBOARD.sh`
2. Test: Open http://localhost:3000
3. Build C++: Run cmake + make
4. Verify: Use TESTING_GUIDE.md

**All three languages working together! 🚀**

---

## API Endpoints (via Dashboard Server)

### Players
- `GET /api/player/{id}` - Get player stats
- `POST /api/player/{id}/score` - Add score
- `POST /api/player/{id}/stats` - Update level/score

### Matchmaking
- `POST /api/matchmake` - Create match
- `GET /api/matchmake/queue` - Get queue

### Health
- `GET /api/health` - Server status

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Connection refused (3000) | Run C# dashboard: `dotnet run` |
| Connection refused (5000) | Run backend: `cd backend && dotnet run` |
| Can't build C++ | Install: `apt install libcurl4-openssl-dev nlohmann-json3-dev` |
| CORS errors | C# server already enables CORS for all origins |

---

## Development

### Add new C# endpoints

Edit `Program.cs`:
```csharp
app.MapGet("/api/new-endpoint", async () => {
    // Your logic
});
```

### Add new UI tab

Edit `wwwroot/index.html` and `wwwroot/app.js`:
```javascript
function myNewTab() {
    // UI logic
}
```

---

## Status

- ✅ C# Web Server (ASP.NET Core 8.0)
- ✅ HTML/CSS/JS Frontend
- ✅ C++ CLI Client
- ✅ Full API proxy to backend

Ready to test locally! 🚀
