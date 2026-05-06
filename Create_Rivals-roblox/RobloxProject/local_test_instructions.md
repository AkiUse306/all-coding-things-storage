# Local Testing Guide (exact quick test setup)

This is the local test checklist for one-player debugging in Studio.

1) Open your place in Roblox Studio.
2) In Explorer, ensure the folder structure exists:
   - `ReplicatedStorage/Modules`
   - `ReplicatedStorage/Remotes`
   - `ServerScriptService`
   - `StarterPlayer/StarterPlayerScripts`
   - `StarterGui/MainUI`
   - `Workspace/Map`, `Workspace/SpawnPoints`, `Workspace/ArenaZones`, `Workspace/Decorative`

3) Create these remote events in `ReplicatedStorage.Remotes`:
   - DealDamage
   - BuyItem
   - OpenEgg
   - EquipWeapon
   - TeleportToArena
   - TeleportToHub

4) Add Script objects and paste code:
   - `ServerScriptService/GameInit` (require all managers)
   - `ServerScriptService/DataManager`
   - `ServerScriptService/CombatHandler`
   - `ServerScriptService/EconomyHandler`
   - `ServerScriptService/PetHandler`
   - `ServerScriptService/TeleportHandler`
   - `ServerScriptService/GameManager`
   - `ServerScriptService/RemoteInit`
   - `ServerScriptService/GraphicsService`
   - `ServerScriptService/AntiCheat`
   - `ServerScriptService/AdminTag`

5) Add LocalScripts in StarterPlayerScripts:
   - WeaponLocalScript
   - UIController
   - InputHandler
   - CameraEffects
   - NametagLocalScript
   - SkyLocalScript

6) Add `ScreenGui` in StarterGui named `MainUI`, then add `MainUI` and `MainUIController` scripts.

7) Add the weapon tool in StarterPack called `StarterGun` with a `Handle`.

8) Set place IDs in `ServerScriptService/GameManager.lua`:
   GameManager:SetPlaceIds(HUB_PLACE_ID, ARENA_PLACE_ID)

9) Create `ServerScriptService/StartGame` script with:
   require(script.GameInit)

10) Run local Play test:
   - open `MainUI`
   - buy pistol
   - open egg
   - fire at enemy
   - teleport arena/hub

11) If there are errors, open Output and fix missing remotes/script names.

This local test setup exactly matches the full game and is for quick one-player debug before multi-player publish.
