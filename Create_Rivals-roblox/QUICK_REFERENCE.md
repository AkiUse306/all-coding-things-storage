# ⚡ Create Rivals - Quick Reference Guide

## 🎯 One-Page Cheat Sheet

### 🚀 Start Everything
```bash
cd /workspaces/rg-Create_Rivals/local && bash START_DASHBOARD.sh
# → Backend running on :5000
# → Dashboard running on :3000
# → Open browser: http://localhost:3000
```

### 🌐 Browser Tabs
| Tab | What To Do | Expected Result |
|-----|-----------|-----------------|
| **Dashboard** | Load page | Green status, queue count updates every 5s |
| **Players** | Type ID, click Search | Shows player stats or "not found" |
| **Players** | Add 50 points, click button | Score increases by 50 |
| **Matchmaking** | Type player ID, create match | Appears in queue list |
| **Stats** | Enter score/level, update | Player data updated |

### 🖥️ C++ CLI Tool
```bash
cd local/build
cmake ..
make
./rivals-client
# Menu:
# 1 = Get player stats
# 2 = Add score
# 3 = Update stats
# 4 = Create match
# 5 = View queue
# 6 = Health check
# 0 = Exit
```

### 📡 API Endpoints
```bash
# All through http://localhost:3000/api/*

curl http://localhost:3000/api/health

curl http://localhost:3000/api/player/alice

curl -X POST http://localhost:3000/api/player/alice/score \
  -H "Content-Type: application/json" \
  -d '{"add": 50}'

curl http://localhost:3000/api/matchmake/queue
```

### 🔧 Port Cleanup
```bash
lsof -i :3000  # Check who's using port 3000
kill -9 <PID>  # Kill the process

lsof -i :5000  # Check who's using port 5000
kill -9 <PID>  # Kill the process
```

---

## 📋 Testing Checklist

### ✅ Backend Check
```bash
curl http://localhost:5000/health
# Expected: 200 OK with JSON response
```

### ✅ Dashboard Check
```bash
curl http://localhost:3000/api/health
# Expected: 200 OK with JSON response
```

### ✅ Browser Check
1. Open http://localhost:3000
2. Look for green status indicator
3. Should show queue count > 0 if players exist
4. Dashboard tab should auto-refresh

### ✅ C++ CLI Check
```bash
./rivals-client
# Choose option 6 (Health Check)
# Should show backend is responding
```

---

## 🎮 Test Scenario (5 Minutes)

### Step 1: Create Player (C++ CLI)
```
./rivals-client
Option: 2
Player ID: bob
Score: 100
→ Success message
```

### Step 2: View in Browser
```
1. Go to http://localhost:3000
2. Players tab
3. Search: bob
4. Should show score 100
```

### Step 3: Update via Browser
```
1. Add Score button: 50
2. Click submit
3. Should show score 150
```

### Step 4: Verify via C++ CLI
```
./rivals-client
Option: 1
Player ID: bob
→ Should show score 150
```

✅ **Congratulations! All 3 languages talking to same backend!**

---

## 🗂️ File Locations

```
Backend:           /workspaces/rg-Create_Rivals/backend/
Dashboard Server:  /workspaces/rg-Create_Rivals/local/
Dashboard UI:      /workspaces/rg-Create_Rivals/local/wwwroot/
C++ CLI:           /workspaces/rg-Create_Rivals/local/main.cpp
Build Script:      /workspaces/rg-Create_Rivals/local/START_DASHBOARD.sh
Docs:              /workspaces/rg-Create_Rivals/MULTI_LANGUAGE_SYSTEM_SUMMARY.md
```

---

## 🔄 Architecture at a Glance

```
Browser (JavaScript)
    ↓ fetch() calls to
C# Dashboard (Program.cs) ← :3000
    ↓ HttpClient proxy to
C# Backend (Matchmaker) ← :5000
    ↓ Always the same data

Plus:
C++ CLI → Direct calls to :3000 or :5000
```

---

## ⚙️ Component Details

### C# Backend (:5000)
- **Purpose:** Game API, player stats, matchmaking
- **Compiles to:** Matchmaker.dll
- **Run:** `dotnet run --project Matchmaker.csproj`
- **Code:** `/backend/`

### C# Dashboard (:3000)
- **Purpose:** Reverse proxy + static file server
- **Compiles to:** LocalDashboard.dll
- **Run:** `dotnet run --project LocalDashboard.csproj`
- **Code:** `/local/Program.cs`

### Browser UI
- **Purpose:** Admin dashboard in browser
- **Files:** `/local/wwwroot/*.{html,css,js}`
- **Access:** http://localhost:3000
- **Tabs:** Dashboard • Players • Matchmaking • Stats

### C++ CLI
- **Purpose:** Command-line testing tool
- **Compiles to:** rivals-client
- **Build:** `cmake .. && make`
- **Code:** `/local/main.cpp`
- **Run:** `./rivals-client`

---

## 💡 Tips & Tricks

### Monitor Queue Real-Time
```bash
while true; do
  curl -s http://localhost:3000/api/matchmake/queue | jq '.queue | length'
  sleep 5
done
```

### Create 10 Test Players
```bash
./rivals-client
# Repeat 10 times:
# Option 2
# Enter: player1, player2, ... player10
# Score: 100 each
```

### Bulk Score Update
```bash
for i in {1..10}; do
  curl -X POST http://localhost:3000/api/player/player$i/score \
    -H "Content-Type: application/json" \
    -d '{"add": 50}'
done
```

### Watch Queue Changes
```bash
watch -n 1 'curl -s http://localhost:3000/api/matchmake/queue | jq'
```

---

## 🆘 Troubleshooting

| Problem | Fix |
|---------|-----|
| Nothing works | Start backend first, then dashboard |
| Red status | Backend not responding, check :5000 |
| C++ won't build | `sudo apt install libcurl4-openssl-dev` |
| Port already in use | `kill -9 $(lsof -t -i:PORT)` |
| Static files missing | Check `/local/wwwroot/` exists |

---

## 📖 When to Read What

| Situation | Read This |
|-----------|-----------|
| First time setup | README.md |
| Step-by-step testing | TESTING_GUIDE.md |
| How does it work? | ARCHITECTURE.md |
| What's included? | SYSTEM_CHECKLIST.md |
| Need help now? | This file |

---

## 🎯 Success Indicators

✅ You're good if you can:
- [ ] Start both servers with one command
- [ ] Open dashboard in browser
- [ ] See green status indicator
- [ ] Search for a player
- [ ] Add score to player
- [ ] Create a match
- [ ] View queue updates
- [ ] Build and run C++ CLI
- [ ] See same data in all three interfaces

---

## 🚀 You're Ready!

Your multi-language game admin system is **complete and production-ready**.

Start with:
```bash
bash START_DASHBOARD.sh
```

Then access:
```
http://localhost:3000
```

That's it! 🎮

---

**Quick Links:**
- 📖 Full Docs: MULTI_LANGUAGE_SYSTEM_SUMMARY.md
- 🧪 Testing: TESTING_GUIDE.md
- 🏛️ Architecture: ARCHITECTURE.md
- ✅ Checklist: SYSTEM_CHECKLIST.md
