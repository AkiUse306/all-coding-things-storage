package akincraft.game;

/**
 * Mining system - handles break times and XP gain
 * Similar to Minecraft's block breaking mechanics
 */
public class MiningSystem {
    
    public enum BlockHardness {
        // Block name, break time in seconds, xp drop
        DIRT(0.5f, 0),
        GRASS(0.6f, 0),
        STONE(1.5f, 10),
        COBBLESTONE(2.0f, 0),
        COAL_ORE(3.0f, 20),
        IRON_ORE(5.0f, 50),
        GOLD_ORE(6.0f, 75),
        DIAMOND_ORE(8.0f, 100),
        OBSIDIAN(15.0f, 150),
        OAK_LOG(2.0f, 15),
        OAK_LEAVES(0.2f, 0),
        SAND(0.5f, 0),
        GRAVEL(0.6f, 0);
        
        private final float breakTime;
        private final int xpDrop;
        
        BlockHardness(float breakTime, int xpDrop) {
            this.breakTime = breakTime;
            this.xpDrop = xpDrop;
        }
        
        public float getBreakTime() { return breakTime; }
        public int getXpDrop() { return xpDrop; }
    }
    
    private float miningProgress = 0.0f;
    private String currentBlock = null;
    private float miningSpeed = 1.0f; // Affected by tool type
    
    /**
     * Get effective mining speed based on tool efficiency
     */
    public float getToolEfficiency(String toolType, String blockType) {
        // Tool efficiency multiplier (like Minecraft)
        float baseSpeed = 1.0f;
        
        // Match tool to block for speed bonus
        if (toolType.equals("DIAMOND_PICKAXE") && blockType.contains("ORE")) {
            baseSpeed = 3.5f;
        } else if (toolType.equals("IRON_PICKAXE") && blockType.contains("ORE")) {
            baseSpeed = 2.5f;
        } else if (toolType.equals("STONE_PICKAXE") && blockType.contains("ORE")) {
            baseSpeed = 1.8f;
        } else if (toolType.equals("WOODEN_PICKAXE") && blockType.contains("ORE")) {
            baseSpeed = 1.2f;
        }
        // Axes are faster on logs
        else if (toolType.contains("AXE") && blockType.contains("LOG")) {
            baseSpeed = 1.5f;
        }
        // Shovels are faster on dirt/sand
        else if (toolType.contains("SHOVEL") && (blockType.equals("DIRT") || blockType.equals("SAND"))) {
            baseSpeed = 2.0f;
        }
        
        return baseSpeed;
    }
    
    /**
     * Calculate actual break time accounting for tool and enchantments
     */
    public float getAdjustedBreakTime(BlockHardness block, String toolType, int efficiencyLevel) {
        float baseTime = block.getBreakTime();
        float toolBonus = getToolEfficiency(toolType, block.name());
        float efficiencyBonus = 1.0f + (efficiencyLevel * 0.25f);
        
        return baseTime / (toolBonus * efficiencyBonus);
    }
    
    /**
     * Update mining progress (call every frame while holding attack button)
     */
    public void updateMining(float deltaTime, String toolType, BlockHardness block) {
        if (block == null) {
            miningProgress = 0.0f;
            return;
        }
        
        float adjustedBreakTime = getAdjustedBreakTime(block, toolType, 0);
        miningProgress += (deltaTime / adjustedBreakTime) * 100.0f; // 0-100 progress
        miningProgress = Math.min(miningProgress, 100.0f);
    }
    
    public void stopMining() {
        miningProgress = 0.0f;
        currentBlock = null;
    }
    
    public float getMiningProgress() {
        return miningProgress;
    }
    
    public boolean isMiningComplete() {
        return miningProgress >= 100.0f;
    }
    
    public int getXpReward(BlockHardness block) {
        // Bonus XP multiplier based on ore rarity
        return block.getXpDrop() + (int)(Math.random() * block.getXpDrop() * 0.5f);
    }
}
