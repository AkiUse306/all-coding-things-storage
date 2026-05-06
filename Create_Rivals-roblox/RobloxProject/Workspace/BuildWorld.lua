-- Workspace world builder for Studio. Run this in Studio to generate the full Workspace scene.
local workspace = game:GetService("Workspace")

local function createPart(name, size, cframe, color, parent)
    parent = parent or workspace
    local part = Instance.new("Part")
    part.Name = name
    part.Size = size
    part.CFrame = cframe
    part.Anchored = true
    part.CanCollide = true
    part.BrickColor = BrickColor.new(color)
    part.Parent = parent
    return part
end

local mapFolder = workspace:FindFirstChild("Map") or Instance.new("Folder", workspace)
mapFolder.Name = "Map"

local spawnFolder = workspace:FindFirstChild("SpawnPoints") or Instance.new("Folder", workspace)
spawnFolder.Name = "SpawnPoints"

local arenaFolder = workspace:FindFirstChild("ArenaZones") or Instance.new("Folder", workspace)
arenaFolder.Name = "ArenaZones"

local decorativeFolder = workspace:FindFirstChild("Decorative") or Instance.new("Folder", workspace)
decorativeFolder.Name = "Decorative"

-- Create ground
createPart("Ground", Vector3.new(200, 2, 200), CFrame.new(0, -1, 0), "Dark stone grey", mapFolder)

-- Hub area
createPart("HubPlatform", Vector3.new(80, 2, 80), CFrame.new(-50, 1, 0), "Medium stone grey", mapFolder)

-- Arena area
createPart("ArenaPlatform", Vector3.new(80, 2, 80), CFrame.new(50, 1, 0), "Really black", mapFolder)

-- Spawn points
local spawn1 = Instance.new("SpawnLocation")
spawn1.Name = "Spawn1"
spawn1.Size = Vector3.new(6,1,6)
spawn1.CFrame = CFrame.new(-50, 3, 0)
spawn1.Anchored = true
spawn1.Parent = spawnFolder

local spawn2 = Instance.new("SpawnLocation")
spawn2.Name = "Spawn2"
spawn2.Size = Vector3.new(6,1,6)
spawn2.CFrame = CFrame.new(50, 3, 0)
spawn2.Anchored = true
spawn2.Parent = spawnFolder

-- Arena zone markers
createPart("ArenaZone1", Vector3.new(5, 10, 80), CFrame.new(50, 5, 0), "Bright red", arenaFolder).Transparency = 0.8
createPart("ArenaZone2", Vector3.new(80, 10, 5), CFrame.new(50, 5, 0), "Bright red", arenaFolder).Transparency = 0.8

-- Decorative graffiti wall
local wall = createPart("GraffitiWall", Vector3.new(20, 10, 1), CFrame.new(0, 5, -90), "Cyan", decorativeFolder)
local decal = Instance.new("Decal")
decal.Texture = "rbxassetid://7831368732" -- valid graffiti style texture
decal.Face = Enum.NormalId.Front
decal.Parent = wall

-- Add lighting and atmosphere details
local lighting = game:GetService("Lighting")
lighting.TimeOfDay = "14:00:00"
lighting.Brightness = 2
lighting.OutdoorAmbient = Color3.fromRGB(150, 150, 160)

print("Workspace world built: Hub, Arena, SpawnPoints, Decorative created.")
