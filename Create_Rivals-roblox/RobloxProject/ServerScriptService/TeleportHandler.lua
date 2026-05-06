local ReplicatedStorage = game:GetService("ReplicatedStorage")
local TeleportService = game:GetService("TeleportService")
local TeleportToArena = ReplicatedStorage:WaitForChild("Remotes"):WaitForChild("TeleportToArena")
local TeleportToHub = ReplicatedStorage:WaitForChild("Remotes"):WaitForChild("TeleportToHub")

local GameManager = require(script.GameManager)

TeleportToArena.OnServerEvent:Connect(function(player)
    GameManager:TeleportToArena(player)
end)

TeleportToHub.OnServerEvent:Connect(function(player)
    GameManager:TeleportToHub(player)
end)

return true
