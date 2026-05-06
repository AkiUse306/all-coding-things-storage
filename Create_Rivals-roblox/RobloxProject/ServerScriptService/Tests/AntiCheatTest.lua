local AntiCheat = require(script.Parent.Parent.AntiCheat)

local Test = {}

function Test:Run()
    print("[AntiCheatTest] Starting")
    local fakePlayer = {UserId = 1, IsDescendantOf = function() return false end}

    assert(AntiCheat:ValidateDamage(fakePlayer, 100), "Normal damage should be valid")
    assert(not AntiCheat:ValidateDamage(fakePlayer, 9999), "Crushing damage should be invalid")
    assert(not AntiCheat:ValidateDamage(fakePlayer, -5), "Negative damage invalid")

    assert(AntiCheat:ValidateFireRate(fakePlayer, 0.1), "Legal fire rate should pass")
    assert(not AntiCheat:ValidateFireRate(fakePlayer, 0.001), "Too-fast fire rate should fail")

    print("[AntiCheatTest] All tests passed")
end

return Test