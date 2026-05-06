#include "acities/Pathfinding.h"
#include <algorithm>
#include <iostream>

namespace acities {

Pathfinding::Pathfinding(int w, int h) : width(w), height(h), grid(w, std::vector<bool>(h, false)) {
}

void Pathfinding::setObstacle(int x, int y, bool obstacle) {
    if (x >= 0 && x < width && y >= 0 && y < height) {
        grid[x][y] = obstacle;
    }
}

std::vector<Vec2> Pathfinding::findPath(const Vec2& start, const Vec2& goal) {
    std::priority_queue<Node*, std::vector<Node*>, NodeCompare> openSet;
    std::unordered_set<std::string> closedSet;
    std::unordered_map<std::string, Node*> allNodes;

    auto key = [](const Vec2& v) { return std::to_string((int)v.x) + "," + std::to_string((int)v.y); };

    Node* startNode = new Node(start, 0, start.distance(goal));
    openSet.push(startNode);
    allNodes[key(start)] = startNode;

    while (!openSet.empty()) {
        Node* current = openSet.top();
        openSet.pop();

        if (current->pos.distance(goal) < 1.0f) {
            return reconstructPath(current);
        }

        closedSet.insert(key(current->pos));

        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                if (dx == 0 && dy == 0) continue;

                Vec2 neighbor(current->pos.x + dx, current->pos.y + dy);
                if (neighbor.x < 0 || neighbor.x >= width || neighbor.y < 0 || neighbor.y >= height) continue;
                if (grid[(int)neighbor.x][(int)neighbor.y]) continue;

                std::string nkey = key(neighbor);
                if (closedSet.count(nkey)) continue;

                float g = current->g + current->pos.distance(neighbor);
                float h = neighbor.distance(goal);
                float f = g + h;

                Node* neighborNode = nullptr;
                if (allNodes.count(nkey)) {
                    neighborNode = allNodes[nkey];
                    if (g >= neighborNode->g) continue;
                } else {
                    neighborNode = new Node(neighbor, g, h, current);
                    allNodes[nkey] = neighborNode;
                }

                neighborNode->g = g;
                neighborNode->f = f;
                neighborNode->parent = current;
                openSet.push(neighborNode);
            }
        }
    }

    // Cleanup
    for (auto& pair : allNodes) {
        delete pair.second;
    }

    return {}; // No path found
}

std::vector<Vec2> Pathfinding::reconstructPath(Node* node) {
    std::vector<Vec2> path;
    Node* current = node;
    while (current) {
        path.push_back(current->pos);
        current = current->parent;
    }
    std::reverse(path.begin(), path.end());

    // Cleanup
    current = node;
    while (current) {
        Node* temp = current;
        current = current->parent;
        delete temp;
    }

    return path;
}

} // namespace acities