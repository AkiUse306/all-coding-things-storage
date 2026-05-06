-- StudioAutoBuilder: run this in Roblox Studio command bar or as a temporary Script to auto-create the full game structure and code.
-- Copy entire file content in Studio's Command Bar and press Enter.
local game = game
local Players = game:GetService("Players")
local ReplicatedStorage = game:GetService("ReplicatedStorage")
local ServerScriptService = game:GetService("ServerScriptService")
local StarterPlayer = game:GetService("StarterPlayer")
local StarterGui = game:GetService("StarterGui")
local StarterPack = game:GetService("StarterPack")

local function createIfMissing(parent, className, name)
    local existing = parent:FindFirstChild(name)
    if existing then return existing end
    local obj = Instance.new(className)
    obj.Name = name
    obj.Parent = parent
    return obj
end

-- Create section folders
local sections = {
    {parent = ReplicatedStorage, name = "Modules", class="Folder"},
    {parent = ReplicatedStorage, name = "Remotes", class="Folder"},
    {parent = ServerScriptService, name = "GameScripts", class="Folder"},
    {parent = StarterPlayer, name = "StarterPlayerScripts", class="Folder"},
    {parent = StarterGui, name = "MainUI", class="ScreenGui"},
    {parent = workspace, name = "Map", class="Folder"},
    {parent = workspace, name = "SpawnPoints", class="Folder"},
    {parent = workspace, name = "ArenaZones", class="Folder"},
    {parent = workspace, name = "Decorative", class="Folder"},
}

for _, section in ipairs(sections) do
    createIfMissing(section.parent, section.class, section.name)
end

local moduleFolder = ReplicatedStorage:FindFirstChild("Modules")
local remotesFolder = ReplicatedStorage:FindFirstChild("Remotes")

local remoteNames = {"DealDamage","BuyItem","OpenEgg","EquipWeapon","TeleportToArena","TeleportToHub"}
for _, rn in ipairs(remoteNames) do
    createIfMissing(remotesFolder, "RemoteEvent", rn)
end

local function setSource(container, className, name, source)
    local obj = createIfMissing(container, className, name)
    if obj:IsA("ModuleScript") or obj:IsA("Script") or obj:IsA("LocalScript") then
        obj.Source = source
    end
    return obj
end

