local Players = game:GetService("Players")

local adminNames = {"C.Rivals", "YourFriendName"}
local adminTag = "[Owner]"

local function isAdmin(player)
    for _, name in ipairs(adminNames) do
        if player.Name == name then
            return true
        end
    end
    return false
end

Players.PlayerAdded:Connect(function(player)
    if isAdmin(player) then
        player:SetAttribute("Role", "Admin")
        if not player:IsA("Player") then return end
        local function setTag(char)
            local head = char:WaitForChild("Head", 5)
            if head then
                local tag = Instance.new("BillboardGui")
                tag.Name = "AdminTag"
                tag.Adornee = head
                tag.Size = UDim2.new(0, 120, 0, 34)
                tag.StudsOffset = Vector3.new(0, 3, 0)
                tag.AlwaysOnTop = true
                local text = Instance.new("TextLabel", tag)
                text.Size = UDim2.new(1,0,1,0)
                text.BackgroundTransparency = 1
                text.Text = adminTag
                text.TextColor3 = Color3.fromRGB(255, 100, 100)
                text.TextStrokeTransparency = 0.3
                text.TextScaled = true
                tag.Parent = player.PlayerGui
            end
        end
        player.CharacterAdded:Connect(setTag)
    end
end)
