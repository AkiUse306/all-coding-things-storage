#include "Biome.h"

BiomeProperties::BiomeProperties(BiomeType biomeType) : type(biomeType) {
    auto props = getProperties(biomeType);
    this->name = props.name;
    this->color = props.color;
    this->treeDensity = props.treeDensity;
    this->grassDensity = props.grassDensity;
    this->temperature = props.temperature;
}

BiomeProperties BiomeProperties::getProperties(BiomeType type) {
    BiomeProperties props(type);
    
    switch (type) {
        case BiomeType::PLAINS:
            props.name = "Plains";
            props.color = glm::vec3(0.5f, 0.5f, 0);
            props.treeDensity = 0.1f;
            props.grassDensity = 1.0f;
            props.temperature = 0.5f;
            break;
        case BiomeType::FOREST:
            props.name = "Forest";
            props.color = glm::vec3(0.3f, 0.7f, 0);
            props.treeDensity = 0.7f;
            props.grassDensity = 0.8f;
            props.temperature = 0.5f;
            break;
        case BiomeType::DESERT:
            props.name = "Desert";
            props.color = glm::vec3(1.0f, 0.8f, 0.2f);
            props.treeDensity = 0.0f;
            props.grassDensity = 0.2f;
            props.temperature = 1.5f;
            break;
        case BiomeType::MOUNTAINS:
            props.name = "Mountains";
            props.color = glm::vec3(0.6f, 0.6f, 0.6f);
            props.treeDensity = 0.3f;
            props.grassDensity = 0.5f;
            props.temperature = 0.0f;
            break;
        case BiomeType::SNOWY:
            props.name = "Snowy";
            props.color = glm::vec3(0.9f, 0.9f, 0.95f);
            props.treeDensity = 0.2f;
            props.grassDensity = 0.1f;
            props.temperature = -0.5f;
            break;
        default:
            props.name = "Unknown";
            props.color = glm::vec3(0.5f, 0.5f, 0.5f);
            props.temperature = 0;
            break;
    }
    
    return props;
}
