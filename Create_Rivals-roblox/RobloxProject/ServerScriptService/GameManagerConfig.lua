local GameManagerConfig = {
    HubPlaceId = 0,
    ArenaPlaceId = 0,
}

function GameManagerConfig:GetPlaceIds()
    return self.HubPlaceId, self.ArenaPlaceId
end

function GameManagerConfig:SetPlaceIds(hubId, arenaId)
    self.HubPlaceId = hubId or self.HubPlaceId
    self.ArenaPlaceId = arenaId or self.ArenaPlaceId
end

return GameManagerConfig