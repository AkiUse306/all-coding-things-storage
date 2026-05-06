#pragma once

#include <cstdint>
#include <string>
#include <unordered_map>

/**
 * Block type definitions for C++ version
 */
enum class BlockType : uint8_t {
    // Natural terrain
    AIR = 0,
    GRASS = 1,
    DIRT = 2,
    STONE = 3,
    SAND = 4,
    GRAVEL = 5,
    WATER = 6,
    LAVA = 7,
    
    // Vegetation
    OAK_LOG = 8,
    OAK_LEAVES = 9,
    BIRCH_LOG = 10,
    BIRCH_LEAVES = 11,
    SPRUCE_LOG = 12,
    SPRUCE_LEAVES = 13,
    
    // Ores
    COAL_ORE = 14,
    IRON_ORE = 15,
    GOLD_ORE = 16,
    DIAMOND_ORE = 17,
    
    // Blocks
    OAK_PLANK = 18,
    BIRCH_PLANK = 19,
    COBBLESTONE = 20,
    SMOOTH_STONE = 21,
    CRAFTING_TABLE = 22,
    FURNACE = 23,
    CHEST = 24,
    
    // Stairs & slabs
    OAK_STAIRS = 25,
    STONE_STAIRS = 26,
    OAK_SLAB = 27,
    STONE_SLAB = 28,
    
    // Doors & gates
    OAK_DOOR = 29,
    IRON_DOOR = 30,
    FENCE = 31,
    
    // Redstone
    REDSTONE_DUST = 32,
    REDSTONE_REPEATER = 33,
    REDSTONE_TORCH = 34,
    LEVER = 35,
    BUTTON = 36,
    OAK_PRESSURE_PLATE = 37,

    // Nether
    NETHERRACK = 38,
    SOUL_SAND = 39,
    MAGMA = 40,
    NETHER_BRICK = 41,
    GLOWSTONE = 42,
    NETHER_QUARTZ_ORE = 43,
    NETHER_GOLD_ORE = 44,
    BASALT = 45,
    NETHERITE_BLOCK = 46,

    // End
    END_STONE = 47,
    PURPUR_BLOCK = 48,
    OBSIDIAN = 49,
    BEDROCK = 50
};

static inline bool isLiquid(BlockType block) {
    return block == BlockType::WATER || block == BlockType::LAVA;
}

static inline bool isTransparent(BlockType block) {
    return block == BlockType::AIR || block == BlockType::WATER;
}