-- Modules
setSource(moduleFolder, "ModuleScript", "WeaponConfig", [[
local WeaponConfig = { Primary = { Pistol = {Damage=15, FireRate=0.3, Price=150}, SMG={Damage=10, FireRate=0.12, Price=550}, Rifle={Damage=28,FireRate=0.45,Price=1200} }, Melee={Knife={Damage=20,FireRate=0.7,Price=80}} }
return WeaponConfig
]])
setSource(moduleFolder, "ModuleScript", "RNGSystem", [[
local RNGSystem = { Pets={Common=60,Rare=25,Epic=10,Legendary=4,Mythic=1} }
function RNGSystem:RollRank() local r=math.random(1,100); if r<=self.Pets.Mythic then return "Mythic" elseif r<=self.Pets.Mythic+self.Pets.Legendary then return "Legendary" elseif r<=self.Pets.Mythic+self.Pets.Legendary+self.Pets.Epic then return "Epic" elseif r<=self.Pets.Mythic+self.Pets.Legendary+self.Pets.Epic+self.Pets.Rare then return "Rare" end return "Common" end
return RNGSystem
]])
setSource(moduleFolder, "ModuleScript", "PetSystem", [[
local PetSystem = {}
local RNG = require(script.Parent.RNGSystem)
PetSystem.PetsByRank={Common={"Sparky","Reno"},Rare={"Kitsu","Blaze"},Epic={"Astra","Titan"},Legendary={"Phantom","Nova"},Mythic={"Akinion"}}
function PetSystem:RollEgg(player)
 local rank=RNG:RollRank(); local pet=self.PetsByRank[rank][math.random(#self.PetsByRank[rank])]
 if not player.Data.Pets then player.Data.Pets={} end
 table.insert(player.Data.Pets,{Name=pet,Rank=rank,Time=os.time()})
 return pet,rank
end
return PetSystem
]])
setSource(moduleFolder, "ModuleScript", "EconomySystem", [[
local Economy = {}
function Economy:CreatePlayerData() return {Aklas=0,Weapons={"Pistol"},Pets={},Multipliers={Damage=1,Money=1},EquippedWeapon="Pistol"} end
function Economy:GiveKillReward(player) if not player.Data then return 0 end local reward=100*(player.Data.Multipliers.Money or 1); player.Data.Aklas=(player.Data.Aklas or 0)+reward; return reward end
function Economy:Charge(player,amount) if not player.Data then return false end if player.Data.Aklas>=amount then player.Data.Aklas=player.Data.Aklas-amount; return true end return false end
function Economy:ApplyGamepass(player,id) if not player.Data then return end player.Data.Multipliers={Damage=1.4,Money=1.4} end
return Economy
]])
setSource(moduleFolder, "ModuleScript", "PlayerStats", [[
local PlayerStats={}
function PlayerStats:GetDefaultStats() return {Level=1,XP=0,Kills=0,Deaths=0,BestStreak=0} end
return PlayerStats
]])
setSource(moduleFolder, "ModuleScript", "CombatSystem", [[
local CombatSystem={}
function CombatSystem:GetDamage(weapon,player) local base=20 if weapon=="Rifle" then base=28 elseif weapon=="SMG" then base=10 end local mult=(player.Data and player.Data.Multipliers and player.Data.Multipliers.Damage or 1) return base*mult end
function CombatSystem:ApplyDamage(attacker,target,dmg) if target and target.Health>0 then target:TakeDamage(dmg); if attacker.Data and attacker.Data.Session then attacker.Data.Session.DamageDone=(attacker.Data.Session.DamageDone or 0)+dmg end; if target.Health<=0 then local econ=require(game.ReplicatedStorage.Modules.EconomySystem); econ:GiveKillReward(attacker) end end end
return CombatSystem
]])

-- Server scripts
local sm = ServerScriptService
setSource(sm, "Script", "GameManager", [[
local TeleportService=game:GetService("TeleportService")
local GameManager={Hub=0,Arena=0}
function GameManager:SetPlaceIds(hub,arena) self.Hub=hub; self.Arena=arena end
function GameManager:TeleportToArena(player) if self.Arena~=0 then TeleportService:Teleport(self.Arena,player) end end
function GameManager:TeleportToHub(player) if self.Hub~=0 then TeleportService:Teleport(self.Hub,player) end end
return GameManager
]])
setSource(sm, "Script", "AntiCheat", [[
local AntiCheat={}
function AntiCheat:ValidateDamage(player,dmg) return dmg>0 and dmg<=500 end
return AntiCheat
]])
setSource(sm, "Script", "DataManager", [[
local Players=game:GetService("Players")
local DataManager={}
local Economy=require(game.ReplicatedStorage.Modules.EconomySystem)
local Stats=require(game.ReplicatedStorage.Modules.PlayerStats)
function DataManager:Create(player)
 player.Data=Economy:CreatePlayerData(); player.Data.Stats=Stats:GetDefaultStats(); player.Data.Session={Kills=0,DamageDone=0,Start=os.time()}
 if player.Name=="C.Rivals" then player:SetAttribute("Role","Owner"); player.Data.Multipliers={Damage=2.1,Money=2.1} end
end
Players.PlayerAdded:Connect(function(p) DataManager:Create(p) end)
Players.PlayerRemoving:Connect(function(p) end)
return DataManager
]])
setSource(sm, "Script", "CombatHandler", [[
local Remotes=game.ReplicatedStorage.Remotes
local Combat=require(game.ReplicatedStorage.Modules.CombatSystem)
local AntiCheat=require(script.Parent.AntiCheat)
Remotes.DealDamage.OnServerEvent:Connect(function(player, dmg, target)
 if not AntiCheat:ValidateDamage(player,dmg) then return end
 if target and target:FindFirstChild("Humanoid") then Combat:ApplyDamage(player,target.Humanoid,dmg) end
end)
]])
setSource(sm, "Script", "EconomyHandler", [[
local Remotes=game.ReplicatedStorage.Remotes
local WConfig=require(game.ReplicatedStorage.Modules.WeaponConfig)
local Economy=require(game.ReplicatedStorage.Modules.EconomySystem)
Remotes.BuyItem.OnServerEvent:Connect(function(p, cat, name)
 local cfg=WConfig[cat] and WConfig[cat][name]
 if cfg and Economy:Charge(p,cfg.Price) then table.insert(p.Data.Weapons,name); Remotes.BuyItem:FireClient(p,true,name) else Remotes.BuyItem:FireClient(p,false,"no funds") end
end)
Remotes.EquipWeapon.OnServerEvent:Connect(function(p,name)
 for _,w in ipairs(p.Data.Weapons or {}) do if w==name then p.Data.EquippedWeapon=name; Remotes.EquipWeapon:FireClient(p,true,name); return end end Remotes.EquipWeapon:FireClient(p,false,"not owned")
end)
]])
setSource(sm, "Script", "PetHandler", [[
local Remotes=game.ReplicatedStorage.Remotes
local Pet=require(game.ReplicatedStorage.Modules.PetSystem)
Remotes.OpenEgg.OnServerEvent:Connect(function(p)
 local pet,rank=Pet:RollEgg(p)
 if pet then Remotes.OpenEgg:FireClient(p,true,pet,rank) else Remotes.OpenEgg:FireClient(p,false,"fail") end
end)
]])
setSource(sm, "Script", "TeleportHandler", [[
local Remotes=game.ReplicatedStorage.Remotes
local GM=require(script.Parent.GameManager)
Remotes.TeleportToArena.OnServerEvent:Connect(function(p) GM:TeleportToArena(p) end)
Remotes.TeleportToHub.OnServerEvent:Connect(function(p) GM:TeleportToHub(p) end)
]])
setSource(sm, "Script", "GameInit", [[
require(script.DataManager)
require(script.CombatHandler)
require(script.EconomyHandler)
require(script.PetHandler)
require(script.TeleportHandler)
print("GameInit loaded")
]])

-- StarterPlayer scripts
local sps = StarterPlayer:FindFirstChild("StarterPlayerScripts")
setSource(sps, "LocalScript", "WeaponLocalScript", [[
local player=game.Players.LocalPlayer
local remotes=game.ReplicatedStorage.Remotes
local tool=player:WaitForChild("Backpack"):WaitForChild("StarterGun")
tool.Activated:Connect(function()
 local target=player:GetMouse().Target
 if target and target.Parent and target.Parent:FindFirstChild("Humanoid") then remotes.DealDamage:FireServer(25,target.Parent) end
end)
]])
setSource(sps, "LocalScript", "MainUIController", [[
local player=game.Players.LocalPlayer
local gui=player:WaitForChild("PlayerGui"):WaitForChild("MainUI")
local remotes=game.ReplicatedStorage.Remotes
local shop=gui:FindFirstChild("ShopFrame")
if shop then local btn=Instance.new("TextButton",shop); btn.Text="Buy Pistol"; btn.Size=UDim2.new(0,120,0,40); btn.Position=UDim2.new(0,0,0,0); btn.MouseButton1Click:Connect(function() remotes.BuyItem:FireServer("Primary","Pistol") end) end
remotes.OpenEgg.OnClientEvent:Connect(function(succ,pet,rank) print("Egg:"..tostring(pet).." "..tostring(rank)) end)
]])

-- StarterGui UI elements
local gui = StarterGui:FindFirstChild("MainUI")
for _, name in ipairs({"ShopFrame","InventoryFrame","PetFrame","StatsFrame","EggOpeningUI"}) do
 local f=gui:FindFirstChild(name) or Instance.new("Frame",gui)
 f.Name=name; f.Size=UDim2.new(0.32,0,0.25,0); f.Position=UDim2.new(0.02,0,0.02,0); f.BackgroundColor3=Color3.fromRGB(20,20,20)
end

-- Starter tool
local tool = createIfMissing(StarterPack, "Tool", "StarterGun")
if not tool:FindFirstChild("Handle") then
 local handle = Instance.new("Part", tool)
 handle.Name="Handle"; handle.Size=Vector3.new(1,1,3); handle.Anchored=false
end

print("StudioAutoBuilder done. Check Explorer sections and unfold the created tree.")
print("Then run GameInit from ServerScriptService to start server systems.")
