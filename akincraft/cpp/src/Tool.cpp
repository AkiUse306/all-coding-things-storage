#include "Tool.h"
#include <algorithm>

Tool::Tool(ToolType toolType) : type(toolType) {
    switch (type) {
        case ToolType::WOODEN_PICKAXE:
            maxDurability = 60;
            damage = 2;
            break;
        case ToolType::STONE_PICKAXE:
            maxDurability = 131;
            damage = 4;
            break;
        case ToolType::IRON_PICKAXE:
            maxDurability = 250;
            damage = 6;
            break;
        case ToolType::DIAMOND_PICKAXE:
            maxDurability = 1561;
            damage = 8;
            break;
        case ToolType::NETHERITE_PICKAXE:
            maxDurability = 2031;
            damage = 10;
            break;
        case ToolType::WOODEN_AXE:
            maxDurability = 59;
            damage = 5;
            break;
        case ToolType::STONE_AXE:
            maxDurability = 132;
            damage = 7;
            break;
        case ToolType::IRON_AXE:
            maxDurability = 251;
            damage = 9;
            break;
        case ToolType::DIAMOND_AXE:
            maxDurability = 1562;
            damage = 11;
            break;
        case ToolType::NETHERITE_AXE:
            maxDurability = 2032;
            damage = 13;
            break;
        case ToolType::WOODEN_SHOVEL:
            maxDurability = 59;
            damage = 2;
            break;
        case ToolType::STONE_SHOVEL:
            maxDurability = 131;
            damage = 3;
            break;
        case ToolType::IRON_SHOVEL:
            maxDurability = 250;
            damage = 4;
            break;
        case ToolType::DIAMOND_SHOVEL:
            maxDurability = 1561;
            damage = 5;
            break;
        case ToolType::NETHERITE_SHOVEL:
            maxDurability = 2031;
            damage = 6;
            break;
        case ToolType::WOODEN_SWORD:
            maxDurability = 59;
            damage = 4;
            break;
        case ToolType::STONE_SWORD:
            maxDurability = 131;
            damage = 5;
            break;
        case ToolType::IRON_SWORD:
            maxDurability = 250;
            damage = 6;
            break;
        case ToolType::DIAMOND_SWORD:
            maxDurability = 1561;
            damage = 7;
            break;
        case ToolType::NETHERITE_SWORD:
            maxDurability = 2031;
            damage = 9;
            break;
        case ToolType::STONE_HOE:
            maxDurability = 131;
            damage = 1;
            break;
        case ToolType::IRON_HOE:
            maxDurability = 250;
            damage = 2;
            break;
        case ToolType::DIAMOND_HOE:
            maxDurability = 1561;
            damage = 3;
            break;
        case ToolType::NETHERITE_HOE:
            maxDurability = 2031;
            damage = 4;
            break;
        default:
            maxDurability = 60;
            damage = 1;
            break;
    }
    currentDurability = maxDurability;
}

std::string Tool::getDisplayName() const {
    switch (type) {
        case ToolType::WOODEN_PICKAXE: return "Wooden Pickaxe";
        case ToolType::STONE_PICKAXE: return "Stone Pickaxe";
        case ToolType::IRON_PICKAXE: return "Iron Pickaxe";
        case ToolType::DIAMOND_PICKAXE: return "Diamond Pickaxe";
        case ToolType::WOODEN_AXE: return "Wooden Axe";
        case ToolType::STONE_AXE: return "Stone Axe";
        case ToolType::IRON_AXE: return "Iron Axe";
        case ToolType::DIAMOND_AXE: return "Diamond Axe";
        case ToolType::WOODEN_SHOVEL: return "Wooden Shovel";
        case ToolType::STONE_SHOVEL: return "Stone Shovel";
        case ToolType::IRON_SHOVEL: return "Iron Shovel";
        case ToolType::DIAMOND_SHOVEL: return "Diamond Shovel";
        case ToolType::WOODEN_SWORD: return "Wooden Sword";
        case ToolType::STONE_SWORD: return "Stone Sword";
        case ToolType::IRON_SWORD: return "Iron Sword";
        case ToolType::DIAMOND_SWORD: return "Diamond Sword";
        case ToolType::NETHERITE_SWORD: return "Netherite Sword";
        case ToolType::STONE_HOE: return "Stone Hoe";
        case ToolType::IRON_HOE: return "Iron Hoe";
        case ToolType::DIAMOND_HOE: return "Diamond Hoe";
        case ToolType::NETHERITE_HOE: return "Netherite Hoe";
        default: return "Unknown Tool";
    }
}
