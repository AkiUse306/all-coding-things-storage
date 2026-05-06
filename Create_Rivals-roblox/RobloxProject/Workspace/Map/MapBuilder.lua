local MapBuilder = {}

function MapBuilder:BuildBase()
    local workspace = game:GetService("Workspace")
    local existing = workspace:FindFirstChild("MapBase")
    if existing then existing:Destroy() end
    local base = Instance.new("Part")
    base.Name = "MapBase"
    base.Size = Vector3.new(200, 2, 200)
    base.Position = Vector3.new(0, 0, 0)
    base.Anchored = true
    base.Material = Enum.Material.SmoothPlastic
    base.BrickColor = BrickColor.new("Really black")
    base.Parent = workspace
end

return MapBuilder