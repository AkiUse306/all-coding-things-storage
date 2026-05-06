package akincraft.game;

/**
 * Combat and damage system
 */
public class CombatSystem {
    
    /**
     * Calculate damage with enchantments and armor
     */
    public static float calculateDamage(ToolType weapon, float armorLevel, float enchantmentMultiplier) {
        float baseDamage = weapon.getDamage();
        
        // Armor reduces damage
        float armorReduction = Math.min(armorLevel * 0.04f, 0.8f); // Max 80% reduction
        
        // Enchantment multiplier (Sharpness, etc)
        float finalDamage = baseDamage * (1 - armorReduction) * enchantmentMultiplier;
        
        return Math.max(0.5f, finalDamage); // Minimum 0.5 damage
    }
    
    /**
     * Calculate critical strike (1.5x damage when falling)
     */
    public static float applyCriticalStrike(float damage, float yVelocity) {
        if (yVelocity < -0.5f) {
            return damage * 1.5f;
        }
        return damage;
    }
    
    /**
     * Get knockback value based on weapon
     */
    public static float getKnockback(ToolType weapon) {
        if (weapon.toString().contains("SWORD")) {
            return 0.5f;
        } else if (weapon.toString().contains("AXE")) {
            return 0.7f;
        }
        return 0.0f;
    }
}
