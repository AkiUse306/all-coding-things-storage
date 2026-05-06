local Players = game:GetService("Players")
local function addNametag(player)
    player.CharacterAdded:Connect(function(char)
        local head = char:WaitForChild("Head", 5)
        if head then
            local billboard = Instance.new("BillboardGui")
            billboard.Name = "NameTag"
            billboard.Adornee = head
            billboard.Size = UDim2.new(0, 150, 0, 50)
            billboard.StudsOffset = Vector3.new(0, 2.3, 0)
            billboard.AlwaysOnTop = true
            local label = Instance.new("TextLabel", billboard)
            label.Size = UDim2.new(1,0,1,0)
            label.BackgroundTransparency = 1
            label.Text = player.Name
            label.TextColor3 = Color3.fromRGB(255,255,255)
            label.TextStrokeColor3 = Color3.fromRGB(0,0,0)
            label.TextStrokeTransparency = 0.3
            label.TextScaled = true
            billboard.Parent = player.PlayerGui
        end
    end)
end
for _, p in ipairs(Players:GetPlayers()) do addNametag(p) end
Players.PlayerAdded:Connect(addNametag)
