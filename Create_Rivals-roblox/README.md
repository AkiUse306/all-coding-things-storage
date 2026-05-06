# Game Documentation

1. 📌 Core Game Loop (Refined)

The game is built around a high-retention loop:

Spawn → Fight (Arena) → Earn Aklas → Return to Hub → Upgrade / Gamble → Get Stronger → Repeat

Key Design Improvements:

Skill-based combat (not pure pay-to-win)

RNG (pets) balanced with grind

Fast UI interactions (no lag menus)

Session-based rewards to keep players engaged

2. 🌍 Roblox Experience Structure (IMPORTANT)

In Roblox Studio, your game should be structured using Places:

Create a Rival (Experience)
│
├── Place 1: Hub World (Crafting / Shop)
├── Place 2: Combat Arena (Fighting)
🔁 Teleport Flow:

Use TeleportService to move players:

Hub → Arena

Arena → Hub

3. 🧱 EXACT ROBLOX STUDIO FOLDER STRUCTURE

This is critical — follow this exactly.

📁 ReplicatedStorage (Shared Logic)

➡️ Right-click → Insert Folder → name it Modules

ReplicatedStorage
│
├── Modules
│   ├── CombatSystem.lua
│   ├── WeaponConfig.lua
│   ├── PetSystem.lua
│   ├── EconomySystem.lua
│   ├── RNGSystem.lua
│   ├── PlayerStats.lua
│
├── Remotes
│   ├── DealDamage (RemoteEvent)
│   ├── BuyItem (RemoteEvent)
│   ├── OpenEgg (RemoteEvent)
│   ├── EquipWeapon (RemoteEvent)
📁 ServerScriptService (Backend Logic)

➡️ Insert Scripts here

ServerScriptService
│
├── DataManager.lua
├── GameManager.lua
├── AntiCheat.lua
├── CombatHandler.lua
├── EconomyHandler.lua
├── PetHandler.lua
📁 StarterPlayerScripts (Client Systems)
StarterPlayer
│
├── StarterPlayerScripts
│   ├── UIController.lua
│   ├── InputHandler.lua
│   ├── CameraEffects.lua
📁 StarterGui (UI System)
StarterGui
│
├── MainUI (ScreenGui)
│   ├── ShopFrame
│   ├── InventoryFrame
│   ├── PetFrame
│   ├── StatsFrame
│   ├── EggOpeningUI
📁 Workspace (Game World)
Workspace
│
├── Map
├── SpawnPoints
├── ArenaZones
├── Decorative
│   ├── GraffitiWall ("Made by Akin")
4. 🥊 Combat System (Roblox Implementation)
🔧 How to Build:
Step 1: Weapon Tool

Insert Tool into StarterPack

Add:

Handle

Script (LocalScript + Server validation)

Step 2: Raycasting

Inside CombatSystem.lua:

function FireWeapon(player, origin, direction)
    local ray = Ray.new(origin, direction * 500)
    local hit, position = workspace:FindPartOnRay(ray)

    if hit and hit.Parent:FindFirstChild("Humanoid") then
        return hit.Parent
    end

    return nil
end
Step 3: Server Validation

➡️ NEVER trust client damage

5. 💰 Economy System (Aklas)
Data Model:
PlayerData = {
    Aklas = 0,
    Weapons = {},
    Pets = {},
    Multipliers = {
        Damage = 1,
        Money = 1
    }
}
Reward Example:
function GiveKillReward(player)
    local reward = 100 * player.Data.Multipliers.Money
    player.Data.Aklas += reward
end
6. 🐾 Pet (Egg RNG System)
RNG Table Example:
Pets = {
    Common = 60,
    Rare = 25,
    Epic = 10,
    Legendary = 4,
    Mythic = 1
}
Open Egg:
function RollPet()
    local roll = math.random(1,100)
    -- logic here
end
7. 🛒 Shop + Monetization (Roblox Setup)
Gamepasses Setup:

➡️ Roblox Studio → Monetization → Gamepasses

Name	Price
MVP	1234
Best Gun	2999
Best Pet	2999
MVP System Script:
if MarketplaceService:UserOwnsGamePassAsync(player.UserId, MVP_ID) then
    player.Data.Multipliers = {
        Damage = 1.4,
        Money = 1.4
    }
end
8. 👑 Owner System
if player.Name == "C.Rivals" then
    player.Data.Multipliers = {
        Damage = 2.1,
        Money = 2.1
    }

    player:SetAttribute("Role", "Owner")
end
9. 🎨 UI System (Modern Roblox UI)
UI Principles:

Tween animations (TweenService)

Blur background when menus open

Minimalist neon style

