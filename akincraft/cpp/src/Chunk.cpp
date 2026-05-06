#include "Chunk.h"
#include "BlockType.h"
#include <cmath>

Chunk::Chunk(int chunkX, int chunkZ, int seed, Dimension dimension)
    : chunkX(chunkX), chunkZ(chunkZ), dimension(dimension) {
    blocks.fill(0);
    generateTerrain(seed);
    buildMesh();
}

int Chunk::worldX() const { return chunkX * CHUNK_SIZE; }
int Chunk::worldZ() const { return chunkZ * CHUNK_SIZE; }

const std::vector<Quad>& Chunk::getQuads() const { return quads; }

uint8_t Chunk::getBlock(int x, int y, int z) const {
    if (x < 0 || x >= CHUNK_SIZE || y < 0 || y >= CHUNK_HEIGHT || z < 0 || z >= CHUNK_SIZE) {
        return 0;
    }
    return blocks[y * CHUNK_SIZE * CHUNK_SIZE + z * CHUNK_SIZE + x];
}

void Chunk::setBlock(int x, int y, int z, uint8_t value) {
    blocks[y * CHUNK_SIZE * CHUNK_SIZE + z * CHUNK_SIZE + x] = value;
}

void Chunk::generateTerrain(int seed) {
    for (int x = 0; x < CHUNK_SIZE; x++) {
        for (int z = 0; z < CHUNK_SIZE; z++) {
            float worldX = static_cast<float>(chunkX * CHUNK_SIZE + x);
            float worldZ = static_cast<float>(chunkZ * CHUNK_SIZE + z);
            int terrainHeight = 0;
            bool isDesert = false;

            if (dimension == Dimension::OVERWORLD) {
                float height = 40.0f + perlin(worldX * 0.08f, worldZ * 0.08f, seed) * 16.0f;
                terrainHeight = static_cast<int>(std::floor(height));
                isDesert = perlin(worldX * 0.02f, worldZ * 0.02f, seed) < -0.2f;
            } else if (dimension == Dimension::NETHER) {
                terrainHeight = 30 + static_cast<int>(perlin(worldX * 0.07f, worldZ * 0.07f, seed) * 12.0f);
            } else if (dimension == Dimension::END) {
                terrainHeight = 30;
            }

            for (int y = 0; y < CHUNK_HEIGHT; y++) {
                uint8_t blockType = 0;
                if (dimension == Dimension::OVERWORLD) {
                    float cave = caveNoise(worldX * 0.09f, static_cast<float>(y) * 0.27f, worldZ * 0.09f, seed);
                    if (y > terrainHeight || cave > 0.40f) {
                        blockType = 0;
                    } else if (y == terrainHeight) {
                        blockType = isDesert ? static_cast<uint8_t>(BlockType::SAND) : static_cast<uint8_t>(BlockType::GRASS);
                    } else if (y > terrainHeight - 4) {
                        blockType = static_cast<uint8_t>(BlockType::DIRT);
                    } else {
                        blockType = static_cast<uint8_t>(BlockType::STONE);
                    }
                } else if (dimension == Dimension::NETHER) {
                    float cave = caveNoise(worldX * 0.12f, static_cast<float>(y) * 0.30f, worldZ * 0.12f, seed);
                    if (y > terrainHeight || cave > 0.35f) {
                        blockType = 0;
                    } else if (y >= terrainHeight - 1) {
                        blockType = static_cast<uint8_t>(BlockType::NETHERRACK);
                    } else if (y >= terrainHeight - 3) {
                        blockType = static_cast<uint8_t>(BlockType::GRAVEL);
                    } else {
                        blockType = static_cast<uint8_t>(BlockType::NETHERRACK);
                    }
                } else if (dimension == Dimension::END) {
                    if (y <= terrainHeight) {
                        blockType = static_cast<uint8_t>(BlockType::END_STONE);
                    } else {
                        blockType = 0;
                    }
                }
                setBlock(x, y, z, blockType);
            }

            if (dimension == Dimension::OVERWORLD && !isDesert && noise(x + chunkX * CHUNK_SIZE, z + chunkZ * CHUNK_SIZE, seed) > 0.62f) {
                int baseY = terrainHeight + 1;
                for (int dy = 0; dy < 4 && baseY + dy < CHUNK_HEIGHT; dy++) {
                    setBlock(x, baseY + dy, z, static_cast<uint8_t>(BlockType::OAK_LOG));
                }
                for (int dx = -2; dx <= 2; dx++) {
                    for (int dz = -2; dz <= 2; dz++) {
                        int ax = x + dx;
                        int az = z + dz;
                        int ay = baseY + 4;
                        if (ax >= 0 && ax < CHUNK_SIZE && az >= 0 && az < CHUNK_SIZE && ay < CHUNK_HEIGHT) {
                            if (std::abs(dx) + std::abs(dz) < 4) {
                                setBlock(ax, ay, az, static_cast<uint8_t>(BlockType::OAK_LEAVES));
                            }
                        }
                    }
                }
            }
        }
    }
}

void Chunk::buildMesh() {
    quads.clear();
    for (int x = 0; x < CHUNK_SIZE; x++) {
        for (int y = 0; y < CHUNK_HEIGHT; y++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                uint8_t block = getBlock(x, y, z);
                if (block == 0) continue;
                if (getBlock(x, y, z - 1) == 0) appendFace(x, y, z, 0, 0, -1, block);
                if (getBlock(x, y, z + 1) == 0) appendFace(x, y, z, 0, 0, 1, block);
                if (getBlock(x - 1, y, z) == 0) appendFace(x, y, z, -1, 0, 0, block);
                if (getBlock(x + 1, y, z) == 0) appendFace(x, y, z, 1, 0, 0, block);
                if (getBlock(x, y - 1, z) == 0) appendFace(x, y, z, 0, -1, 0, block);
                if (getBlock(x, y + 1, z) == 0) appendFace(x, y, z, 0, 1, 0, block);
            }
        }
    }
}

