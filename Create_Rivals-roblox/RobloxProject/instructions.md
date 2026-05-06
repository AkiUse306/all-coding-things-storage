# How to Run the Rival Game (Friend Instructions)

## 1) Open Roblox Studio
1. Create a new place (or open your project).
2. Run one-click setup with `RobloxProject/OneClickSetup.lua` (or `RobloxProject/StudioBootstrap.lua`) in Studio. This automatically creates all required folders, remotes, UI objects, and scripts.
3. Verify Explorer has:
   - `ReplicatedStorage` with folders `Modules` and `Remotes`
   - `ServerScriptService`
   - `StarterPlayer > StarterPlayerScripts`
   - `StarterGui > MainUI`
   - `Workspace > Map`, `SpawnPoints`, `ArenaZones`, `Decorative`

## 2) Verify Required Remotes
Make sure these RemoteEvents exist in `ReplicatedStorage.Remotes`:
- `DealDamage`
- `BuyItem`
- `OpenEgg`
- `EquipWeapon`
- `TeleportToArena`
- `TeleportToHub`

(If they are missing, re-run one-click setup script.)

## 3) Paste code from repo files into Studio objects
Use the scripts in `RobloxProject` as reference.

### A) Modules (ReplicatedStorage.Modules)
- `CombatSystem`
- `WeaponConfig`
- `RNGSystem`
- `PetSystem`
- `EconomySystem`
- `PlayerStats`
- `GraphicsEngine`

### B) Server scripts (ServerScriptService)
- `GameInit` (require all handlers)
- `DataManager`
- `CombatHandler`
- `EconomyHandler`
- `PetHandler`
- `TeleportHandler`
- `GameManager` (set place IDs)
- `AntiCheat`
- `RemoteInit`
- `GraphicsService`
- `AdminTag`

### C) Client scripts (StarterPlayerScripts)
- `WeaponLocalScript`
- `UIController`
- `InputHandler`
- `CameraEffects`
- `NametagLocalScript`
- `SkyLocalScript`

### D) UI (StarterGui.MainUI)
- Add `ScreenGui` named `MainUI`
- Add `MainUI.lua` and `MainUIController.lua`
- Create frames and buttons:
  - `ShopFrame`, `EggOpeningUI`, `NavPanel`
  - `ArenaButton`, `HubButton`, `BuyPistolButton`, `RollButton`, `MVPButton`

### E) Workspace code (Workspace folders)
- `BuildWorld` script (build map, spawn points, arena, graffiti)
- `Map/MapBuilder`, `SpawnPoints/SpawnManager`, `ArenaZones/ArenaManager`, `Decorative/GraffitiWall`

## 4) Set place IDs in `GameManager.lua`
Example:
```lua
GameManager:SetPlaceIds(HUB_PLACE_ID, ARENA_PLACE_ID)
```

## 5) Add startup script
In `ServerScriptService`, create a Script `StartGame`:
```lua
require(script.GameInit)
```

## 6) Test Play
1. Play in Studio.
2. Ensure the UI loads and buttons respond.
3. Buy weapon, open egg, equip, fire, teleport.
4. If errors appear, check remote names and module requires.

## 7) Optimize for project
- Add real textures/skins
- Add sounds, animations, and effects
- Add economy balancing and anti-cheat thresholds
- Publish and test in multiplayer

## Quick check
- If `MainUI` doesn’t appear, re-open Player GUI and update script names.
- If remotes are missing, run `RemoteInit` or re-create events manually.
- If data doesn’t save, enable DataStore API in Game Settings.

Good luck — this game is now fully functional and ready to iterate!