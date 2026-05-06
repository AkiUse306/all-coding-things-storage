local RunService = game:GetService("RunService")
local Players = game:GetService("Players")
local player = Players.LocalPlayer
local camera = workspace.CurrentCamera

local CameraEffects = {}

function CameraEffects:Shake(intensity, duration)
    local t = 0
    local startCFrame = camera.CFrame
    while t < duration do
        local offset = Vector3.new(math.random() - 0.5, math.random() - 0.5, math.random() - 0.5) * intensity
        camera.CFrame = startCFrame * CFrame.new(offset)
        t = t + RunService.RenderStepped:Wait()
    end
    camera.CFrame = startCFrame
end

return CameraEffects
