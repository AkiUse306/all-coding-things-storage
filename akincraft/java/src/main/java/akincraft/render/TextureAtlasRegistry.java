package akincraft.render;

import akincraft.world.BlockType;

import java.util.EnumMap;

public final class TextureAtlasRegistry {
    private static final EnumMap<BlockType, float[]> topUv = new EnumMap<>(BlockType.class);
    private static final EnumMap<BlockType, float[]> bottomUv = new EnumMap<>(BlockType.class);
    private static final EnumMap<BlockType, float[]> sideUv = new EnumMap<>(BlockType.class);

    static {
        setUv(BlockType.GRASS, 0, 0, 1, 0, 2, 0);
        setUv(BlockType.DIRT, 2, 0);
        setUv(BlockType.STONE, 3, 0);
        setUv(BlockType.SAND, 4, 0);
        setUv(BlockType.WATER, 5, 0);
        setUv(BlockType.OAK_LOG, 6, 0, 7, 0, 7, 0);
        setUv(BlockType.OAK_LEAVES, 0, 1);
        setUv(BlockType.COAL_ORE, 1, 1);
        setUv(BlockType.IRON_ORE, 2, 1);
        setUv(BlockType.GOLD_ORE, 3, 1);
        setUv(BlockType.DIAMOND_ORE, 4, 1);
        setUv(BlockType.GRAVEL, 5, 1);
        setUv(BlockType.COBBLESTONE, 6, 1);
        setUv(BlockType.SMOOTH_STONE, 7, 1);
        setUv(BlockType.OAK_PLANK, 6, 0);

        float[] dirtUv = getUvFromGrid(2, 0);
        topUv.put(BlockType.DIRT, dirtUv);
        bottomUv.put(BlockType.DIRT, dirtUv);
        sideUv.put(BlockType.DIRT, dirtUv);
    }

    private TextureAtlasRegistry() {}

    public static float[] getUv(BlockType block, int dx, int dy, int dz) {
        Face face = dy > 0 ? Face.TOP : dy < 0 ? Face.BOTTOM : Face.SIDE;
        return switch (face) {
            case TOP -> topUv.getOrDefault(block, topUv.get(BlockType.DIRT));
            case BOTTOM -> bottomUv.getOrDefault(block, bottomUv.get(BlockType.DIRT));
            case SIDE -> sideUv.getOrDefault(block, sideUv.get(BlockType.DIRT));
        };
    }

    private static void setUv(BlockType block, int topX, int topY, int sideX, int sideY, int bottomX, int bottomY) {
        topUv.put(block, getUvFromGrid(topX, topY));
        sideUv.put(block, getUvFromGrid(sideX, sideY));
        bottomUv.put(block, getUvFromGrid(bottomX, bottomY));
    }

    private static void setUv(BlockType block, int gridX, int gridY) {
        float[] uv = getUvFromGrid(gridX, gridY);
        topUv.put(block, uv);
        sideUv.put(block, uv);
        bottomUv.put(block, uv);
    }

    private static float[] getUvFromGrid(int gridX, int gridY) {
        float step = 1.0f / 8.0f;
        float u0 = gridX * step;
        float v0 = gridY * step;
        float u1 = u0 + step;
        float v1 = v0 + step;
        return new float[]{u0, v0, u1, v1};
    }

    private enum Face {
        TOP,
        BOTTOM,
        SIDE
    }
}
