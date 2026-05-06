package akincraft.game;

/**
 * Item drops system - determines what drops when blocks are broken
 */
public class ItemDrops {
    
    public static class ItemDrop {
        public String itemId;
        public int quantity;
        public int minXp;
        public int maxXp;
        
        public ItemDrop(String itemId, int quantity, int minXp, int maxXp) {
            this.itemId = itemId;
            this.quantity = quantity;
            this.minXp = minXp;
            this.maxXp = maxXp;
        }
    }
    
    /**
     * Get what drops when a block is broken
     */
    public static ItemDrop getBlockDrop(String blockType) {
        switch (blockType) {
            case "DIRT":
                return new ItemDrop("DIRT", 1, 0, 0);
            case "GRASS":
                return new ItemDrop("DIRT", 1, 0, 0); // Grass drops dirt
            case "STONE":
                return new ItemDrop("COBBLESTONE", 1, 10, 20);
            case "COBBLESTONE":
                return new ItemDrop("COBBLESTONE", 1, 0, 0);
            case "OAK_LOG":
                return new ItemDrop("OAK_LOG", 1, 15, 30);
            case "OAK_LEAVES":
                // Only drops leaves 12% of the time
                return Math.random() < 0.12 ? new ItemDrop("OAK_LEAVES", 1, 0, 0) : new ItemDrop("STICK", 1, 0, 0);
            case "BIRCH_LOG":
                return new ItemDrop("BIRCH_LOG", 1, 15, 30);
            case "COAL_ORE":
                return new ItemDrop("COAL", 1, 20, 40);
            case "IRON_ORE":
                return new ItemDrop("RAW_IRON", 1, 50, 100);
            case "GOLD_ORE":
                return new ItemDrop("RAW_GOLD", 1, 75, 150);
            case "DIAMOND_ORE":
                return new ItemDrop("DIAMOND", 1, 100, 200);
            case "SAND":
                return new ItemDrop("SAND", 1, 0, 0);
            case "GRAVEL":
                // Gravel has 10% chance to drop flint
                return Math.random() < 0.1 ? new ItemDrop("FLINT", 1, 0, 0) : new ItemDrop("GRAVEL", 1, 0, 0);
            case "FURNACE":
                return new ItemDrop("FURNACE", 1, 0, 0);
            case "CRAFTING_TABLE":
                return new ItemDrop("CRAFTING_TABLE", 1, 0, 0);
            case "CHEST":
                return new ItemDrop("CHEST", 1, 0, 0);
            default:
                return new ItemDrop("STONE", 1, 0, 0);
        }
    }
    
    /**
     * Calculate XP drop when mining a block
     */
    public static int calculateXpDrop(String toolType, String blockType) {
        ItemDrop drop = getBlockDrop(blockType);
        int baseXp = (drop.minXp + drop.maxXp) / 2;
        
        // Bonus XP with right tool
        if ((toolType.contains("PICKAXE") && blockType.contains("ORE")) ||
            (toolType.contains("AXE") && blockType.contains("LOG")) ||
            (toolType.contains("SHOVEL") && (blockType.contains("DIRT") || blockType.contains("SAND")))) {
            baseXp = (int)(baseXp * 1.5f);
        }
        
        return baseXp + (int)(Math.random() * 20) - 10;
    }
}
