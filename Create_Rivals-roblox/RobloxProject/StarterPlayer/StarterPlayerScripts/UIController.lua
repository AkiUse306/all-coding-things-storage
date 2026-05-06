local Players = game:GetService("Players")
local player = Players.LocalPlayer
local PlayerGui = player:WaitForChild("PlayerGui")

local UIController = {}

function UIController:OpenShop()
    local gui = PlayerGui:FindFirstChild("MainUI")
    if gui and gui:FindFirstChild("ShopFrame") then
        gui.ShopFrame.Visible = true
    end
end

function UIController:CloseShop()
    local gui = PlayerGui:FindFirstChild("MainUI")
    if gui and gui:FindFirstChild("ShopFrame") then
        gui.ShopFrame.Visible = false
    end
end

return UIController
