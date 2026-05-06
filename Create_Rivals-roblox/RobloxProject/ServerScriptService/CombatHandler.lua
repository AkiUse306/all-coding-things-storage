local ReplicatedStorage = game:GetService("ReplicatedStorage")
local Players = game:GetService("Players")
local DealDamage = ReplicatedStorage:WaitForChild("Remotes"):WaitForChild("DealDamage")
local CombatSystem = require(ReplicatedStorage.Modules.CombatSystem)
local AntiCheat = require(script.Parent.AntiCheat)
local GraphicsService = require(script.Parent.GraphicsService)
local ExternalCore = require(script.Parent.ExternalCoreIntegration)

local CombatHandler = {}

DealDamage.OnServerEvent:Connect(function(player, weaponName, origin, targetPosition, fireRate)
    if not player or not player.Character or not weaponName or not origin or not targetPosition then
        return
    end

    if not AntiCheat:ValidateFireRate(player, fireRate) then
        return
    end

    local actualWeapon = weaponName
    if player.Data and player.Data.EquippedWeapon then
        actualWeapon = player.Data.EquippedWeapon
    end

    local damage = CombatSystem:GetDamageFromWeapon(actualWeapon, player)
    if not CombatSystem:ValidateDamage(player, damage) then
        return
    end

    local targetHumanoid, impactPos = CombatSystem:FireWeapon(player, origin, targetPosition)
    if not targetHumanoid then
        return
    end

    local success, killed = CombatSystem:ApplyDamage(player, targetHumanoid, damage)
    if impactPos then
        GraphicsService:OnHit(impactPos)
    end

    if killed then
        local targetPlayer = Players:GetPlayerFromCharacter(targetHumanoid.Parent)
        ExternalCore:OnPlayerKill(player, targetPlayer)
        local DataManager = require(script.Parent.DataManager)
        DataManager:EmitPlayerDataUpdate(player)
        if targetPlayer then
            DataManager:EmitPlayerDataUpdate(targetPlayer)
        end
    end
end)

return CombatHandler
