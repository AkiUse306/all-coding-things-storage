local Players = game:GetService("Players")
local ReplicatedStorage = game:GetService("ReplicatedStorage")
local UserInputService = game:GetService("UserInputService")
local RunService = game:GetService("RunService")

local player = Players.LocalPlayer
local mouse = player:GetMouse()
local camera = workspace.CurrentCamera
local DealDamageRemote = ReplicatedStorage:WaitForChild("Remotes"):WaitForChild("DealDamage")

local lastFireTime = 0

local function onToolActivated(tool)
    local now = tick()
    local fireRate = now - lastFireTime
    lastFireTime = now

    local weaponName = "Pistol"
    if player.Data and player.Data.EquippedWeapon then
        weaponName = player.Data.EquippedWeapon
    elseif tool and tool.Name then
        weaponName = tool.Name
    end

    local origin = camera and camera.CFrame.Position
    local targetPosition = mouse.Hit and mouse.Hit.p
    if not origin or not targetPosition then
        return
    end

    DealDamageRemote:FireServer(weaponName, origin, targetPosition, fireRate)
end

local function setupTool(tool)
    if tool:IsA("Tool") then
        tool.Activated:Connect(function()
            onToolActivated(tool)
        end)
    end
end

local function connectTools()
    local backpack = player:WaitForChild("Backpack")
    for _, tool in ipairs(backpack:GetChildren()) do
        setupTool(tool)
    end
    backpack.ChildAdded:Connect(setupTool)
end

if player.Character then
    connectTools()
end

player.CharacterAdded:Connect(function()
    connectTools()
end)

UserInputService.InputBegan:Connect(function(input, processed)
    if processed then return end
    if input.KeyCode == Enum.KeyCode.R then
        print("Reloading weapon...")
    end
end)

-- Ensure player data is always a table (client-side fallback for local effects)
RunService.Heartbeat:Connect(function()
    if not player.Data then
        player.Data = player.Data or {Multipliers = {Damage = 1, Money = 1}}
    end
end)

