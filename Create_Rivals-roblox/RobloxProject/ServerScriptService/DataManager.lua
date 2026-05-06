local DataStoreService = game:GetService("DataStoreService")
local Players = game:GetService("Players")
local ReplicatedStorage = game:GetService("ReplicatedStorage")
local PlayerStore = DataStoreService:GetDataStore("PlayerData")
local Economy = require(game.ReplicatedStorage.Modules.EconomySystem)
local PlayerStats = require(game.ReplicatedStorage.Modules.PlayerStats)
local PlayerDataUpdated = ReplicatedStorage:WaitForChild("Remotes"):WaitForChild("PlayerDataUpdated")

local DataManager = {}

function DataManager:CreatePlayerData(userId)
    local data = Economy:CreatePlayerData()
    data.Stats = PlayerStats:GetDefaultStats()
    data.Session = {Kills = 0, DamageDone = 0, StartTime = os.time()}
    return data
end

function DataManager:EmitPlayerDataUpdate(player)
    if not player or not player.Data then return end
    PlayerDataUpdated:FireClient(player, player.Data)
end


function DataManager:LoadPlayer(player)
    local success, storedData = pcall(function()
        return PlayerStore:GetAsync("player_" .. tostring(player.UserId))
    end)
    if success and storedData then
        player.Data = storedData
        return true
    end
    player.Data = self:CreatePlayerData(player.UserId)
    return false
end

function DataManager:SavePlayer(player)
    if not player.Data then return false end
    local success, err = pcall(function()
        PlayerStore:SetAsync("player_" .. tostring(player.UserId), player.Data)
    end)
    if not success then
        warn("Save failed for", player.Name, err)
    end
    return success
end

function DataManager:SetupPlayer(player)
    self:LoadPlayer(player)
    player:SetAttribute("Role", "Player")

    if player.Name == "C.Rivals" then
        player:SetAttribute("Role", "Owner")
        player.Data.Multipliers = {Damage = 2.1, Money = 2.1}
    end

    if player.Data and player.Data.Multipliers then
        player.Data.EquippedWeapon = player.Data.EquippedWeapon or "Pistol"
    end
end

function DataManager:OnPlayerAdded(player)
    self:SetupPlayer(player)
end

function DataManager:OnPlayerRemoving(player)
    self:SavePlayer(player)
end

Players.PlayerAdded:Connect(function(player)
    DataManager:OnPlayerAdded(player)
end)

Players.PlayerRemoving:Connect(function(player)
    DataManager:OnPlayerRemoving(player)
end)

-- Auto save periodically every 60 seconds.
spawn(function()
    while true do
        wait(60)
        for _, player in ipairs(Players:GetPlayers()) do
            DataManager:SavePlayer(player)
        end
    end
end)

return DataManager
