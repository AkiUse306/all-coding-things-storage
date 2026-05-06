# 📑 Create Rivals - Documentation Index

## 🎯 Where to Start

### First Time? Start Here ⭐
1. **[QUICK_REFERENCE.md](QUICK_REFERENCE.md)** (5 min read)
   - One-page cheat sheet
   - All commands and basics
   - Quick troubleshooting

2. **Run the system:**
   ```bash
   cd /workspaces/rg-Create_Rivals/local
   bash START_DASHBOARD.sh
   # Then: http://localhost:3000
   ```

3. **Then read:** [README.md](README.md) (10 min read)
   - Overview of system
   - Features and capabilities
   - Manual setup options

---

## 📚 Complete Documentation Map

### Quick References
| Document | Purpose | Read Time |
|----------|---------|-----------|
| [QUICK_REFERENCE.md](QUICK_REFERENCE.md) | One-page cheat sheet | 5 min |
| [SYSTEM_MAP.md](SYSTEM_MAP.md) | Visual system architecture | 10 min |
| [README.md](README.md) | Project overview | 10 min |

### Detailed Guides
| Document | Purpose | Read Time |
|----------|---------|-----------|
| [TESTING_GUIDE.md](TESTING_GUIDE.md) | How to test everything | 20 min |
| [ARCHITECTURE.md](ARCHITECTURE.md) | Technical deep dive | 30 min |
| [SYSTEM_CHECKLIST.md](SYSTEM_CHECKLIST.md) | What's included | 15 min |

### Top-Level Summaries
| Document | Purpose | Read Time |
|----------|---------|-----------|
| [../MULTI_LANGUAGE_SYSTEM_SUMMARY.md](../MULTI_LANGUAGE_SYSTEM_SUMMARY.md) | Complete system overview | 15 min |
| [../QUICK_REFERENCE.md](../QUICK_REFERENCE.md) | Quick reference from repo root | 5 min |

### Automation
| File | Purpose | Run |
|------|---------|-----|
| [START_DASHBOARD.sh](START_DASHBOARD.sh) | Start both servers | `bash START_DASHBOARD.sh` |

---

## 🗺️ Navigation by Use Case

### "I just want to run it"
1. Read: [QUICK_REFERENCE.md](QUICK_REFERENCE.md)
2. Run: `bash START_DASHBOARD.sh`
3. Open: http://localhost:3000
4. ✅ Done

### "I want to test features"
1. Read: [TESTING_GUIDE.md](TESTING_GUIDE.md)
2. Follow: Step-by-step procedures
3. Check: "Scenario" sections
4. ✅ All features tested

### "I want to understand the system"
1. Read: [SYSTEM_MAP.md](SYSTEM_MAP.md) (visual)
2. Read: [ARCHITECTURE.md](ARCHITECTURE.md) (technical)
3. Read: [../MULTI_LANGUAGE_SYSTEM_SUMMARY.md](../MULTI_LANGUAGE_SYSTEM_SUMMARY.md) (complete)
4. ✅ System understood

