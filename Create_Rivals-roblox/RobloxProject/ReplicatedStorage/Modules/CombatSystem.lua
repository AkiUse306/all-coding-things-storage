local CombatSystem = {}

local MAX_DAMAGE = 400
local MAX_RANGE = 500
local WeaponConfig = require(game.ReplicatedStorage.Modules.WeaponConfig)
local PlayerStats = require(game.ReplicatedStorage.Modules.PlayerStats)

function CombatSystem:GetDamageFromWeapon(weaponName, player)
    local base = 15
    if WeaponConfig.Primary[weaponName] then
        base = WeaponConfig.Primary[weaponName].Damage
    elseif WeaponConfig.Melee[weaponName] then
        base = WeaponConfig.Melee[weaponName].Damage
    elseif weaponName == "SMG" then
        base = 10
    elseif weaponName == "Rifle" then
        base = 28
    end

    local multiplier = 1
    if player and player.Data and player.Data.Multipliers then
        multiplier = player.Data.Multipliers.Damage or 1
    end
    return math.clamp(base * multiplier, 1, MAX_DAMAGE)
end

function CombatSystem:FireWeapon(player, origin, targetPosition)
    if not origin or not targetPosition or not player or not player.Character then
        return nil, nil
    end
    local direction = targetPosition - origin
    local distance = direction.Magnitude
    if distance <= 0 or distance > MAX_RANGE then
        return nil, nil
    end

    local params = RaycastParams.new()
    params.FilterDescendantsInstances = {player.Character}
    params.FilterType = Enum.RaycastFilterType.Blacklist

    local result = workspace:Raycast(origin, direction.Unit * distance, params)
    if result and result.Instance and result.Instance.Parent then
        local humanoid = result.Instance.Parent:FindFirstChild("Humanoid")
        if humanoid then
            return humanoid, result.Position
        end
    end

    return nil, nil
end

function CombatSystem:ValidateDamage(player, damage)
    if not player or type(damage) ~= "number" then
        return false
    end
    if damage <= 0 or damage > MAX_DAMAGE then
        return false
    end
    return true
end

function CombatSystem:ApplyDamage(attacker, targetHumanoid, damage)
    if not targetHumanoid or targetHumanoid.Health <= 0 then
        return false, false
    end
    targetHumanoid:TakeDamage(damage)
    if attacker and attacker.Data and attacker.Data.Session then
        attacker.Data.Session.DamageDone = (attacker.Data.Session.DamageDone or 0) + damage
    end

    local wasKill = targetHumanoid.Health <= 0
    if wasKill and attacker then
        self:OnPlayerKill(attacker)
    end
    return true, wasKill
end

function CombatSystem:OnPlayerKill(player)
    if not player or not player.Data then return 0 end
    local economy = require(game.ReplicatedStorage.Modules.EconomySystem)
    local amount = economy:GiveKillReward(player)
    if player.Data.Session then
        player.Data.Session.Kills = (player.Data.Session.Kills or 0) + 1
    end
    if player.Data.Stats then
        player.Data.Stats.Kills = (player.Data.Stats.Kills or 0) + 1
        PlayerStats:AddXP(player.Data.Stats, 20)
    end
    return amount
end

return CombatSystem
