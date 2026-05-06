local PlayerStats = {}

function PlayerStats:GetDefaultStats()
    return {
        Level = 1,
        XP = 0,
        Kills = 0,
        Deaths = 0,
        BestStreak = 0,
    }
end

function PlayerStats:AddXP(data, amount)
    data.XP = data.XP + amount
    if data.XP >= 100 then
        data.XP = data.XP - 100
        data.Level = data.Level + 1
    end
end

return PlayerStats
