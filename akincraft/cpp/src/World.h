#pragma once

#include "BlockType.h"
#include "Chunk.h"

#include <unordered_map>
#include <string>

struct World {
    void initialize();
    void update(float playerX, float playerZ);
    const std::unordered_map<std::string, Chunk>& getChunks() const;
    BlockType getBlockAt(int x, int y, int z) const;
    bool setBlockAt(int x, int y, int z, BlockType type);
    void setDimension(Dimension newDimension);
    Dimension getDimension() const;

private:
    static constexpr int ACTIVE_RADIUS = 3;
    std::unordered_map<std::string, Chunk> chunks;
    int seed = 95431;
    Dimension currentDimension = Dimension::OVERWORLD;
    std::string chunkKey(int x, int z) const;
};
