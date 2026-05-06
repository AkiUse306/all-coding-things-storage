local ReplicatedStorage = game:GetService("ReplicatedStorage")
local OpenEgg = ReplicatedStorage:WaitForChild("Remotes"):WaitForChild("OpenEgg")
local PetSystem = require(ReplicatedStorage.Modules.PetSystem)
local DataManager = require(script.Parent.DataManager)

local PetHandler = {}

OpenEgg.OnServerEvent:Connect(function(player)
    local petName, rank = PetSystem:RollEgg(player)
    if petName then
        OpenEgg:FireClient(player, true, petName, rank)
        DataManager:EmitPlayerDataUpdate(player)
    else
        OpenEgg:FireClient(player, false, "egg error")
    end
end)

return PetHandler
