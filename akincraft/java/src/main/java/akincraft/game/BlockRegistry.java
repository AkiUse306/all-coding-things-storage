package akincraft.game;

import java.util.*;

/**
 * Comprehensive block registry with properties and tools
 */
public class BlockRegistry {
    
    public static class BlockProperties {
        public String id;
        public String displayName;
        public float hardness;           // Mining time multiplier
        public String[] toolEffective;   // Which tools mine it effectively
        public String drops;             // What item drops
        public int dropQuantity;
        public boolean transparent;
        public boolean solid;
        public float friction;
        public int lightLevel;           // 0-15, emitted light
        
        public BlockProperties(String id, String name, float hardness, String[] tools, 
                              String drops, int quantity, boolean transparent, boolean solid) {
            this.id = id;
            this.displayName = name;
            this.hardness = hardness;
            this.toolEffective = tools;
            this.drops = drops;
            this.dropQuantity = quantity;
            this.transparent = transparent;
            this.solid = solid;
            this.friction = solid ? 0.6f : 0.0f;
            this.lightLevel = 0;
        }
    }
    
    private static final Map<String, BlockProperties> REGISTRY = new HashMap<>();
    
    static {
        // Natural terrain
        register("DIRT", "Dirt", 0.5f, new String[]{"SHOVEL"}, "DIRT", 1, false, true);
        register("GRASS", "Grass Block", 0.6f, new String[]{"SHOVEL"}, "DIRT", 1, false, true);
        register("STONE", "Stone", 1.5f, new String[]{"PICKAXE"}, "COBBLESTONE", 1, false, true);
        register("COBBLESTONE", "Cobblestone", 2.0f, new String[]{"PICKAXE"}, "COBBLESTONE", 1, false, true);
        register("SAND", "Sand", 0.5f, new String[]{"SHOVEL"}, "SAND", 1, false, true);
        register("GRAVEL", "Gravel", 0.6f, new String[]{"SHOVEL"}, "GRAVEL", 1, false, true);
        
        // Logs and leaves
        register("OAK_LOG", "Oak Log", 2.0f, new String[]{"AXE"}, "OAK_LOG", 1, false, true);
        register("BIRCH_LOG", "Birch Log", 2.0f, new String[]{"AXE"}, "BIRCH_LOG", 1, false, true);
        register("SPRUCE_LOG", "Spruce Log", 2.0f, new String[]{"AXE"}, "SPRUCE_LOG", 1, false, true);
        register("OAK_LEAVES", "Oak Leaves", 0.2f, new String[]{}, "STICK", 1, true, false);
        
        // Ores
        register("COAL_ORE", "Coal Ore", 3.0f, new String[]{"PICKAXE"}, "COAL", 1, false, true);
        register("IRON_ORE", "Iron Ore", 5.0f, new String[]{"STONE_PICKAXE", "IRON_PICKAXE", "DIAMOND_PICKAXE"}, "RAW_IRON", 1, false, true);
        register("GOLD_ORE", "Gold Ore", 6.0f, new String[]{"IRON_PICKAXE", "DIAMOND_PICKAXE"}, "RAW_GOLD", 1, false, true);
        register("DIAMOND_ORE", "Diamond Ore", 8.0f, new String[]{"DIAMOND_PICKAXE"}, "DIAMOND", 1, false, true);
        
        // Crafted blocks
        register("OAK_PLANK", "Oak Planks", 2.0f, new String[]{}, "OAK_PLANK", 1, false, true);
        register("CRAFTING_TABLE", "Crafting Table", 2.5f, new String[]{}, "CRAFTING_TABLE", 1, false, true);
        register("FURNACE", "Furnace", 3.5f, new String[]{}, "FURNACE", 1, false, true);
        register("CHEST", "Chest", 2.5f, new String[]{}, "CHEST", 1, false, true);
        
        // Special
        register("WATER", "Water", -1.0f, new String[]{}, null, 0, true, false);
        register("LAVA", "Lava", -1.0f, new String[]{}, null, 0, true, false);
        
        // Transparent/light emitters
        BlockProperties glass = new BlockProperties("GLASS", "Glass", 0.3f, new String[]{}, "GLASS", 1, true, true);
        REGISTRY.put(glass.id, glass);
        
        BlockProperties glowstone = new BlockProperties("GLOWSTONE", "Glowstone", 0.3f, new String[]{}, "GLOWSTONE", 2, true, true);
        glowstone.lightLevel = 15;
        REGISTRY.put(glowstone.id, glowstone);
    }
    
    public static void register(String id, String name, float hardness, String[] tools, 
                               String drops, int quantity, boolean transparent, boolean solid) {
        REGISTRY.put(id, new BlockProperties(id, name, hardness, tools, drops, quantity, transparent, solid));
    }
    
    public static BlockProperties get(String blockId) {
        return REGISTRY.getOrDefault(blockId, new BlockProperties("STONE", "Unknown", 1.5f, new String[]{"PICKAXE"}, "COBBLESTONE", 1, false, true));
    }
    
    /**
     * Check if tool can mine this block effectively
     */
    public static boolean isToolEffective(String blockId, String toolType) {
        BlockProperties block = get(blockId);
        for (String effective : block.toolEffective) {
            if (toolType.contains(effective)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get mining time for block with tool
     */
    public static float getMiningTime(String blockId, String toolType) {
        BlockProperties block = get(blockId);
        float baseTime = block.hardness;
        
        if (isToolEffective(blockId, toolType)) {
            return baseTime * 0.5f; // 50% faster with right tool
        }
        return baseTime * 3.0f; // 300% slower with wrong tool
    }
    
    /**
     * Check if block can have light
     */
    public static boolean emitsLight(String blockId) {
        return get(blockId).lightLevel > 0;
    }
    
    public static int getLightLevel(String blockId) {
        return get(blockId).lightLevel;
    }
    
    /**
     * Get all registered blocks
     */
    public static Collection<BlockProperties> getAllBlocks() {
        return REGISTRY.values();
    }
}
