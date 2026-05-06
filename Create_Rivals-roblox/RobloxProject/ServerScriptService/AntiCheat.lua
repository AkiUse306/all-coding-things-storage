local AntiCheat = {}
local MAX_DAMAGE = 250
local MAX_FIRE_RATE = 0.08
local MinFireRate = 0.08
local playerLastFire = {}

function AntiCheat:ValidateDamage(player, damage)
    if type(damage) ~= "number" or damage <= 0 or damage > MAX_DAMAGE then
        if player and player:IsDescendantOf(game) then
            player:Kick("Exploit detected: invalid damage")
        end
        return false
    end
    return true
end

function AntiCheat:ValidateFireRate(player, fireRate)
    local userId = player and player.UserId
    if not userId or type(fireRate) ~= "number" or fireRate <= 0 then
        return false
    end

    local last = playerLastFire[userId] or 0
    local delta = tick() - last
    playerLastFire[userId] = tick()

    if fireRate < MinFireRate or delta < MinFireRate * 0.85 then
        if player and player:IsDescendantOf(game) then
            player:Kick("Exploit detected: fire rate too high")
        end
        return false
    end

    return true
end

return AntiCheat
