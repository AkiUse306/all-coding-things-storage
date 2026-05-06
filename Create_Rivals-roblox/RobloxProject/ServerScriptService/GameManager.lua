local TeleportService = game:GetService("TeleportService")
local Players = game:GetService("Players")
local Config = require(script.GameManagerConfig)

local GameManager = {
    HubPlaceId = Config.HubPlaceId,
    ArenaPlaceId = Config.ArenaPlaceId,
}

function GameManager:SetPlaceIds(hubPlaceId, arenaPlaceId)
    self.HubPlaceId = hubPlaceId or self.HubPlaceId
    self.ArenaPlaceId = arenaPlaceId or self.ArenaPlaceId
    Config:SetPlaceIds(self.HubPlaceId, self.ArenaPlaceId)
end

function GameManager:LoadFromConfig()
    local hub, arena = Config:GetPlaceIds()
    self.HubPlaceId = hub
    self.ArenaPlaceId = arena
end

function GameManager:TeleportToArena(player)
    if self.ArenaPlaceId == 0 then
        warn("ArenaPlaceId not configured")
        return
    end
    pcall(function()
        TeleportService:Teleport(self.ArenaPlaceId, player)
    end)
end

function GameManager:TeleportToHub(player)
    if self.HubPlaceId == 0 then
        warn("HubPlaceId not configured")
        return
    end
    pcall(function()
        TeleportService:Teleport(self.HubPlaceId, player)
    end)
end

return GameManager
