local EconomySystem = {}

function EconomySystem:CreatePlayerData()
    return {
        Aklas = 0,
        Weapons = {"Pistol"},
        Pets = {},
        Multipliers = {Damage = 1, Money = 1},
    }
end

function EconomySystem:GiveKillReward(player)
    if not player or not player.Data then return 0 end
    local reward = 100 * (player.Data.Multipliers.Money or 1)
    player.Data.Aklas = (player.Data.Aklas or 0) + reward
    return reward
end

function EconomySystem:Charge(player, amount)
    if not player or not player.Data then return false end
    if player.Data.Aklas >= amount then
        player.Data.Aklas = player.Data.Aklas - amount
        return true
    end
    return false
end

function EconomySystem:ApplyGamepassMultipliers(player, gamepassId)
    if not player or not player.Data then return end
    if not player.Data.Multipliers then
        player.Data.Multipliers = {Damage = 1, Money = 1}
    end
    if gamepassId == "MVP" then
        player.Data.Multipliers.Damage = 1.4
        player.Data.Multipliers.Money = 1.4
    elseif gamepassId == "BestGun" then
        player.Data.Multipliers.Damage = player.Data.Multipliers.Damage + 0.35
    elseif gamepassId == "BestPet" then
        player.Data.Multipliers.Money = player.Data.Multipliers.Money + 0.35
    end
end

return EconomySystem
