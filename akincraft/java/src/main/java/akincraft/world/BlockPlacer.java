package akincraft.world;

import org.joml.Vector3f;

/**
 * Handles block placement with collision and neighbor checks
 */
public class BlockPlacer {
    private final WorldManager worldManager;
    
    public BlockPlacer(WorldManager worldManager) {
        this.worldManager = worldManager;
    }
    
    /**
     * Place a block at target position if valid
     */
    public boolean placeBlock(Vector3f position, BlockType blockType) {
        int x = (int) position.x;
        int y = (int) position.y;
        int z = (int) position.z;
        
        // Check bounds
        if (!isValidPosition(x, y, z)) {
            return false;
        }
        
        // Can only place on air or water
        BlockType existingBlock = worldManager.getBlock(x, y, z);
        if (existingBlock != BlockType.AIR && existingBlock != BlockType.WATER) {
            return false;
        }
        
        // In a full implementation, would call worldManager.setBlock(x, y, z, blockType);
        // For now, just validate the placement
        // TODO: worldManager.setBlock(x, y, z, blockType);
        notifyNeighbors(x, y, z);
        return true;
    }
    
    private void notifyNeighbors(int x, int y, int z) {
        // Notify adjacent chunks to rebuild mesh if needed
        int[][] neighbors = {
            {x-1, y, z}, {x+1, y, z},
            {x, y-1, z}, {x, y+1, z},
            {x, y, z-1}, {x, y, z+1}
        };
        
        for (int[] neighbor : neighbors) {
            BlockType blockType = worldManager.getBlock(neighbor[0], neighbor[1], neighbor[2]);
            if (blockType != BlockType.AIR && blockType != BlockType.WATER) {
                // Trigger chunk remesh
            }
        }
    }
    
    private boolean isValidPosition(int x, int y, int z) {
        return y >= 0 && y < 256; // Minecraft-like height limit
    }
}
