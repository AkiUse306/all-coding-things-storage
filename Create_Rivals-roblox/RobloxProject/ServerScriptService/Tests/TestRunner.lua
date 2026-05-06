local TestRunner = {}
local CombatSystemTest = require(script.CombatSystemTest)
local AntiCheatTest = require(script.AntiCheatTest)

function TestRunner:RunAll()
    print("Running Roblox tests...")
    CombatSystemTest:Run()
    AntiCheatTest:Run()
    print("Roblox tests completed")
end

return TestRunner