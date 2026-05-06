package akincraft.world;

import org.joml.Vector3f;
import java.util.*;

/**
 * Handles block breaking mechanics with tool-based speed modifiers
 */
public class BlockBreaker {
    private final WorldManager worldManager;
    private float breakProgress = 0.0f;
    private Vector3f targetBlock = null;
    private Map<String, Float> toolStrengths;
    
    public BlockBreaker(WorldManager worldManager) {
        this.worldManager = worldManager;
        this.toolStrengths = initializeToolStrengths();
    }
    
    private Map<String, Float> initializeToolStrengths() {
        Map<String, Float> strengths = new HashMap<>();
        strengths.put("wooden_pickaxe", 2.0f);
        strengths.put("stone_pickaxe", 4.0f);
        strengths.put("iron_pickaxe", 6.0f);
        strengths.put("diamond_pickaxe", 8.0f);
        strengths.put("wooden_axe", 1.5f);
        strengths.put("stone_axe", 3.0f);
        strengths.put("iron_axe", 5.0f);
        strengths.put("diamond_axe", 7.0f);
        strengths.put("wooden_shovel", 1.0f);
        strengths.put("fist", 0.5f);
        return strengths;
    }
    
    public boolean canBreakBlock(BlockType type, String tool) {
        // Only pickaxes can mine stone/ore
        if ((type == BlockType.STONE || type == BlockType.COAL_ORE || type == BlockType.IRON_ORE || 
             type == BlockType.GOLD_ORE || type == BlockType.DIAMOND_ORE) && !tool.contains("pickaxe")) {
            return false;
        }
        // Axes for wood, shovels for dirt/sand
        if ((type == BlockType.OAK_LOG || type == BlockType.BIRCH_LOG || type == BlockType.SPRUCE_LOG) && 
            !tool.contains("axe") && !tool.equals("fist")) {
            return false;
        }
        return true;
    }
    
    public void startBreaking(Vector3f blockPos, String tool) {
        targetBlock = blockPos;
        breakProgress = 0.0f;
    }
    
    public void updateBreaking(String tool, float deltaTime) {
        if (targetBlock == null) return;
        
        float strength = toolStrengths.getOrDefault(tool, 0.5f);
        breakProgress += strength * deltaTime;
        
        if (breakProgress >= 1.0f) {
            finishBreaking();
        }
    }
    
    public void finishBreaking() {
        if (targetBlock != null) {
            int x = (int) targetBlock.x;
            int y = (int) targetBlock.y;
            int z = (int) targetBlock.z;
            BlockType blockType = worldManager.getBlock(x, y, z);
            
            if (blockType != BlockType.AIR) {
                // In a full implementation, would modify the world and spawn items
                // For now, just track that block was broken
                // TODO: worldManager.setBlock(x, y, z, BlockType.AIR);
                // TODO: spawn item entity at blockPos
            }
            resetBreaking();
        }
    }
    
    public void resetBreaking() {
        targetBlock = null;
        breakProgress = 0.0f;
    }
    
    public float getBreakProgress() {
        return Math.min(breakProgress, 1.0f);
    }
}
