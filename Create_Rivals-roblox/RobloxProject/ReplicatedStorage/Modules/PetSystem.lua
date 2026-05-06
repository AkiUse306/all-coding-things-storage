local PetSystem = {}
local RNGSystem = require(game.ReplicatedStorage.Modules.RNGSystem)

PetSystem.PetsByRank = {
    Common = {"Sparky", "Reno"},
    Rare = {"Kitsu", "Blaze"},
    Epic = {"Astra", "Titan"},
    Legendary = {"Phantom", "Nova"},
    Mythic = {"Akinion"},
}

function PetSystem:RollEgg(player)
    if not player or not player.Data then
        return nil
    end

    local rank = RNGSystem:RollRank()
    local list = self.PetsByRank[rank]
    local petName = list[math.random(1, #list)]

    local data = player.Data
    data.Pets = data.Pets or {}
    table.insert(data.Pets, {Name = petName, Rank = rank, CreatedAt = os.time()})
    return petName, rank
end

return PetSystem
