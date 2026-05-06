package akincraft.game;

/**
 * Original Akincraft Enchantment System
 * Allows players to enchant items with magical properties
 */
public class EnchantmentSystem {
    public enum Enchantment {
        // Tool Enchantments
        EFFICIENCY(1, "Efficiency", "Faster block breaking"),
        UNBREAKING(2, "Unbreaking", "Tools last longer"),
        FORTUNE(3, "Fortune", "Get more resources"),
        
        // Weapon Enchantments
        SHARPNESS(4, "Sharpness", "Deal more damage"),
        KNOCKBACK(5, "Knockback", "Push enemies back"),
        
        // Armor Enchantments
        PROTECTION(6, "Protection", "Reduce damage taken"),
        FEATHER_FALLING(7, "Feather Falling", "Reduce fall damage"),
        
        // Special Enchantments
        AQUA_AFFINITY(8, "Aqua Affinity", "Mine underwater normally"),
        SILK_TOUCH(9, "Silk Touch", "Collect blocks as items"),
        MENDING(10, "Mending", "Repair with XP orbs");
        
        public final int id;
        public final String displayName;
        public final String description;
        
        Enchantment(int id, String displayName, String description) {
            this.id = id;
            this.displayName = displayName;
            this.description = description;
        }
    }
    
    public static class EnchantedItem {
        public String itemName;
        public int level;
        public Enchantment enchantment;
        
        public EnchantedItem(String itemName, Enchantment enchantment, int level) {
            this.itemName = itemName;
            this.enchantment = enchantment;
            this.level = Math.min(level, 3);  // Max level 3
        }
        
        public float getBonus() {
            return 1.0f + (level * 0.25f);  // 25% bonus per level
        }
        
        @Override
        public String toString() {
            return String.format("%s [%s %d]", itemName, enchantment.displayName, level);
        }
    }
    
    /**
     * Enchant an item at the enchantment table
     */
    public static EnchantedItem enchantItem(String itemName, Enchantment enchantment, int level) {
        return new EnchantedItem(itemName, enchantment, level);
    }
    
    /**
     * Apply enchantment effects to gameplay
     */
    public static float applyEnchantmentEffect(EnchantedItem item, String effectType) {
        if (item == null) return 1.0f;
        
        switch(effectType) {
            case "DAMAGE":
                if (item.enchantment == Enchantment.SHARPNESS) {
                    return item.getBonus() * 1.5f;
                }
                break;
            case "MINING_SPEED":
                if (item.enchantment == Enchantment.EFFICIENCY) {
                    return item.getBonus() * 2.0f;
                }
                break;
            case "DURABILITY":
                if (item.enchantment == Enchantment.UNBREAKING) {
                    return 1.0f - (0.2f * item.level);  // Takes longer to break
                }
                break;
            case "PROTECTION":
                if (item.enchantment == Enchantment.PROTECTION) {
                    return 1.0f - (0.15f * item.level);  // Reduces damage
                }
                break;
        }
        return 1.0f;
    }
}
