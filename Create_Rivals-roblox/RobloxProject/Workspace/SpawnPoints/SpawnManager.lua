local SpawnManager = {}

function SpawnManager:CreateSpawn(location)
    local spawn = Instance.new("SpawnLocation")
    spawn.Name = "Spawn"
    spawn.Size = Vector3.new(6, 1, 6)
    spawn.Position = location
    spawn.Anchored = true
    spawn.Parent = workspace.SpawnPoints
    return spawn
end

function SpawnManager:CreateDefaultSpawns()
    self:CreateSpawn(Vector3.new(-30, 3, 0))
    self:CreateSpawn(Vector3.new(30, 3, 0))
end

return SpawnManager