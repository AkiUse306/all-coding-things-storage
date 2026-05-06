local ReplicatedStorage = game:GetService("ReplicatedStorage")
local BuyItem = ReplicatedStorage:WaitForChild("Remotes"):WaitForChild("BuyItem")
local EquipWeapon = ReplicatedStorage:WaitForChild("Remotes"):WaitForChild("EquipWeapon")
local WeaponConfig = require(ReplicatedStorage.Modules.WeaponConfig)
local EconomySystem = require(ReplicatedStorage.Modules.EconomySystem)

local EconomyHandler = {}
local DataManager = require(script.Parent.DataManager)

BuyItem.OnServerEvent:Connect(function(player, category, weaponName)
    local configCategory = WeaponConfig[category]
    if not configCategory then
        BuyItem:FireClient(player, false, "invalid category")
        return
    end

    local weaponData = configCategory[weaponName]
    if not weaponData then
        BuyItem:FireClient(player, false, "invalid weapon")
        return
    end

    if EconomySystem:Charge(player, weaponData.Price) then
        if not player.Data.Weapons then player.Data.Weapons = {} end
        table.insert(player.Data.Weapons, weaponName)
        BuyItem:FireClient(player, true, weaponName)
        DataManager:EmitPlayerDataUpdate(player)
    else
        BuyItem:FireClient(player, false, "insufficient aklas")
    end
end)

EquipWeapon.OnServerEvent:Connect(function(player, weaponName)
    if not player.Data or not player.Data.Weapons then return end
    for _, w in ipairs(player.Data.Weapons) do
        if w == weaponName then
            player.Data.EquippedWeapon = weaponName
            EquipWeapon:FireClient(player, true, weaponName)
            DataManager:EmitPlayerDataUpdate(player)
            return
        end
    end
    EquipWeapon:FireClient(player, false, "weapon not owned")
end)

return EconomyHandler
