package akincraft.game;

/**
 * Enhanced combat system with damage types, knockback, and on-hit effects
 */
public class EnhancedCombat {
    
    public enum DamageType {
        MELEE(1.0f),
        PROJECTILE(0.8f),
        MAGIC(1.2f),
        EXPLOSION(1.5f),
        FALL(0.5f);
        
        private final float multiplier;
        
        DamageType(float multiplier) {
            this.multiplier = multiplier;
        }
        
        public float getMultiplier() { return multiplier; }
    }
    
    /**
     * Calculate damage based on weapon and enchantments
     */
    public static float calculateDamage(String weaponType, int sharpnessLevel) {
        float baseDamage = 0f;
        
        switch (weaponType) {
            case "WOODEN_SWORD": baseDamage = 4.0f; break;
            case "STONE_SWORD": baseDamage = 5.0f; break;
            case "IRON_SWORD": baseDamage = 6.0f; break;
            case "DIAMOND_SWORD": baseDamage = 7.0f; break;
            case "WOODEN_AXE": baseDamage = 7.0f; break;
            case "STONE_AXE": baseDamage = 9.0f; break;
            case "IRON_AXE": baseDamage = 9.0f; break;
            case "DIAMOND_AXE": baseDamage = 10.0f; break;
            case "FIST": baseDamage = 1.0f; break;
            default: baseDamage = 3.0f;
        }
        
        // Sharpness adds 1.25 damage per level
        float sharpnessBonus = sharpnessLevel * 1.25f;
        
        // Random variance (+/- 0.5)
        float variance = (float)(Math.random() - 0.5f);
        
        return baseDamage + sharpnessBonus + variance;
    }
    
    /**
     * Calculate knockback based on weapon and enchantment
     */
    public static float calculateKnockback(String weaponType, int knockbackLevel) {
        float baseKnockback = 0.4f;
        
        // Axes do more knockback
        if (weaponType.contains("AXE")) {
            baseKnockback = 0.65f;
        }
        
        // Knockback enchantment: 0.4 per level
        float enchantmentBonus = knockbackLevel * 0.4f;
        
        return baseKnockback + enchantmentBonus;
    }
    
    /**
     * Apply critical hit (bonus damage when hitting mid-air)
     */
    public static float applyCriticalHit(float damage, boolean isAirborne) {
        if (isAirborne) {
            return damage * 1.5f; // 150% damage
        }
        return damage;
    }
    
    /**
     * Calculate effective damage after armor
     */
    public static float applyArmorReduction(float damage, float armorPoints) {
        // Each armor point reduces 4% damage
        float reduction = Math.min(armorPoints * 0.04f, 0.8f); // Max 80% reduction
        return damage * (1.0f - reduction);
    }
    
    /**
     * Handle on-hit effects like fire aspect
     */
    public static void applyOnHitEffect(String enchantment, Object target) {
        switch (enchantment) {
            case "FIRE_ASPECT":
                // Apply fire status effect
                break;
            case "FROST_WALKER":
                // Apply slowness
                break;
            case "LIFE_STEAL":
                // Heal attacker
                break;
        }
    }
    
    /**
     * Calculate attack cooldown
     */
    public static float getAttackCooldown(String weaponType) {
        switch (weaponType) {
            case "SWORD": return 0.625f;
            case "AXE": return 1.0f;
            case "PICKAXE": return 1.2f;
            case "SHOVEL": return 0.8f;
            default: return 1.0f;
        }
    }
}
