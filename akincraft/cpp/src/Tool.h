#pragma once

#include <string>
#include <cstdint>

/**
 * Tool types with properties
 */
enum class ToolType {
    WOODEN_PICKAXE,
    STONE_PICKAXE,
    IRON_PICKAXE,
    DIAMOND_PICKAXE,
    WOODEN_AXE,
    STONE_AXE,
    IRON_AXE,
    DIAMOND_AXE,
    WOODEN_SHOVEL,
    STONE_SHOVEL,
    IRON_SHOVEL,
    DIAMOND_SHOVEL,
    WOODEN_SWORD,
    STONE_SWORD,
    IRON_SWORD,
    DIAMOND_SWORD,
    NETHERITE_SWORD,
    STONE_HOE,
    IRON_HOE,
    DIAMOND_HOE,
    NETHERITE_PICKAXE,
    NETHERITE_AXE,
    NETHERITE_SHOVEL,
    NETHERITE_HOE
};

/**
 * Tool with durability tracking
 */
class Tool {
private:
    ToolType type;
    int currentDurability;
    int maxDurability;
    int damage;
    
public:
    Tool(ToolType type);
    
    int getDamage() const { return damage; }
    int getDurability() const { return currentDurability; }
    int getMaxDurability() const { return maxDurability; }
    float getDurabilityPercent() const { return (float)currentDurability / maxDurability; }
    
    void damageToolByUse() {
        if (currentDurability > 0) currentDurability--;
    }
    
    void repair(int amount) {
        currentDurability = std::min(currentDurability + amount, maxDurability);
    }
    
    bool isBroken() const { return currentDurability <= 0; }
    std::string getDisplayName() const;
};
