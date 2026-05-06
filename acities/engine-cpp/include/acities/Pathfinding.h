#pragma once

#include <vector>
#include <queue>
#include <unordered_map>
#include <unordered_set>
#include <cmath>

namespace acities {

struct Vec2 {
    float x, y;
    Vec2(float x = 0, float y = 0) : x(x), y(y) {}
    float distance(const Vec2& other) const {
        float dx = x - other.x;
        float dy = y - other.y;
        return std::sqrt(dx * dx + dy * dy);
    }
};

class Pathfinding {
public:
    Pathfinding(int width, int height);
    ~Pathfinding() = default;

    void setObstacle(int x, int y, bool obstacle);
    std::vector<Vec2> findPath(const Vec2& start, const Vec2& goal);

private:
    int width, height;
    std::vector<std::vector<bool>> grid;

    struct Node {
        Vec2 pos;
        float g, h, f;
        Node* parent;
        Node(Vec2 p, float g, float h, Node* par = nullptr) : pos(p), g(g), h(h), f(g + h), parent(par) {}
    };

    struct NodeCompare {
        bool operator()(const Node* a, const Node* b) const {
            return a->f > b->f;
        }
    };

    std::vector<Vec2> reconstructPath(Node* node);
};

} // namespace acities