10. 🔐 Data Saving (CRITICAL)
DataStore Setup:
local DataStore = game:GetService("DataStoreService")
local PlayerStore = DataStore:GetDataStore("PlayerData")
Save:
PlayerStore:SetAsync(player.UserId, player.Data)
Load:
local data = PlayerStore:GetAsync(player.UserId)
11. 🧪 Anti-Cheat (Basic)

Server checks:

Damage values

Fire rate

Kick if abnormal

if damage > MAX_DAMAGE then
    player:Kick("Exploit detected")
end
12. 🧱 Build Order (VERY IMPORTANT)
✅ Phase 1 (Core)

Movement

Basic gun

Simple arena

Aklas system

✅ Phase 2

13. 🧠 External Core (C# Backend Integration)

For powerful external logic like matchmaking, analytics, or global state, run the backend server in `backend/`:
- `dotnet run` from `backend`
- Calls are available at `http://localhost:5000`
- Roblox uses `HttpService` from server scripts to talk to the backend (see `RobloxProject/ServerScriptService/ExternalCoreIntegration.lua`).

This gives you a robust external core (closer to Unreal-like server architecture) while keeping game logic in Roblox client/server.


Shop UI

Inventory

Data saving

✅ Phase 3

Pets + eggs

Gamepasses

✅ Phase 4

Polish + effects

UI animations

Events

13. 🎯 Advanced Features (Upgrade Your Game)

🔄 Matchmaking system

🏆 Ranked PvP

👥 Party system

🔁 Trading system

🌍 Open world expansion

14. ⚠️ Key Improvements Over Your Original Idea

Structured as multi-place experience

Proper server-client separation

Scalable systems (modules)

Anti-exploit built-in

Clean Roblox Studio workflow

## Roblox Project Scaffold
The `RobloxProject` folder contains a folder-structured script scaffold for your game:
- `ReplicatedStorage/Modules` for shared systems
- `ReplicatedStorage/Remotes` for remote event names
- `ServerScriptService` for backend handlers and anti-cheat
- `StarterPlayer/StarterPlayerScripts` for client UI, input, and effects
- `StarterGui/MainUI` placeholder for UI elements

### Next steps
1. Open Roblox Studio
2. Create the `ReplicatedStorage`, `ServerScriptService`, `StarterPlayerScripts`, and GUI structure exactly
3. Copy each Lua file into the corresponding script/module in Roblox Studio
4. Add RemoteEvents in ReplicatedStorage: `DealDamage`, `BuyItem`, `OpenEgg`, `EquipWeapon`
5. Wire up UI buttons to `BuyItem` and `OpenEgg` remotes
6. Set up TeleportService place IDs in `GameManager.lua`
7. Publish and test in a two-place experience (Hub and Arena)

## Game Build Status: Full Roblox Core Implemented

Your scaffold now includes:
- Full combat + anti-cheat remote flow
- Shop buying and equip weapon remote flow
- Pet egg RNG and UI remote feedback
- Teleport hub/arena remotes
- Data persistence and owner/gamepass multiplier support
- Exact recommended folder structure across services

### How to run in Roblox Studio
1. Create `ReplicatedStorage` + `Remotes` folder.
2. Paste each Lua file from `RobloxProject` into matching service modules.
3. Add `RemoteEvent` instances in `ReplicatedStorage.Remotes` for `DealDamage`, `BuyItem`, `OpenEgg`, `EquipWeapon`, `TeleportToArena`, `TeleportToHub`.
4. In `ServerScriptService`, insert a Script running `GameInit.lua` plus required module scripts.
5. Add Tool in StarterPack with Handle and local script for firing.
6. Set place IDs in `GameManager.lua`, then test teleport.

### Quick note
For a playable game, implement the studio UI frames named in `MainUIController.lua` and connect the same button names.

## 🧩 Quick Setup: Build all sections automatically
If you saw a video where the creator unfolded folders and the game appeared, this is the same idea: run a bootstrap script once and it creates every required section/object tree.

### 1) In Roblox Studio command bar (or a temporary Script), paste:
-- copy the entire contents of `RobloxProject/StudioBootstrap.lua` and run.

### 2) After bootstrap runs:
- `ReplicatedStorage` will have `Modules` and `Remotes` precreated.
- `ServerScriptService` will have core script placeholders.
- `StarterPlayerScripts` will have local scripts.
- `StarterGui -> MainUI` and named frames are created.
- `Workspace` sections (`Map`, `SpawnPoints`, `ArenaZones`, `Decorative`) are created.

### 3) Then paste your Lua code from `RobloxProject` files into those created scripts + modules.

### 4) Set your place IDs in `ServerScriptService/GameManager.lua`:
GameManager:SetPlaceIds(HUB_PLACE_ID, ARENA_PLACE_ID)

### 5) For a full run: add one Script in ServerScriptService with:
require(script.GameInit)

*Now the game should run with your actual code (not skeleton).
