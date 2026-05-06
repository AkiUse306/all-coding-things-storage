local RNGSystem = {}

RNGSystem.Pets = {
    Common = 60,
    Rare = 25,
    Epic = 10,
    Legendary = 4,
    Mythic = 1,
}

function RNGSystem:RollRank()
    local roll = math.random(1, 100)
    if roll <= self.Pets.Mythic then
        return "Mythic"
    elseif roll <= self.Pets.Mythic + self.Pets.Legendary then
        return "Legendary"
    elseif roll <= self.Pets.Mythic + self.Pets.Legendary + self.Pets.Epic then
        return "Epic"
    elseif roll <= self.Pets.Mythic + self.Pets.Legendary + self.Pets.Epic + self.Pets.Rare then
        return "Rare"
    end
    return "Common"
end

return RNGSystem
