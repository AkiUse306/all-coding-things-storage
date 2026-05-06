local Graphics = {}
local ReplicatedStorage = game:GetService("ReplicatedStorage")
local GraphicsEngine = require(ReplicatedStorage.Modules.GraphicsEngine)

function Graphics:OnHit(position)
    GraphicsEngine:SpawnHitEffect(position)
    GraphicsEngine:SpawnTextEffect(position + Vector3.new(0,2,0), "Hit!")
end

return Graphics
