package akincraft.game;

/**
 * Experience and leveling system
 * Tracks player XP, levels, and unlocks crafting recipes
 */
public class ExperienceSystem {
    
    private int totalExperience = 0;
    private int currentLevel = 0;
    private int currentXp = 0; // XP toward next level
    private int xpForNextLevel = 17; // Increases with level
    
    public static final int MAX_LEVEL = 30;
    
    /**
     * Calculate XP needed for next level
     * Formula similar to Minecraft
     */
    private int calculateXpForLevel(int level) {
        if (level < 16) {
            return 2 * level + 7;
        } else if (level < 31) {
            return 5 * level - 38;
        } else {
            return 9 * level - 158;
        }
    }
    
    /**
     * Add experience points
     */
    public void addExperience(int amount) {
        totalExperience += amount;
        currentXp += amount;
        
        // Level up if threshold reached
        while (currentXp >= xpForNextLevel && currentLevel < MAX_LEVEL) {
            currentXp -= xpForNextLevel;
            currentLevel++;
            xpForNextLevel = calculateXpForLevel(currentLevel);
            onLevelUp();
        }
    }
    
    /**
     * Called when player levels up
     */
    private void onLevelUp() {
        System.out.println("Level UP! Now level " + currentLevel);
        // Could trigger particle effects, sound, UI elements, etc.
    }
    
    /**
     * Get percentage progress to next level (0.0 to 1.0)
     */
    public float getProgressToNextLevel() {
        return (float) currentXp / xpForNextLevel;
    }
    
    public int getTotalExperience() {
        return totalExperience;
    }
    
    public int getCurrentLevel() {
        return currentLevel;
    }
    
    public int getCurrentXp() {
        return currentXp;
    }
    
    public int getXpForNextLevel() {
        return xpForNextLevel;
    }
    
    /**
     * Check if player has enough level for crafting recipe
     */
    public boolean meetsLevelRequirement(int requiredLevel) {
        return currentLevel >= requiredLevel;
    }
    
    /**
     * Get list of unlocked crafting recipes based on level
     */
    public String[] getUnlockedRecipes() {
        // This would be expanded with actual recipe registry
        String[] recipes = new String[100];
        
        if (currentLevel >= 0) {
            recipes[0] = "WOODEN_PICKAXE";
            recipes[1] = "WOODEN_AXE";
        }
        
        if (currentLevel >= 5) {
            recipes[2] = "STONE_PICKAXE";
            recipes[3] = "FURNACE";
        }
        
        if (currentLevel >= 15) {
            recipes[4] = "IRON_PICKAXE";
            recipes[5] = "DIAMOND_PICKAXE";
        }
        
        return recipes;
    }
    
    /**
     * Add experience orb (when mining, defeating mobs, etc.)
     */
    public static int getOrbExperience(String source) {
        switch (source) {
            case "COAL_ORE": return 17;
            case "IRON_ORE": return 25;
            case "GOLD_ORE": return 50;
            case "DIAMOND_ORE": return 150;
            case "ZOMBIE_KILL": return 10;
            case "CREEPER_KILL": return 5;
            case "SMELT": return 10;
            case "CRAFT": return 5;
            default: return 0;
        }
    }
    
    /**
     * Reset experience (for respawn)
     */
    public void reset() {
        totalExperience = 0;
        currentLevel = 0;
        currentXp = 0;
        xpForNextLevel = 17;
    }
}
