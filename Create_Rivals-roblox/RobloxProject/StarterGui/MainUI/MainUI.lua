local Players = game:GetService("Players")
local player = Players.LocalPlayer
local playerGui = player:WaitForChild("PlayerGui")

local ui = Instance.new("ScreenGui")
ui.Name = "MainUI"
ui.ResetOnSpawn = false
ui.Parent = playerGui

local bg = Instance.new("Frame")
bg.Name = "BG"
bg.Size = UDim2.new(1,0,1,0)
bg.Position = UDim2.new(0,0,0,0)
bg.BackgroundColor3 = Color3.fromRGB(10,10,22)
bg.BackgroundTransparency = 0.1
bg.Parent = ui

local title = Instance.new("TextLabel")
title.Name = "Title"
title.Text = "Create A Rival"
title.Size = UDim2.new(0,300,0,45)
title.Position = UDim2.new(0.02,0,0.02,0)
title.BackgroundTransparency = 1
title.TextColor3 = Color3.fromRGB(255,255,255)
title.TextScaled = true
title.Font = Enum.Font.GothamBold
title.Parent = ui

local status = Instance.new("Frame")
status.Name = "StatusPanel"
status.Size = UDim2.new(0, 320, 0, 120)
status.Position = UDim2.new(0.02,0,0.11,0)
status.BackgroundColor3 = Color3.fromRGB(30,30,40)
status.BackgroundTransparency = 0.2
status.Parent = ui

local aklas = Instance.new("TextLabel")
aklas.Name = "AklasLabel"
aklas.Text = "Aklas: 0"
aklas.Size = UDim2.new(1,-20,0,40)
aklas.Position = UDim2.new(0.02,0,0.05,0)
aklas.BackgroundTransparency = 1
aklas.TextColor3 = Color3.fromRGB(255,255,255)
aklas.TextScaled = true
aklas.Font = Enum.Font.Gotham
aklas.Parent = status

local equip = Instance.new("TextLabel")
equip.Name = "EquippedLabel"
equip.Text = "Equipped: Pistol"
equip.Size = UDim2.new(1,-20,0,35)
equip.Position = UDim2.new(0.02,0,0.5,0)
equip.BackgroundTransparency = 1
equip.TextColor3 = Color3.fromRGB(200,200,255)
equip.TextScaled = true
equip.Font = Enum.Font.Gotham

equip.Parent = status

local shopFrame = Instance.new("Frame")
shopFrame.Name = "ShopFrame"
shopFrame.Size = UDim2.new(0, 320, 0, 220)
shopFrame.Position = UDim2.new(0.02,0,0.32,0)
shopFrame.BackgroundColor3 = Color3.fromRGB(30,30,40)
shopFrame.BackgroundTransparency = 0.2
shopFrame.Parent = ui

local shopTitle = Instance.new("TextLabel")
shopTitle.Text = "Shop"
shopTitle.Size = UDim2.new(1,0,0,30)
shopTitle.Position = UDim2.new(0,0,0,0)
shopTitle.BackgroundTransparency = 1
shopTitle.TextColor3 = Color3.fromRGB(255,255,255)
shopTitle.Font = Enum.Font.GothamBold
shopTitle.TextScaled = true
shopTitle.Parent = shopFrame

local items = {
    {Name = "Pistol", Price = 150},
    {Name = "SMG", Price = 550},
    {Name = "Rifle", Price = 1200},
}
for i, item in ipairs(items) do
    local btn = Instance.new("TextButton")
    btn.Name = item.Name .. "Button"
    btn.Text = item.Name .. " - " .. item.Price .. " Aklas"
    btn.Size = UDim2.new(0.95,0,0,34)
    btn.Position = UDim2.new(0.025,0,0, 32 + (i-1)*38)
    btn.BackgroundColor3 = Color3.fromRGB(50,50,70)
    btn.TextColor3 = Color3.fromRGB(255,255,255)
    btn.Font = Enum.Font.Gotham
    btn.TextScaled = true
    btn.Parent = shopFrame
    btn:SetAttribute("Name", item.Name)
    btn:SetAttribute("Price", item.Price)
end

local eggFrame = Instance.new("Frame")
eggFrame.Name = "EggOpeningUI"
eggFrame.Size = UDim2.new(0, 320, 0, 120)
eggFrame.Position = UDim2.new(0.02,0,0.68,0)
eggFrame.BackgroundColor3 = Color3.fromRGB(30,30,40)
eggFrame.BackgroundTransparency = 0.2
eggFrame.Parent = ui

local eggTitle = Instance.new("TextLabel")
eggTitle.Text = "Eggs"
eggTitle.Size = UDim2.new(1,0,0,30)
eggTitle.BackgroundTransparency = 1
eggTitle.TextColor3 = Color3.fromRGB(255,255,255)
eggTitle.Font = Enum.Font.GothamBold
eggTitle.TextScaled = true
eggTitle.Parent = eggFrame

local rollButton = Instance.new("TextButton")
rollButton.Name = "RollButton"
rollButton.Text = "Open Egg (100 Aklas)"
rollButton.Size = UDim2.new(0.95,0,0,40)
rollButton.Position = UDim2.new(0.025,0,0.4,0)
rollButton.BackgroundColor3 = Color3.fromRGB(100,0,200)
rollButton.TextColor3 = Color3.fromRGB(255,255,255)
rollButton.Font = Enum.Font.Gotham
rollButton.TextScaled = true
rollButton.Parent = eggFrame

local navFrame = Instance.new("Frame")
navFrame.Name = "NavPanel"
navFrame.Size = UDim2.new(0,320,0,120)
navFrame.Position = UDim2.new(0.35,0,0.32,0)
navFrame.BackgroundColor3 = Color3.fromRGB(30,30,40)
navFrame.BackgroundTransparency = 0.2
navFrame.Parent = ui

local arenaButton = Instance.new("TextButton")
arenaButton.Name = "ArenaButton"
arenaButton.Text = "Teleport to Arena"
arenaButton.Size = UDim2.new(0.95,0,0,40)
arenaButton.Position = UDim2.new(0.025,0,0.1,0)
arenaButton.BackgroundColor3 = Color3.fromRGB(0,120,255)
arenaButton.TextColor3 = Color3.fromRGB(255,255,255)
arenaButton.Font = Enum.Font.Gotham
arenaButton.TextScaled = true
arenaButton.Parent = navFrame

local hubButton = Instance.new("TextButton")
hubButton.Name = "HubButton"
hubButton.Text = "Teleport to Hub"
hubButton.Size = UDim2.new(0.95,0,0,40)
hubButton.Position = UDim2.new(0.025,0,0.55,0)
hubButton.BackgroundColor3 = Color3.fromRGB(0,200,120)
hubButton.TextColor3 = Color3.fromRGB(255,255,255)
hubButton.Font = Enum.Font.Gotham
hubButton.TextScaled = true
hubButton.Parent = navFrame

local popup = Instance.new("TextLabel")
popup.Name = "Popup"
popup.Text = ""
popup.Size = UDim2.new(0, 320, 0, 60)
popup.Position = UDim2.new(0.35, 0, 0.6, 0)
popup.BackgroundColor3 = Color3.fromRGB(0,0,0)
popup.BackgroundTransparency = 0.35
popup.TextColor3 = Color3.fromRGB(255,255,255)
popup.TextScaled = true
popup.Font = Enum.Font.Gotham
popup.Visible = false
popup.Parent = ui


