local ReplicatedStorage = game:GetService("ReplicatedStorage")
local DataManager = require(script.DataManager)
local RemoteInit = require(script.RemoteInit)
local CombatHandler = require(script.CombatHandler)
local EconomyHandler = require(script.EconomyHandler)
local PetHandler = require(script.PetHandler)
local TeleportHandler = require(script.TeleportHandler)
local GraphicsService = require(script.GraphicsService)
local GameManager = require(script.GameManager)
local TestRunner = require(script.Tests.TestRunner)

RemoteInit:CreateRemotes()

-- load place IDs from config (update GameManagerConfig) or override manually
GameManager:LoadFromConfig()

-- optional defaults for local quick start if config is blank
if GameManager.HubPlaceId == 0 and GameManager.ArenaPlaceId == 0 then
    GameManager:SetPlaceIds(123456789, 987654321)
end

print("Game server initialized: remotes ready, modules loaded")

-- Run test suite in development
spawn(function()
    TestRunner:RunAll()
end)
