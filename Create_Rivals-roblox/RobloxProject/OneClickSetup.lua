-- OneClick setup script for Roblox Studio
-- Copy this into a Script in Studio and run once to create full project structure.
local ReplicatedStorage = game:GetService("ReplicatedStorage")
local ServerScriptService = game:GetService("ServerScriptService")
local StarterPlayer = game:GetService("StarterPlayer")
local StarterGui = game:GetService("StarterGui")
local Workspace = game:GetService("Workspace")

local function make(parent, className, name)
    local existing = parent:FindFirstChild(name)
    if existing then return existing end
    local obj = Instance.new(className)
    obj.Name = name
    obj.Parent = parent
    return obj
end

local modulesFolder = make(ReplicatedStorage, "Folder", "Modules")
local remotesFolder = make(ReplicatedStorage, "Folder", "Remotes")
local starterPlayerScripts = make(StarterPlayer, "Folder", "StarterPlayerScripts")
local uiRoot = make(StarterGui, "ScreenGui", "MainUI")
local mapFolder = make(Workspace, "Folder", "Map")
local spawnFolder = make(Workspace, "Folder", "SpawnPoints")
local arenaFolder = make(Workspace, "Folder", "ArenaZones")
local decFolder = make(Workspace, "Folder", "Decorative")

local remoteNames = {"DealDamage", "BuyItem", "OpenEgg", "EquipWeapon", "TeleportToArena", "TeleportToHub"}
for _, name in ipairs(remoteNames) do
    make(remotesFolder, "RemoteEvent", name)
end

local function createScript(parent, className, name, source)
    local existing = parent:FindFirstChild(name)
    if existing then
        if existing:IsA("Script") or existing:IsA("LocalScript") or existing:IsA("ModuleScript") then
            existing.Source = source or existing.Source
            return existing
        end
    end
    local obj = Instance.new(className)
    obj.Name = name
    if source and (className == "Script" or className == "LocalScript" or className == "ModuleScript") then
        obj.Source = source
    end
    obj.Parent = parent
    return obj
end

-- Modules
local moduleNames = {"CombatSystem", "WeaponConfig", "RNGSystem", "PetSystem", "EconomySystem", "PlayerStats", "GraphicsEngine"}
for _, name in ipairs(moduleNames) do
    createScript(modulesFolder, "ModuleScript", name, "return {}")
end

-- Server scripts
local serverScripts = {"GameInit", "DataManager", "CombatHandler", "EconomyHandler", "PetHandler", "TeleportHandler", "GameManager", "AntiCheat", "RemoteInit", "GraphicsService", "AdminTag"}
for _, name in ipairs(serverScripts) do
    createScript(ServerScriptService, "Script", name, "print(\"" .. name .. " loaded\")")
end

-- Client scripts
local clientScripts = {"WeaponLocalScript", "UIController", "InputHandler", "CameraEffects", "NametagLocalScript", "SkyLocalScript"}
for _, name in ipairs(clientScripts) do
    createScript(starterPlayerScripts, "LocalScript", name, "print(\"" .. name .. " loaded\")")
end

-- UI structure
local shopFrame = make(uiRoot, "Frame", "ShopFrame")
local eggFrame = make(uiRoot, "Frame", "EggOpeningUI")
local navPanel = make(uiRoot, "Frame", "NavPanel")
make(uiRoot, "TextButton", "ArenaButton")
make(uiRoot, "TextButton", "HubButton")
make(uiRoot, "TextButton", "BuyPistolButton")
make(uiRoot, "TextButton", "RollButton")
make(uiRoot, "TextButton", "MVPButton")

-- Workspace helper scripts
createScript(Workspace, "Script", "BuildWorld", "print(\"BuildWorld loaded\")")
createScript(mapFolder, "Script", "MapBuilder", "print(\"MapBuilder loaded\")")
createScript(spawnFolder, "Script", "SpawnManager", "print(\"SpawnManager loaded\")")
createScript(arenaFolder, "Script", "ArenaManager", "print(\"ArenaManager loaded\")")
createScript(decFolder, "Script", "GraffitiWall", "print(\"GraffitiWall loaded\")")

print("OneClick setup complete. You now have all required places, remotes, and scripts.")
print("Paste code from repo files into each script/module and set GameManager place IDs.")
