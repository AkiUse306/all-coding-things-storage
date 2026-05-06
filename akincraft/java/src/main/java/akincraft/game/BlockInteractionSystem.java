package akincraft.game;

/**
 * Block interaction system - placing, breaking, and using blocks
 */
public class BlockInteractionSystem {
    
    public enum InteractionType {
        BREAK,
        PLACE,
        USE,
        HARVEST
    }
    
    public static class BlockInteraction {
        public InteractionType type;
        public String blockType;
        public String toolUsed;
        public float duration;
        public boolean resultSuccess;
        
        public BlockInteraction(InteractionType type, String blockType, String toolUsed) {
            this.type = type;
            this.blockType = blockType;
            this.toolUsed = toolUsed;
        }
    }
    
    /**
     * Calculate time to break a block with tool
     */
    public static float getBreakTime(String blockId, String toolType) {
        float baseTime = BlockRegistry.getMiningTime(blockId, toolType);
        
        // Tool durability affects speed
        // Damaged tools are slower
        return baseTime;
    }
    
    /**
     * Can player place block here
     */
    public static boolean canPlaceBlock(String blockId, float playerX, float playerY, float playerZ,
                                       String[] surroundingBlocks) {
        // Check collision with entity (player should not be overlapping)
        // Check if block is replaceable (like water, plants)
        
        BlockRegistry.BlockProperties block = BlockRegistry.get(blockId);
        
        // Most blocks can be placed if space is empty
        if (!block.solid) {
            return true; // Can place non-solid blocks anywhere
        }
        
        // Solid blocks need an empty space
        return true; // Simplified - in real game check actual space
    }
    
    /**
     * Handle block use (like doors, furnaces, chests)
     */
    public static void useBlock(String blockId, PlayerController player) {
        switch (blockId) {
            case "CRAFTING_TABLE":
                // Open crafting menu
                player.openCraftingMenu();
                break;
            case "FURNACE":
                // Open furnace interface
                player.openFurnaceMenu();
                break;
            case "CHEST":
                // Open chest interface
                player.openContainerMenu();
                break;
            case "DOOR":
                // Toggle door open/closed
                break;
            case "LEVER":
                // Toggle lever
                break;
            case "BUTTON":
                // Activate mechanisms
                break;
        }
    }
    
    /**
     * Check if block is harvestable (drops items when broken)
     */
    public static boolean isHarvestable(String blockId, String toolType) {
        BlockRegistry.BlockProperties block = BlockRegistry.get(blockId);
        
        // If tool is effective, always harvestable
        if (BlockRegistry.isToolEffective(blockId, toolType)) {
            return true;
        }
        
        // Some blocks can be broken by hand but won't drop
        return false;
    }
    
    /**
     * Calculate drops for broken block
     */
    public static ItemDrops.ItemDrop calculateDrops(String blockId, String toolType) {
        ItemDrops.ItemDrop drop = ItemDrops.getBlockDrop(blockId);
        
        // Reduce drops if tool isn't effective
        if (!BlockRegistry.isToolEffective(blockId, toolType)) {
            drop.quantity = 0;
        }
        
        return drop;
    }
    
    /**
     * Calculate XP drop
     */
    public static int calculateXpDrop(String blockId, String toolType) {
        return ItemDrops.calculateXpDrop(toolType, blockId);
    }
    
    /**
     * Handle block replacement (water flowing, etc)
     */
    public static boolean doesBlockReplace(String newBlock, String existingBlock) {
        // Water and lava can replace certain blocks
        if ("WATER".equals(newBlock) || "LAVA".equals(newBlock)) {
            // Can only replace replaceable blocks (plants, leaves, etc)
            return !BlockRegistry.get(existingBlock).solid;
        }
        
        return false;
    }
    
    /**
     * Get block light level for ambient occlusion
     */
    public static int getBlockLuminance(String blockId) {
        return BlockRegistry.getLightLevel(blockId);
    }
    
    /**
     * Check if block is transparent (affects visibility)
     */
    public static boolean isBlockTransparent(String blockId) {
        return BlockRegistry.get(blockId).transparent;
    }
    
    /**
     * Get friction for physics (affects movement speed)
     */
    public static float getBlockFriction(String blockId) {
        return BlockRegistry.get(blockId).friction;
    }
}
