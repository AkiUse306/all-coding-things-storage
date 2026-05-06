#pragma once

#include <glm/glm.hpp>
#include <string>

/**
 * Biome types with distinct characteristics
 */
enum class BiomeType {
    PLAINS,
    FOREST,
    DESERT,
    MOUNTAINS,
    OCEAN,
    JUNGLE,
    SNOWY,
    SWAMP
};

/**
 * Biome properties
 */
class BiomeProperties {
public:
    BiomeType type;
    std::string name;
    glm::vec3 color;
    float treeDensity;
    float grassDensity;
    float temperature;
    
    BiomeProperties(BiomeType biomeType);
    
    static BiomeProperties getProperties(BiomeType type);
    bool canSnow() const { return temperature < 0; }
};
