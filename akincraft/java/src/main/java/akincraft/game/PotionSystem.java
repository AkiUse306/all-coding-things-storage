package akincraft.game;

/**
 * Original Akincraft Magic/Potion System
 * Allows players to brew potions with unique effects
 */
public class PotionSystem {
    
    public enum PotionEffect {
        // Beneficial
        SPEED(1, "Speed", 10, true),
        STRENGTH(2, "Strength", 15, true),
        RESISTANCE(3, "Resistance", 12, true),
        REGENERATION(4, "Regeneration", 8, true),
        NIGHT_VISION(5, "Night Vision", 20, true),
        WATER_BREATHING(6, "Water Breathing", 25, true),
        
        // Harmful
        POISON(7, "Poison", 6, false),
        WEAKNESS(8, "Weakness", 8, false),
        SLOWNESS(9, "Slowness", 10, false),
        BLINDNESS(10, "Blindness", 5, false),
        NAUSEA(11, "Nausea", 3, false);
        
        public final int id;
        public final String name;
        public final int defaultDuration;  // seconds
        public final boolean beneficial;
        
        PotionEffect(int id, String name, int defaultDuration, boolean beneficial) {
            this.id = id;
            this.name = name;
            this.defaultDuration = defaultDuration;
            this.beneficial = beneficial;
        }
    }
    
    public static class Potion {
        public PotionEffect effect;
        public int level;
        public int durationSeconds;
        public float amplifier;
        
        public Potion(PotionEffect effect, int level, int durationSeconds) {
            this.effect = effect;
            this.level = Math.min(level, 2);  // Max level 2
            this.durationSeconds = durationSeconds;
            this.amplifier = 1.0f + (level * 0.5f);
        }
        
        @Override
        public String toString() {
            return String.format("%s (Level %d) - %ds", effect.name, level, durationSeconds);
        }
    }
    
    /**
     * Brew a potion from ingredients
     */
    public static Potion brewPotion(String baseIngredient, String modifierIngredient, int level) {
        // Simple potion brewing logic
        if (baseIngredient.equals("nether_wart")) {
            if (modifierIngredient.equals("redstone")) {
                return new Potion(PotionEffect.STRENGTH, level, 20);
            } else if (modifierIngredient.equals("glowstone")) {
                return new Potion(PotionEffect.SPEED, level, 15);
            } else if (modifierIngredient.equals("golden_carrot")) {
                return new Potion(PotionEffect.NIGHT_VISION, level, 30);
            }
        }
        return null;
    }
    
    /**
     * Apply potion effect to player
     */
    public static void applyPotionEffect(Potion potion, PlayerStats player) {
        if (potion == null) return;
        
        switch(potion.effect) {
            case SPEED:
                player.addSpeedBoost(potion.amplifier);
                break;
            case STRENGTH:
                player.addDamageBoost(potion.amplifier);
                break;
            case RESISTANCE:
                player.addDamageReduction(potion.amplifier);
                break;
            case REGENERATION:
                player.enableRegeneration(potion.durationSeconds);
                break;
            case NIGHT_VISION:
                player.enableNightVision(potion.durationSeconds);
                break;
            case WATER_BREATHING:
                player.enableWaterBreathing(potion.durationSeconds);
                break;
            case POISON:
                player.takePoisonDamage(potion.amplifier, potion.durationSeconds);
                break;
            case WEAKNESS:
                player.addDamageReduction(1.0f / potion.amplifier);
                break;
            case SLOWNESS:
                player.addSpeedBoost(1.0f / potion.amplifier);
                break;
            default:
                break;
        }
    }
}
