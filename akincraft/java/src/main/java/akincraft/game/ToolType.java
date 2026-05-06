package akincraft.game;

/**
 * Tool types and their properties
 */
public enum ToolType {
    WOODEN_PICKAXE("Wooden Pickaxe", 2, 60),
    STONE_PICKAXE("Stone Pickaxe", 4, 131),
    IRON_PICKAXE("Iron Pickaxe", 6, 250),
    DIAMOND_PICKAXE("Diamond Pickaxe", 8, 1561),
    
    WOODEN_AXE("Wooden Axe", 3, 59),
    STONE_AXE("Stone Axe", 5, 132),
    IRON_AXE("Iron Axe", 7, 251),
    DIAMOND_AXE("Diamond Axe", 9, 1562),
    
    WOODEN_SHOVEL("Wooden Shovel", 1, 59),
    STONE_SHOVEL("Stone Shovel", 3, 131),
    IRON_SHOVEL("Iron Shovel", 5, 250),
    DIAMOND_SHOVEL("Diamond Shovel", 7, 1561),
    
    WOODEN_SWORD("Wooden Sword", 4, 59),
    STONE_SWORD("Stone Sword", 5, 131),
    IRON_SWORD("Iron Sword", 6, 250),
    DIAMOND_SWORD("Diamond Sword", 7, 1561);
    
    private final String displayName;
    private final int damage;
    private final int durability;
    private int currentDurability;
    
    ToolType(String displayName, int damage, int durability) {
        this.displayName = displayName;
        this.damage = damage;
        this.durability = durability;
        this.currentDurability = durability;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public int getDamage() {
        return damage;
    }
    
    public int getDurability() {
        return durability;
    }
    
    public float getDurabilityPercent() {
        return (float) currentDurability / durability;
    }
    
    public void damage() {
        if (currentDurability > 0) {
            currentDurability--;
        }
    }
    
    public void repair(int amount) {
        currentDurability = Math.min(currentDurability + amount, durability);
    }
    
    public boolean isBroken() {
        return currentDurability <= 0;
    }
}
