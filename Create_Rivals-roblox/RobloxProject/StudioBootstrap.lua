-- Studio bootstrap script (run in command bar or copy into Script)
local ReplicatedStorage = game:GetService("ReplicatedStorage")
local ServerScriptService = game:GetService("ServerScriptService")
local StarterPlayer = game:GetService("StarterPlayer")
local StarterGui = game:GetService("StarterGui")
local Workspace = game:GetService("Workspace")

local function make(className, name, parent)
    local existing = parent:FindFirstChild(name)
    if existing then return existing end
    local obj = Instance.new(className)
    obj.Name = name
    obj.Parent = parent
    return obj
end

local modulesFolder = ReplicatedStorage:FindFirstChild("Modules") or make("Folder", "Modules", ReplicatedStorage)
local remotesFolder = ReplicatedStorage:FindFirstChild("Remotes") or make("Folder", "Remotes", ReplicatedStorage)

for _, name in ipairs({"DealDamage", "BuyItem", "OpenEgg", "EquipWeapon", "TeleportToArena", "TeleportToHub"}) do
    make("RemoteEvent", name, remotesFolder)
end

local function makeScriptIn(parent, className, name, source)
    local existing = parent:FindFirstChild(name)
    if existing then return existing end
    local obj = Instance.new(className)
    obj.Name = name
    if source then obj.Source = source end
    obj.Parent = parent
    return obj
end

for _, name in ipairs({"GameInit", "DataManager", "CombatHandler", "EconomyHandler", "PetHandler", "TeleportHandler", "GameManager", "AntiCheat", "RemoteInit", "GraphicsService", "AdminTag"}) do
    makeScriptIn(ServerScriptService, "Script", name, "print(\"" .. name .. " loaded\")")
end

local starterPlayerScripts = StarterPlayer:FindFirstChild("StarterPlayerScripts") or make("Folder", "StarterPlayerScripts", StarterPlayer)
for _, name in ipairs({"WeaponLocalScript", "UIController", "InputHandler", "CameraEffects", "NametagLocalScript", "SkyLocalScript"}) do
    makeScriptIn(starterPlayerScripts, "LocalScript", name, "print(\"" .. name .. " loaded\")")
end

local mainUI = StarterGui:FindFirstChild("MainUI") or make("ScreenGui", "MainUI", StarterGui)
for _, frameName in ipairs({"ShopFrame", "EggOpeningUI", "NavPanel"}) do
    local frame = mainUI:FindFirstChild(frameName) or make("Frame", frameName, mainUI)
    frame.Size = UDim2.new(0.3, 0, 0.4, 0)
    frame.Position = UDim2.new(0.05, 0, 0.05, 0.05)
    frame.BackgroundColor3 = Color3.fromRGB(20, 20, 20)
end
for _, btnName in ipairs({"ArenaButton", "HubButton", "BuyPistolButton", "RollButton", "MVPButton"}) do
    local btn = mainUI:FindFirstChild(btnName) or make("TextButton", btnName, mainUI)
    btn.Text = btnName
    btn.Size = UDim2.new(0, 120, 0, 30)
    btn.Position = UDim2.new(0.7, 0, 0.1 + (#mainUI:GetChildren() * 0.06), 0)
end

local mapFolder = Workspace:FindFirstChild("Map") or make("Folder", "Map", Workspace)
local spawnFolder = Workspace:FindFirstChild("SpawnPoints") or make("Folder", "SpawnPoints", Workspace)
local arenaFolder = Workspace:FindFirstChild("ArenaZones") or make("Folder", "ArenaZones", Workspace)
local decorativeFolder = Workspace:FindFirstChild("Decorative") or make("Folder", "Decorative", Workspace)
makeScriptIn(Workspace, "Script", "BuildWorld", "print(\"BuildWorld loaded\")")
makeScriptIn(mapFolder, "Script", "MapBuilder", "print(\"MapBuilder loaded\")")
makeScriptIn(spawnFolder, "Script", "SpawnManager", "print(\"SpawnManager loaded\")")
makeScriptIn(arenaFolder, "Script", "ArenaManager", "print(\"ArenaManager loaded\")")
makeScriptIn(decorativeFolder, "Script", "GraffitiWall", "print(\"GraffitiWall loaded\")")

print("Studio bootstrap complete. All remotes, modules, scripts, UI frames, and workspace folders are created.")
print("Copy code from repo files into these objects and set GameManager place IDs.")
