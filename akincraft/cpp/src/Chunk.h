#pragma once

#include <cstdint>
#include <vector>
#include <array>

enum class Dimension {
    OVERWORLD,
    NETHER,
    END
};

struct Quad {
    float positions[12];
    float color[3];
};

struct Chunk {
    static constexpr int CHUNK_SIZE = 16;
    static constexpr int CHUNK_HEIGHT = 128;

    Chunk(int chunkX, int chunkZ, int seed, Dimension dimension);

    const std::vector<Quad>& getQuads() const;
    int worldX() const;
    int worldZ() const;
    void rebuildMesh();
    uint8_t getBlock(int x, int y, int z) const;
    void setBlock(int x, int y, int z, uint8_t value);

private:
    int chunkX;
    int chunkZ;
    Dimension dimension;
    std::vector<Quad> quads;
    std::array<uint8_t, CHUNK_SIZE * CHUNK_HEIGHT * CHUNK_SIZE> blocks;
    void generateTerrain(int seed);
    void buildMesh();
    float noise(int x, int z, int seed) const;
    float smoothNoise(int x, int z, int seed) const;
    float interpolatedNoise(float x, float z, int seed) const;
    float perlin(float x, float z, int seed) const;
    float caveNoise(float x, float y, float z, int seed) const;
    void appendFace(int x, int y, int z, int dx, int dy, int dz, uint8_t type);
    void addQuad(float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, const float color[3]);
};
