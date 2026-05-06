package akincraft.game;

/**
 * Advanced Mana & Spell System
 * Resource-based ability casting with cooldowns
 */
public class ManaSystem {
    
    private float mana;
    private float maxMana;
    private float manaRegenRate;
    
    public ManaSystem() {
        this.maxMana = 100.0f;
        this.mana = maxMana;
        this.manaRegenRate = 2.0f;  // Mana per second
    }
    
    public void update(float deltaTime) {
        if (mana < maxMana) {
            mana = Math.min(mana + (manaRegenRate * deltaTime), maxMana);
        }
    }
    
    public boolean canCast(float manaCost) {
        return mana >= manaCost;
    }
    
    public void spendMana(float amount) {
        mana = Math.max(0, mana - amount);
    }
    
    public void restoreMana(float amount) {
        mana = Math.min(maxMana, mana + amount);
    }
    
    public float getManaPercent() {
        return (mana / maxMana) * 100;
    }
    
    public float getMana() { return mana; }
    public float getMaxMana() { return maxMana; }
    public void setMaxMana(float max) { this.maxMana = max; }
}
