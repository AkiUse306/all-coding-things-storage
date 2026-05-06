local ReplicatedStorage = game:GetService("ReplicatedStorage")
local RemoteInit = {}

function RemoteInit:CreateRemotes()
    local remotes = ReplicatedStorage:FindFirstChild("Remotes")
    if not remotes then
        remotes = Instance.new("Folder")
        remotes.Name = "Remotes"
        remotes.Parent = ReplicatedStorage
    end
    local names = {"DealDamage", "BuyItem", "OpenEgg", "EquipWeapon", "TeleportToArena", "TeleportToHub", "PlayerDataUpdated"}
    for _, name in ipairs(names) do
        if not remotes:FindFirstChild(name) then
            local evt = Instance.new("RemoteEvent")
            evt.Name = name
            evt.Parent = remotes
        end
    end
end

return RemoteInit