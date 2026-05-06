-- External C# core integration example
local HttpService = game:GetService("HttpService")
local Players = game:GetService("Players")

local BASE_URL = "http://127.0.0.1:5000"
local ExternalCore = {}

local function safeRequest(func, ...)
    local success, result = pcall(func, ...)
    if not success then
        warn("External core request failed:", result)
    end
    return success, result
end

function ExternalCore:RequestPlayerStats(player)
    if not player then return end
    local url = BASE_URL .. "/player/" .. HttpService:UrlEncode(player.UserId)
    local success, response = safeRequest(HttpService.GetAsync, HttpService, url)
    if not success then return end
    local data = HttpService:JSONDecode(response)
    print("ExternalCore stats for", player.Name, data)
    return data
end

function ExternalCore:AddPlayerScore(player, add)
    if not player or type(add) ~= "number" then return end
    local url = BASE_URL .. "/player/" .. HttpService:UrlEncode(player.UserId) .. "/score"
    local body = HttpService:JSONEncode({ Add = add })
    local success, response = safeRequest(HttpService.PostAsync, HttpService, url, body, Enum.HttpContentType.ApplicationJson)
    if not success then return end
    local data = HttpService:JSONDecode(response)
    print("Updated external score for", player.Name, data)
    return data
end

function ExternalCore:UpdatePlayerStats(player, stats)
    if not player or type(stats) ~= "table" then return end
    local url = BASE_URL .. "/player/" .. HttpService:UrlEncode(player.UserId) .. "/stats"
    local body = HttpService:JSONEncode(stats)
    local success, response = safeRequest(HttpService.PostAsync, HttpService, url, body, Enum.HttpContentType.ApplicationJson)
    if not success then return end
    local data = HttpService:JSONDecode(response)
    print("Updated external stats for", player.Name, data)
    return data
end

function ExternalCore:Matchmake(player)
    if not player then return end
    local url = BASE_URL .. "/matchmake"
    local body = HttpService:JSONEncode({ PlayerId = tostring(player.UserId) })
    local success, response = safeRequest(HttpService.PostAsync, HttpService, url, body, Enum.HttpContentType.ApplicationJson)
    if not success then return end
    local data = HttpService:JSONDecode(response)
    print("Matchmade", player.Name, data.matchId)
    return data
end

function ExternalCore:OnPlayerKill(player, targetPlayer)
    if not player then return end
    self:AddPlayerScore(player, 25)
    if targetPlayer then
        self:AddPlayerScore(targetPlayer, 5)
    end
end

Players.PlayerAdded:Connect(function(player)
    ExternalCore:Matchmake(player)
    ExternalCore:RequestPlayerStats(player)
    ExternalCore:AddPlayerScore(player, 5)
end)

return ExternalCore