void Chunk::rebuildMesh() {
    buildMesh();
}

void Chunk::appendFace(int x, int y, int z, int dx, int dy, int dz, uint8_t type) {
    float baseX = static_cast<float>(worldX() + x);
    float baseY = static_cast<float>(y);
    float baseZ = static_cast<float>(worldZ() + z);
    float color[3];
    switch (type) {
        case 1: color[0] = 0.48f; color[1] = 0.78f; color[2] = 0.32f; break;
        case 2: color[0] = 0.56f; color[1] = 0.39f; color[2] = 0.23f; break;
        case 3: color[0] = 0.55f; color[1] = 0.55f; color[2] = 0.55f; break;
        case 4: color[0] = 0.95f; color[1] = 0.86f; color[2] = 0.57f; break;
        case 6: color[0] = 0.44f; color[1] = 0.34f; color[2] = 0.25f; break;
        case 7: color[0] = 0.30f; color[1] = 0.68f; color[2] = 0.30f; break;
        default: color[0] = 0.7f; color[1] = 0.3f; color[2] = 0.7f; break;
    }
    if (dx != 0) {
        float fx = baseX + (dx > 0 ? 1.0f : 0.0f);
        addQuad(fx, baseY, baseZ, fx, baseY, baseZ + 1.0f, fx, baseY + 1.0f, baseZ + 1.0f, fx, baseY + 1.0f, baseZ, color);
    } else if (dz != 0) {
        float fz = baseZ + (dz > 0 ? 1.0f : 0.0f);
        addQuad(baseX, baseY, fz, baseX + 1.0f, baseY, fz, baseX + 1.0f, baseY + 1.0f, fz, baseX, baseY + 1.0f, fz, color);
    } else {
        float fy = baseY + (dy > 0 ? 1.0f : 0.0f);
        addQuad(baseX, fy, baseZ, baseX + 1.0f, fy, baseZ, baseX + 1.0f, fy, baseZ + 1.0f, baseX, fy, baseZ + 1.0f, color);
    }
}

void Chunk::addQuad(float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, const float color[3]) {
    Quad quad;
    quad.positions[0] = x0; quad.positions[1] = y0; quad.positions[2] = z0;
    quad.positions[3] = x1; quad.positions[4] = y1; quad.positions[5] = z1;
    quad.positions[6] = x2; quad.positions[7] = y2; quad.positions[8] = z2;
    quad.positions[9] = x3; quad.positions[10] = y3; quad.positions[11] = z3;
    quad.color[0] = color[0];
    quad.color[1] = color[1];
    quad.color[2] = color[2];
    quads.push_back(quad);
}

float Chunk::noise(int x, int z, int seed) const {
    int n = x + z * 57 + seed * 131;
    n = (n << 13) ^ n;
    int t = (n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff;
    return 1.0f - static_cast<float>(t) / 1073741824.0f;
}

float Chunk::smoothNoise(int x, int z, int seed) const {
    float corners = (noise(x - 1, z - 1, seed) + noise(x + 1, z - 1, seed) + noise(x - 1, z + 1, seed) + noise(x + 1, z + 1, seed)) / 16.0f;
    float sides = (noise(x - 1, z, seed) + noise(x + 1, z, seed) + noise(x, z - 1, seed) + noise(x, z + 1, seed)) / 8.0f;
    float center = noise(x, z, seed) / 4.0f;
    return corners + sides + center;
}

float Chunk::interpolatedNoise(float x, float z, int seed) const {
    int intX = static_cast<int>(std::floor(x));
    int intZ = static_cast<int>(std::floor(z));
    float fracX = x - intX;
    float fracZ = z - intZ;
    float v1 = smoothNoise(intX, intZ, seed);
    float v2 = smoothNoise(intX + 1, intZ, seed);
    float v3 = smoothNoise(intX, intZ + 1, seed);
    float v4 = smoothNoise(intX + 1, intZ + 1, seed);
    float i1 = v1 + (v2 - v1) * (1.0f - std::cos(fracX * 3.1415926f)) * 0.5f;
    float i2 = v3 + (v4 - v3) * (1.0f - std::cos(fracX * 3.1415926f)) * 0.5f;
    return i1 + (i2 - i1) * (1.0f - std::cos(fracZ * 3.1415926f)) * 0.5f;
}

float Chunk::perlin(float x, float z, int seed) const {
    float total = 0.0f;
    float persistence = 0.55f;
    float frequency = 1.0f;
    float amplitude = 1.0f;
    for (int i = 0; i < 4; i++) {
        total += interpolatedNoise(x * frequency, z * frequency, seed) * amplitude;
        amplitude *= persistence;
        frequency *= 2.0f;
    }
    return total;
}

float Chunk::caveNoise(float x, float y, float z, int seed) const {
    float value = perlin(x * 0.25f, z * 0.25f, seed);
    float heightFactor = (std::sin(x * 0.14f + seed * 1.3f) + std::cos(z * 0.12f + seed * 0.9f)) * 0.3f;
    return value + heightFactor - y * 0.01f;
}
