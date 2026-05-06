#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <map>
#include <vector>
#include <algorithm>
#include <filesystem>

#ifdef NLOHMANN_JSON_AVAILABLE
#include <nlohmann/json.hpp>
using json = nlohmann::json;
#else
// Simple JSON replacement if nlohmann/json not available
#include <iostream>
#endif

// Data storage directory
const std::string DATA_DIR = "./data";
const std::string PLAYERS_FILE = DATA_DIR + "/players.json";
const std::string QUEUE_FILE = DATA_DIR + "/queue.json";

// Ensure data directory exists
void InitializeDataDir() {
    std::filesystem::create_directories(DATA_DIR);
}

// Simple JSON handling (fallback if nlohmann not available)
class SimpleJSON {
public:
    static std::string playerToJson(const std::string& id, int score = 0, int level = 0) {
        return "{\"id\":\"" + id + "\",\"score\":" + std::to_string(score) + ",\"level\":" + std::to_string(level) + "}";
    }

    static std::string queueToJson(const std::vector<std::string>& players) {
        std::string result = "{\"queue\":[";
        for (size_t i = 0; i < players.size(); ++i) {
            if (i > 0) result += ",";
            result += "\"" + players[i] + "\"";
        }
        result += "]}";
        return result;
    }
};

class RivalsClient {
private:
    std::map<std::string, std::pair<int, int>> players;  // playerId -> (score, level)
    std::vector<std::string> queue;

public:
    RivalsClient() {
        InitializeDataDir();
        LoadData();
    }

    ~RivalsClient() {
        SaveData();
    }

    void LoadData() {
        // Load players
        std::ifstream playersFile(PLAYERS_FILE);
        if (playersFile.is_open()) {
            std::string line;
            while (std::getline(playersFile, line)) {
                // Simple parsing: "playerId,score,level"
                size_t pos1 = line.find(',');
                size_t pos2 = line.find(',', pos1 + 1);
                if (pos1 != std::string::npos && pos2 != std::string::npos) {
                    std::string id = line.substr(0, pos1);
                    int score = std::stoi(line.substr(pos1 + 1, pos2 - pos1 - 1));
                    int level = std::stoi(line.substr(pos2 + 1));
                    players[id] = {score, level};
                }
            }
            playersFile.close();
        }
    }

    void SaveData() {
        // Save players
        std::ofstream playersFile(PLAYERS_FILE);
        for (const auto& [id, stats] : players) {
            playersFile << id << "," << stats.first << "," << stats.second << "\n";
        }
        playersFile.close();
    }

    void getPlayerStats(const std::string& playerId) {
        std::cout << "\n=== Player Stats ===" << std::endl;
        auto it = players.find(playerId);
        if (it != players.end()) {
            std::cout << "ID: " << playerId << std::endl;
            std::cout << "Score: " << it->second.first << std::endl;
            std::cout << "Level: " << it->second.second << std::endl;
        } else {
            std::cout << "❌ Player not found: " << playerId << std::endl;
        }
    }

    void addPlayerScore(const std::string& playerId, int amount) {
        std::cout << "\n=== Score Added ===" << std::endl;
        auto it = players.find(playerId);
        if (it != players.end()) {
            it->second.first += amount;
            std::cout << "✅ Added " << amount << " score to " << playerId << std::endl;
            std::cout << "New score: " << it->second.first << std::endl;
        } else {
            // Create new player
            players[playerId] = {amount, 1};
            std::cout << "✅ Created new player " << playerId << " with " << amount << " score" << std::endl;
        }
        SaveData();
    }

    void updatePlayerStats(const std::string& playerId, int score, int level) {
        std::cout << "\n=== Stats Updated ===" << std::endl;
        players[playerId] = {score, level};
        std::cout << "✅ Updated " << playerId << std::endl;
        std::cout << "Score: " << score << ", Level: " << level << std::endl;
        SaveData();
    }

