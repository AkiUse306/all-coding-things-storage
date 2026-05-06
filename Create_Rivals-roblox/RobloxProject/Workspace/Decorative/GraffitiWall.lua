local Graffiti = {}

function Graffiti:MakeWall()
    local wall = Instance.new("Part")
    wall.Name = "GraffitiWall"
    wall.Size = Vector3.new(20, 10, 1)
    wall.Position = Vector3.new(0, 5, -40)
    wall.Anchored = true
    wall.BrickColor = BrickColor.new("Bright violet")
    wall.Parent = workspace.Decorative
    local decal = Instance.new("Decal")
    decal.Texture = "rbxassetid://7831368732" -- valid sample graffiti texture
    decal.Face = Enum.NormalId.Front
    decal.Parent = wall
end

return Graffiti