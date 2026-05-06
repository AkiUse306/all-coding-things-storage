#include "World.h"
#include <cmath>

namespace {
    inline int calculateChunkIndex(int coordinate) {
        if (coordinate >= 0) {
            return coordinate / Chunk::CHUNK_SIZE;
        }
        return (coordinate + 1) / Chunk::CHUNK_SIZE - 1;
    }

    inline int normalizeLocalCoord(int coordinate) {
        int result = coordinate % Chunk::CHUNK_SIZE;
        if (result < 0) result += Chunk::CHUNK_SIZE;
        return result;
    }
}

std::string World::chunkKey(int x, int z) const {
    return std::to_string(x) + "," + std::to_string(z);
}

static void ensureChunk(std::unordered_map<std::string, Chunk>& chunks, const std::string& key, int chunkX, int chunkZ, int seed, Dimension dimension) {
    if (chunks.find(key) == chunks.end()) {
        chunks.emplace(key, Chunk(chunkX, chunkZ, seed, dimension));
    }
}

void World::initialize() {
    update(0.0f, 0.0f);
}

void World::update(float playerX, float playerZ) {
    int centerX = static_cast<int>(std::floor(playerX / Chunk::CHUNK_SIZE));
    int centerZ = static_cast<int>(std::floor(playerZ / Chunk::CHUNK_SIZE));
    for (int x = centerX - ACTIVE_RADIUS; x <= centerX + ACTIVE_RADIUS; x++) {
        for (int z = centerZ - ACTIVE_RADIUS; z <= centerZ + ACTIVE_RADIUS; z++) {
            std::string key = chunkKey(x, z);
            ensureChunk(chunks, key, x, z, seed, currentDimension);
        }
    }
}

BlockType World::getBlockAt(int x, int y, int z) const {
    if (y < 0 || y >= Chunk::CHUNK_HEIGHT) {
        return BlockType::AIR;
    }
    int chunkX = calculateChunkIndex(x);
    int chunkZ = calculateChunkIndex(z);
    std::string key = chunkKey(chunkX, chunkZ);
    auto it = chunks.find(key);
    if (it == chunks.end()) {
        return BlockType::AIR;
    }
    int localX = normalizeLocalCoord(x);
    int localZ = normalizeLocalCoord(z);
    return static_cast<BlockType>(it->second.getBlock(localX, y, localZ));
}

bool World::setBlockAt(int x, int y, int z, BlockType type) {
    if (y < 0 || y >= Chunk::CHUNK_HEIGHT) {
        return false;
    }
    int chunkX = calculateChunkIndex(x);
    int chunkZ = calculateChunkIndex(z);
    std::string key = chunkKey(chunkX, chunkZ);
    ensureChunk(chunks, key, chunkX, chunkZ, seed, currentDimension);
    int localX = normalizeLocalCoord(x);
    int localZ = normalizeLocalCoord(z);
    auto it = chunks.find(key);
    if (it == chunks.end()) {
        return false;
    }
    Chunk& chunk = it->second;
    chunk.setBlock(localX, y, localZ, static_cast<uint8_t>(type));
    chunk.rebuildMesh();

    if (localX == 0) {
        std::string neighborKey = chunkKey(chunkX - 1, chunkZ);
        ensureChunk(chunks, neighborKey, chunkX - 1, chunkZ, seed, currentDimension);
        auto neighborIt = chunks.find(neighborKey);
        if (neighborIt != chunks.end()) neighborIt->second.rebuildMesh();
    }
    if (localX == Chunk::CHUNK_SIZE - 1) {
        std::string neighborKey = chunkKey(chunkX + 1, chunkZ);
        ensureChunk(chunks, neighborKey, chunkX + 1, chunkZ, seed, currentDimension);
        auto neighborIt = chunks.find(neighborKey);
        if (neighborIt != chunks.end()) neighborIt->second.rebuildMesh();
    }
    if (localZ == 0) {
        std::string neighborKey = chunkKey(chunkX, chunkZ - 1);
        ensureChunk(chunks, neighborKey, chunkX, chunkZ - 1, seed, currentDimension);
        auto neighborIt = chunks.find(neighborKey);
        if (neighborIt != chunks.end()) neighborIt->second.rebuildMesh();
    }
    if (localZ == Chunk::CHUNK_SIZE - 1) {
        std::string neighborKey = chunkKey(chunkX, chunkZ + 1);
        ensureChunk(chunks, neighborKey, chunkX, chunkZ + 1, seed, currentDimension);
        auto neighborIt = chunks.find(neighborKey);
        if (neighborIt != chunks.end()) neighborIt->second.rebuildMesh();
    }
    return true;
}

void World::setDimension(Dimension newDimension) {
    if (currentDimension == newDimension) {
        return;
    }
    currentDimension = newDimension;
    chunks.clear();
}

Dimension World::getDimension() const {
    return currentDimension;
}

const std::unordered_map<std::string, Chunk>& World::getChunks() const {
    return chunks;
}