### "I want to extend it"
1. Read: [ARCHITECTURE.md](ARCHITECTURE.md)
2. Study: [Program.cs](Program.cs) (C# proxy)
3. Study: [main.cpp](main.cpp) (C++ client)
4. Study: [wwwroot/api.js](wwwroot/api.js) (JavaScript)
5. ✅ Ready to modify

### "Something's broken"
1. Check: [QUICK_REFERENCE.md](QUICK_REFERENCE.md#️-troubleshooting)
2. Follow: [TESTING_GUIDE.md](TESTING_GUIDE.md#-troubleshooting)
3. Verify: [SYSTEM_CHECKLIST.md](SYSTEM_CHECKLIST.md#-verification-checklist)
4. ✅ Fixed

---

## 🏗️ System Components

### C# Backend (:5000)
- **Location:** `/workspaces/rg-Create_Rivals/backend/`
- **Purpose:** Game API - players, matchmaking, stats
- **Docs:** See [ARCHITECTURE.md](ARCHITECTURE.md) for API endpoints
- **Run:** `cd backend && dotnet run --project Matchmaker.csproj`

### C# Dashboard (:3000)
- **Location:** `/workspaces/rg-Create_Rivals/local/`
- **Purpose:** Reverse proxy + static file server
- **Docs:** [Program.cs](Program.cs) source + [ARCHITECTURE.md](ARCHITECTURE.md)
- **Run:** `cd local && dotnet run --project LocalDashboard.csproj`

### Browser UI
- **Location:** `/workspaces/rg-Create_Rivals/local/wwwroot/`
- **Purpose:** Admin dashboard in browser
- **Access:** http://localhost:3000
- **Tabs:** Dashboard • Players • Matchmaking • Stats

### C++ CLI
- **Location:** `/workspaces/rg-Create_Rivals/local/`
- **Purpose:** Command-line testing tool
- **Docs:** [main.cpp](main.cpp) source + [QUICK_REFERENCE.md#-c-cli-tool](QUICK_REFERENCE.md)
- **Run:** `cd local/build && cmake .. && make && ./rivals-client`

---

## 📊 Feature Overview

### Dashboard Features
- ✅ Server status & connection indicator
- ✅ Real-time queue count
- ✅ Player search and stats viewing
- ✅ Score and level management
- ✅ Match creation and queue management
- ✅ Direct stat updates

### API Endpoints
```
GET    /api/health              # Health check
GET    /api/player/{id}         # Get player
POST   /api/player/{id}/score   # Add score
POST   /api/player/{id}/stats   # Update stats
POST   /api/matchmake           # Create match
GET    /api/matchmake/queue     # Get queue
```

### C++ CLI Features
- ✅ Get player stats
- ✅ Add player score
- ✅ Update player stats
- ✅ Create matches
- ✅ View queue
- ✅ Health checks

---

## 🎯 Quick Commands Reference

### Start System
```bash
cd /workspaces/rg-Create_Rivals/local
bash START_DASHBOARD.sh
```

### Check Status
```bash
curl http://localhost:5000/health    # Backend
curl http://localhost:3000/api/health # Dashboard
```

### Build C++ CLI
```bash
cd /workspaces/rg-Create_Rivals/local
mkdir -p build && cd build
cmake ..
make
./rivals-client
```

### Clean Ports
```bash
kill -9 $(lsof -t -i:3000)  # Kill port 3000
kill -9 $(lsof -t -i:5000)  # Kill port 5000
```

---

## 🔍 What Each File Does

### Documentation Files
- **QUICK_REFERENCE.md** - One-page guide (read first!)
- **README.md** - Project overview
- **TESTING_GUIDE.md** - How to test everything
- **ARCHITECTURE.md** - Technical details
- **SYSTEM_CHECKLIST.md** - What's included
- **SYSTEM_MAP.md** - Visual diagrams
- **INDEX.md** - This file

### Source Code Files
- **Program.cs** - C# ASP.NET Core server
- **main.cpp** - C++ CLI client
- **CMakeLists.txt** - C++ build config
- **LocalDashboard.csproj** - C# project file

### UI Files
- **wwwroot/index.html** - Dashboard template
- **wwwroot/style.css** - Dark neon styling
- **wwwroot/api.js** - JavaScript API wrapper
- **wwwroot/app.js** - UI logic

### Automation
- **START_DASHBOARD.sh** - Run both servers

---

## 📈 Support & Troubleshooting

### Common Issues
| Problem | Solution | Docs |
|---------|----------|------|
| Port in use | `kill -9 $(lsof -t -i:PORT)` | [QUICK_REFERENCE.md](QUICK_REFERENCE.md) |
| Backend not responding | Start backend first | [README.md](README.md) |
| C++ build fails | `sudo apt install libcurl4-openssl-dev` | [TESTING_GUIDE.md](TESTING_GUIDE.md) |
| Static files missing | Check wwwroot/ exists | [SYSTEM_CHECKLIST.md](SYSTEM_CHECKLIST.md) |

### Debug Tools
- Browser DevTools: `F12` in dashboard
- curl: Test API directly
- `watch`: Monitor queue changes
- Logs: Check console output in terminals

---

## ✅ Verification Checklist

All systems verified and working:
- ✅ C# Backend: Compiles & runs on :5000
- ✅ C# Dashboard: Compiles & runs on :3000
- ✅ Browser UI: Loads & functional on :3000
- ✅ C++ CLI: Compiles & runs interactively
- ✅ API Endpoints: All 6 working
- ✅ Multi-language: All 3 can talk to backend
- ✅ Documentation: Complete & comprehensive

---

## 🚀 Getting Started in 3 Steps

### Step 1: Run
```bash
cd /workspaces/rg-Create_Rivals/local
bash START_DASHBOARD.sh
```

### Step 2: Access
```
Browser: http://localhost:3000
```

### Step 3: Test
Follow [TESTING_GUIDE.md](TESTING_GUIDE.md)

---

## 🎓 Learning Resources

### Beginner
- Start: [QUICK_REFERENCE.md](QUICK_REFERENCE.md)
- Run: `bash START_DASHBOARD.sh`
- Test: http://localhost:3000

### Intermediate
- Read: [README.md](README.md)
- Test: Follow [TESTING_GUIDE.md](TESTING_GUIDE.md)
- Extend: Modify UI in wwwroot/

### Advanced
- Study: [ARCHITECTURE.md](ARCHITECTURE.md)
- Modify: Edit Program.cs for proxy
- Extend: Add C++ CLI features

---

## 📞 Quick Help

**Q: Where do I start?**
A: Read [QUICK_REFERENCE.md](QUICK_REFERENCE.md) then run `bash START_DASHBOARD.sh`

**Q: How do I test features?**
A: Follow [TESTING_GUIDE.md](TESTING_GUIDE.md)

**Q: Where are the APIs?**
A: See [ARCHITECTURE.md](ARCHITECTURE.md) - API Endpoints section

**Q: How do I use the C++ CLI?**
A: See [QUICK_REFERENCE.md](QUICK_REFERENCE.md) - C++ CLI Tool section

**Q: Something's not working!**
A: Check [QUICK_REFERENCE.md](QUICK_REFERENCE.md) - Troubleshooting section

---

## 📋 File Locations Summary

```
🗂️ Documentation (Read First)
  ├─ 📄 QUICK_REFERENCE.md      ⭐ START HERE
  ├─ 📄 README.md                (Overview)
  ├─ 📄 TESTING_GUIDE.md         (How to test)
  ├─ 📄 ARCHITECTURE.md          (Technical)
  ├─ 📄 SYSTEM_CHECKLIST.md      (What's included)
  └─ 📄 SYSTEM_MAP.md            (Visual diagrams)

🔧 C# Projects
  ├─ 🛠️ Program.cs               (Dashboard server)
  └─ 🛠️ LocalDashboard.csproj    (Project file)

🌐 Browser UI
  └─ 📁 wwwroot/
     ├─ 🎨 index.html            (Dashboard)
     ├─ 🎨 style.css             (Styling)
     ├─ 🎨 api.js                (API client)
     └─ 🎨 app.js                (UI logic)

🔨 C++ Project
  ├─ 🛠️ main.cpp                 (CLI client)
  └─ 🛠️ CMakeLists.txt           (Build config)

🚀 Automation
  └─ ⚙️ START_DASHBOARD.sh       (Run this!)
```

---

## 🎯 Next Steps

1. ✅ **Read:** [QUICK_REFERENCE.md](QUICK_REFERENCE.md)
2. ✅ **Run:** `bash START_DASHBOARD.sh`
3. ✅ **Test:** Open http://localhost:3000
4. ✅ **Explore:** Click through all dashboard tabs
5. ✅ **Advanced:** Build C++ CLI and test it
6. ✅ **Learn:** Read ARCHITECTURE.md for deep understanding

---

**🎮 Your multi-language game admin system is ready!**

Start here: [QUICK_REFERENCE.md](QUICK_REFERENCE.md)

Then run: `bash START_DASHBOARD.sh`

Then open: http://localhost:3000

---

*Happy game development! 🚀*
