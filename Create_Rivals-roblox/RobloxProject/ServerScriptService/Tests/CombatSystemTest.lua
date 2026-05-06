local CombatSystem = require(game.ReplicatedStorage.Modules.CombatSystem)
local EconomySystem = require(game.ReplicatedStorage.Modules.EconomySystem)
local PlayerStats = require(game.ReplicatedStorage.Modules.PlayerStats)

local Test = {}

function Test:Run()
    print("[CombatSystemTest] Starting")

    local fakePlayer = { Data = { Multipliers = { Damage = 2, Money = 2}, Session = { DamageDone = 0, Kills = 0 }, Stats = { Kills = 0, XP = 0, Level = 1 }, Weapons = {"Pistol"}, EquippedWeapon = "Pistol" }}

    local damage = CombatSystem:GetDamageFromWeapon("Pistol", fakePlayer)
    assert(damage == 30, "Expected double damage for multiplier 2")

    assert(CombatSystem:ValidateDamage(fakePlayer, damage), "Valid damage should pass")
    assert(not CombatSystem:ValidateDamage(fakePlayer, 99999), "Oversized damage should fail")

    local currentAklas = fakePlayer.Data.Aklas or 0
    fakePlayer.Data.Aklas = currentAklas
    CombatSystem:OnPlayerKill(fakePlayer)

    assert(fakePlayer.Data.Aklas == 100 * 2, "Kill reward should apply with money multiplier")
    assert(fakePlayer.Data.Session.Kills == 1, "Session kills should increment")
    assert(fakePlayer.Data.Stats.Kills == 1, "Total kills should increment")
    assert(fakePlayer.Data.Stats.XP >= 20, "XP should increase on kill")

    print("[CombatSystemTest] All tests passed")
end

return Test