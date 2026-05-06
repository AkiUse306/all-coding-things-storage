package akincraft.game;

/**
 * Game mode system - Survival and Creative modes
 */
public class GameMode {
    
    public enum Mode {
        SURVIVAL("Survival", true, true, false, false),
        CREATIVE("Creative", false, false, true, true),
        ADVENTURE("Adventure", true, false, false, false);
        
        private final String displayName;
        private final boolean hungerEnabled;
        private final boolean mobDamageEnabled;
        private final boolean infiniteItems;
        private final boolean flyingEnabled;
        
        Mode(String displayName, boolean hungerEnabled, boolean mobDamageEnabled, 
             boolean infiniteItems, boolean flyingEnabled) {
            this.displayName = displayName;
            this.hungerEnabled = hungerEnabled;
            this.mobDamageEnabled = mobDamageEnabled;
            this.infiniteItems = infiniteItems;
            this.flyingEnabled = flyingEnabled;
        }
        
        public String getDisplayName() { return displayName; }
        public boolean isHungerEnabled() { return hungerEnabled; }
        public boolean isMobDamageEnabled() { return mobDamageEnabled; }
        public boolean hasInfiniteItems() { return infiniteItems; }
        public boolean isFlyingEnabled() { return flyingEnabled; }
    }
    
    private Mode currentMode;
    private boolean isFlying = false;
    private float flySpeed = 0.1f;
    
    public GameMode(Mode mode) {
        this.currentMode = mode;
    }
    
    public void setMode(Mode mode) {
        this.currentMode = mode;
        if (mode == Mode.CREATIVE) {
            this.isFlying = true;
        } else {
            this.isFlying = false;
        }
    }
    
    public Mode getMode() {
        return currentMode;
    }
    
    public void toggleFly() {
        if (currentMode.isFlyingEnabled()) {
            isFlying = !isFlying;
        }
    }
    
    public boolean isFlying() {
        return isFlying;
    }
    
    public float getFlySpeed() {
        return flySpeed;
    }
    
    public void setFlySpeed(float speed) {
        this.flySpeed = Math.max(0.05f, Math.min(speed, 0.5f)); // Clamp between 0.05 and 0.5
    }
    
    /**
     * Check if action is allowed in current mode
     */
    public boolean canTakeDamage() {
        return currentMode != Mode.CREATIVE;
    }
    
    public boolean canBreakBlocks() {
        return currentMode != Mode.CREATIVE; // Creative breaks instantly
    }
    
    public boolean canPlaceBlocks() {
        return true; // All modes can place
    }
    
    public boolean canInteractWithMobs() {
        return true;
    }
    
    public boolean canGetDamageFromMobs() {
        return currentMode.isMobDamageEnabled();
    }
    
    public boolean needsFood() {
        return currentMode.isHungerEnabled();
    }
    
    public boolean hasUnlimitedResources() {
        return currentMode.hasInfiniteItems();
    }
    
    /**
     * Get selected item stack size (creative has infinite)
     */
    public int getMaxStackSize(int normalMax) {
        return hasUnlimitedResources() ? Integer.MAX_VALUE : normalMax;
    }
}
