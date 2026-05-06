local ArenaManager = {}

function ArenaManager:MakeArena()
    local arena = Instance.new("Part")
    arena.Name = "ArenaFloor"
    arena.Size = Vector3.new(80, 2, 80)
    arena.Position = Vector3.new(50, 1, 0)
    arena.Anchored = true
    arena.BrickColor = BrickColor.new("Really red")
    arena.Parent = workspace.ArenaZones
end

return ArenaManager