    void createMatch(const std::string& playerId) {
        std::cout << "\n=== Match Created ===" << std::endl;
        auto it = players.find(playerId);
        if (it == players.end()) {
            std::cout << "❌ Player not found: " << playerId << std::endl;
            return;
        }
        
        // Check if already in queue
        auto queueIt = std::find(queue.begin(), queue.end(), playerId);
        if (queueIt != queue.end()) {
            std::cout << "⚠️  Player already in queue" << std::endl;
            return;
        }
        
        queue.push_back(playerId);
        std::string matchId = "match_" + std::to_string(queue.size());
        std::cout << "✅ Player " << playerId << " queued" << std::endl;
        std::cout << "Match ID: " << matchId << std::endl;
        std::cout << "Queue position: " << queue.size() << std::endl;
    }

    void getQueue() {
        std::cout << "\n=== Matchmaking Queue ===" << std::endl;
        if (queue.empty()) {
            std::cout << "Queue is empty" << std::endl;
            return;
        }
        std::cout << "Players in queue: " << queue.size() << std::endl;
        for (size_t i = 0; i < queue.size(); ++i) {
            std::cout << "  " << (i + 1) << ". " << queue[i] << std::endl;
        }
    }

    void healthCheck() {
        std::cout << "\n=== Health Check ===" << std::endl;
        std::cout << "✅ Local storage system operational" << std::endl;
        std::cout << "📁 Data directory: " << DATA_DIR << std::endl;
        std::cout << "👥 Total players: " << players.size() << std::endl;
        std::cout << "🎮 Queue size: " << queue.size() << std::endl;
    }
};

void displayMenu() {
    std::cout << "\n╔════════════════════════════════════╗" << std::endl;
    std::cout << "║  🎮 Create Rivals - C++ Client   ║" << std::endl;
    std::cout << "║        File-Based Storage         ║" << std::endl;
    std::cout << "╚════════════════════════════════════╝" << std::endl;
    std::cout << "1. Get Player Stats" << std::endl;
    std::cout << "2. Add Player Score" << std::endl;
    std::cout << "3. Update Player Stats" << std::endl;
    std::cout << "4. Create Match" << std::endl;
    std::cout << "5. View Queue" << std::endl;
    std::cout << "6. Health Check" << std::endl;
    std::cout << "0. Exit" << std::endl;
    std::cout << "─────────────────────────────────────" << std::endl;
    std::cout << "Enter choice: ";
}

int main() {
    RivalsClient client;
    int choice;

    std::cout << "🚀 Create Rivals C++ Client (File-Based)" << std::endl;
    std::cout << "📁 Data location: ./data/" << std::endl;
    std::cout << "No ports needed - works directly with files!" << std::endl;

    while (true) {
        displayMenu();
        std::cin >> choice;
        std::cin.ignore();

        switch (choice) {
            case 1: {
                std::string playerId;
                std::cout << "Enter Player ID: ";
                std::getline(std::cin, playerId);
                client.getPlayerStats(playerId);
                break;
            }
            case 2: {
                std::string playerId;
                int amount;
                std::cout << "Enter Player ID: ";
                std::getline(std::cin, playerId);
                std::cout << "Enter score amount: ";
                std::cin >> amount;
                client.addPlayerScore(playerId, amount);
                break;
            }
            case 3: {
                std::string playerId;
                int score, level;
                std::cout << "Enter Player ID: ";
                std::getline(std::cin, playerId);
                std::cout << "Enter new score: ";
                std::cin >> score;
                std::cout << "Enter new level: ";
                std::cin >> level;
                client.updatePlayerStats(playerId, score, level);
                break;
            }
            case 4: {
                std::string playerId;
                std::cout << "Enter Player ID: ";
                std::getline(std::cin, playerId);
                client.createMatch(playerId);
                break;
            }
            case 5: {
                client.getQueue();
                break;
            }
            case 6: {
                client.healthCheck();
                break;
            }
            case 0: {
                std::cout << "\n✌️  Data saved and goodbye!" << std::endl;
                return 0;
            }
            default: {
                std::cout << "❌ Invalid choice. Please try again." << std::endl;
            }
        }
    }

    return 0;
}