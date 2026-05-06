local GraphicsEngine = {}

function GraphicsEngine:SpawnHitEffect(position)
    local part = Instance.new("Part")
    part.Size = Vector3.new(0.5,0.5,0.5)
    part.Anchored = true
    part.CanCollide = false
    part.Material = Enum.Material.Neon
    part.Color = Color3.new(1,0.5,0)
    part.Position = position
    part.Parent = workspace

    local tweenService = game:GetService("TweenService")
    local tweenInfo = TweenInfo.new(0.35, Enum.EasingStyle.Quad, Enum.EasingDirection.Out)
    local goal = {Transparency = 1, Size = Vector3.new(2,2,2)}
    local tween = tweenService:Create(part, tweenInfo, goal)
    tween:Play()
    tween.Completed:Connect(function()
        part:Destroy()
    end)
end

function GraphicsEngine:SpawnTextEffect(position, text)
    local billboard = Instance.new("BillboardGui")
    billboard.Size = UDim2.new(0, 100, 0, 40)
    billboard.Adornee = Instance.new("Part", workspace)
    billboard.Adornee.Size = Vector3.new(0.2, 0.2, 0.2)
    billboard.Adornee.Transparency = 1
    billboard.Adornee.Anchored = true
    billboard.Adornee.CanCollide = false
    billboard.Adornee.Position = position
    billboard.AlwaysOnTop = true

    local label = Instance.new("TextLabel", billboard)
    label.Size = UDim2.new(1,0,1,0)
    label.BackgroundTransparency = 1
    label.Text = text
    label.TextColor3 = Color3.fromRGB(255, 255, 0)
    label.TextStrokeTransparency = 0.4

    local duration = 1
    delay(duration, function()
        billboard:Destroy()
    end)
end

return GraphicsEngine
