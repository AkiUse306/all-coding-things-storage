local ReplicatedStorage = game:GetService("ReplicatedStorage")
local Players = game:GetService("Players")
local player = Players.LocalPlayer
local gui = player:WaitForChild("PlayerGui"):WaitForChild("MainUI")

local BuyItem = ReplicatedStorage:WaitForChild("Remotes"):WaitForChild("BuyItem")
local EquipWeapon = ReplicatedStorage:WaitForChild("Remotes"):WaitForChild("EquipWeapon")
local OpenEgg = ReplicatedStorage:WaitForChild("Remotes"):WaitForChild("OpenEgg")
local TeleportToArena = ReplicatedStorage:WaitForChild("Remotes"):WaitForChild("TeleportToArena")
local TeleportToHub = ReplicatedStorage:WaitForChild("Remotes"):WaitForChild("TeleportToHub")

local MainUI = {}
local RunService = game:GetService("RunService")

local function popup(msg)
    local pop = gui:FindFirstChild("Popup")
    if pop then
        pop.Text = msg
        pop.Visible = true
        delay(2, function()
            pop.Visible = false
        end)
    end
end

local function updateStats(aklas, equipped)
    local ak = gui:FindFirstChild("StatusPanel") and gui.StatusPanel:FindFirstChild("AklasLabel")
    local eq = gui:FindFirstChild("StatusPanel") and gui.StatusPanel:FindFirstChild("EquippedLabel")
    if ak then ak.Text = "Aklas: " .. tostring(aklas or 0) end
    if eq then eq.Text = "Equipped: " .. tostring(equipped or "None") end
end

-- Keep local UI in sync with server data updates
local PlayerDataUpdated = ReplicatedStorage:WaitForChild("Remotes"):WaitForChild("PlayerDataUpdated")
PlayerDataUpdated.OnClientEvent:Connect(function(data)
    if data then
        updateStats(data.Aklas, data.EquippedWeapon)
    end
end)


function MainUI:Init()
    local shop = gui:FindFirstChild("ShopFrame")
    if shop then
        for _, obj in ipairs(shop:GetChildren()) do
            if obj:IsA("TextButton") and obj.Name:match("Button$") then
                obj.MouseButton1Click:Connect(function()
                    local item = obj:GetAttribute("Name")
                    BuyItem:FireServer("Primary", item)
                end)
            end
        end
    end

    local rollBtn = gui:FindFirstChild("EggOpeningUI") and gui.EggOpeningUI:FindFirstChild("RollButton")
    if rollBtn then
        rollBtn.MouseButton1Click:Connect(function()
            OpenEgg:FireServer()
        end)
    end

    local arenaBtn = gui:FindFirstChild("NavPanel") and gui.NavPanel:FindFirstChild("ArenaButton")
    local hubBtn = gui:FindFirstChild("NavPanel") and gui.NavPanel:FindFirstChild("HubButton")
    if arenaBtn then
        arenaBtn.MouseButton1Click:Connect(function()
            TeleportToArena:FireServer()
        end)
    end
    if hubBtn then
        hubBtn.MouseButton1Click:Connect(function()
            TeleportToHub:FireServer()
        end)
    end

    BuyItem.OnClientEvent:Connect(function(success, result)
        if success then
            popup("Bought " .. tostring(result) .. "!")
            updateStats(nil, tostring(result))
        else
            popup("Buy failed: " .. tostring(result))
        end
    end)

    EquipWeapon.OnClientEvent:Connect(function(success, result)
        if success then
            popup("Equipped " .. tostring(result))
            updateStats(nil, tostring(result))
        else
            popup("Equip failed: " .. tostring(result))
        end
    end)

    OpenEgg.OnClientEvent:Connect(function(success, petName, rank)
        if success then
            popup("New pet: " .. tostring(petName) .. " (" .. rank .. ")")
        else
            popup("Egg failed: " .. tostring(petName))
        end
    end)
end

MainUI:Init()
