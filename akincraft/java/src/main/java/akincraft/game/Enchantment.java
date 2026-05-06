package akincraft.game;

import java.util.*;

/**
 * Enchantment system for tools and armor
 */
public enum Enchantment {
    SHARPNESS(1.2f, "Increases damage"),
    EFFICIENCY(1.5f, "Increases mining speed"),
    DURABILITY(1.3f, "Increases tool durability"),
    FORTUNE(0.5f, "Increases block drops"),
    PROTECTION(1.2f, "Reduces damage taken"),
    UNBREAKING(2.0f, "Reduces durability loss");
    
    private final float multiplier;
    private final String description;
    
    Enchantment(float multiplier, String description) {
        this.multiplier = multiplier;
        this.description = description;
    }
    
    public float getMultiplier() { return multiplier; }
    public String getDescription() { return description; }
}

/**
 * Potion effects system
 */
enum PotionEffect {
    SPEED("Speed", 5.0f),
    SLOWNESS("Slowness", 0.5f),
    STRENGTH("Strength", 1.5f),
    WEAKNESS("Weakness", 0.5f),
    REGENERATION("Regeneration", 1.0f),
    POISON("Poison", -1.0f),
    FIRE_RESISTANCE("Fire Resistance", 1.0f),
    INVISIBILITY("Invisibility", 1.0f),
    NIGHT_VISION("Night Vision", 1.0f),
    WATER_BREATHING("Water Breathing", 1.0f);
    
    private final String displayName;
    private final float strength;
    private float duration;
    
    PotionEffect(String displayName, float strength) {
        this.displayName = displayName;
        this.strength = strength;
        this.duration = 0;
    }
    
    public String getDisplayName() { return displayName; }
    public float getStrength() { return strength; }
    public float getDuration() { return duration; }
    public void setDuration(float duration) { this.duration = duration; }
}

/**
 * Active potion effects tracker
 */
class PotionEffectList {
    private Map<PotionEffect, Float> activeEffects = new HashMap<>();
    
    public void addEffect(PotionEffect effect, float duration) {
        activeEffects.put(effect, duration);
    }
    
    public void update(float deltaTime) {
        List<PotionEffect> toRemove = new ArrayList<>();
        for (Map.Entry<PotionEffect, Float> entry : activeEffects.entrySet()) {
            float newDuration = entry.getValue() - deltaTime;
            if (newDuration <= 0) {
                toRemove.add(entry.getKey());
            } else {
                activeEffects.put(entry.getKey(), newDuration);
            }
        }
        activeEffects.keySet().removeAll(toRemove);
    }
    
    public boolean hasEffect(PotionEffect effect) {
        return activeEffects.containsKey(effect);
    }
    
    public float getEffectStrength(PotionEffect effect) {
        return activeEffects.getOrDefault(effect, 0.0f);
    }
}
