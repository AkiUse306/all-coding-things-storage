package akincraft.render;

import akincraft.world.BlockType;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.util.EnumMap;

import static org.lwjgl.opengl.GL11.*;

public class TextureAtlas {
    private static final int REGION_SIZE = 16;
    private static final int GRID_SIZE = 8;
    private static final int ATLAS_SIZE = REGION_SIZE * GRID_SIZE;

    private static TextureAtlas instance;

    private final int textureId;
    private final EnumMap<BlockType, float[]> topUv = new EnumMap<>(BlockType.class);
    private final EnumMap<BlockType, float[]> bottomUv = new EnumMap<>(BlockType.class);
    private final EnumMap<BlockType, float[]> sideUv = new EnumMap<>(BlockType.class);

    public TextureAtlas() {
        instance = this;
        textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        ByteBuffer pixelData = BufferUtils.createByteBuffer(ATLAS_SIZE * ATLAS_SIZE * 4);
        fillAtlas(pixelData);
        pixelData.flip();
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, ATLAS_SIZE, ATLAS_SIZE, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelData);
        glBindTexture(GL_TEXTURE_2D, 0);
        setupUvMapping();
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public static float[] getUv(BlockType block, Face face) {
        if (instance == null) {
            throw new IllegalStateException("TextureAtlas has not been initialized");
        }
        return instance.getUvInternal(block, face);
    }

    private float[] getUvInternal(BlockType block, Face face) {
        return switch (face) {
            case TOP -> topUv.getOrDefault(block, topUv.get(BlockType.DIRT));
            case BOTTOM -> bottomUv.getOrDefault(block, bottomUv.get(BlockType.DIRT));
            case SIDE -> sideUv.getOrDefault(block, sideUv.get(BlockType.DIRT));
        };
    }

    private void fillAtlas(ByteBuffer buffer) {
        for (int y = 0; y < ATLAS_SIZE; y++) {
            for (int x = 0; x < ATLAS_SIZE; x++) {
                writePixel(buffer, 0, 0, 0, 255);
            }
        }

        setRegion(buffer, 0, 0, 114, 186, 55);    // grass top
        setRegion(buffer, 1, 0, 160, 143,  88);    // grass side
        setRegion(buffer, 2, 0, 135, 106,  63);    // dirt
        setRegion(buffer, 3, 0, 125, 125, 125);    // stone
        setRegion(buffer, 4, 0, 236, 216,  67);    // sand
        setRegion(buffer, 5, 0,  59, 122, 233);    // water
        setRegion(buffer, 6, 0, 106,  63,  38);    // oak log side
        setRegion(buffer, 7, 0, 168, 129,  85);    // oak log top

        setRegion(buffer, 0, 1,  88, 192,  68);    // leaves
        setRegion(buffer, 1, 1,  61,  61,  61);    // coal ore
        setRegion(buffer, 2, 1, 176, 156,  97);    // iron ore
        setRegion(buffer, 3, 1, 222, 175,  72);    // gold ore
        setRegion(buffer, 4, 1, 104, 226, 239);    // diamond ore
        setRegion(buffer, 5, 1, 141, 141, 141);    // gravel
        setRegion(buffer, 6, 1,  99,  99,  99);    // cobblestone
        setRegion(buffer, 7, 1,  84,  84,  84);    // smooth stone
    }

    private void setupUvMapping() {
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

        // Default fallback
        topUv.put(BlockType.DIRT, getUvFromGrid(2, 0));
        bottomUv.put(BlockType.DIRT, getUvFromGrid(2, 0));
        sideUv.put(BlockType.DIRT, getUvFromGrid(2, 0));
    }

    private void setUv(BlockType block, int topX, int topY, int sideX, int sideY, int bottomX, int bottomY) {
        topUv.put(block, getUvFromGrid(topX, topY));
        sideUv.put(block, getUvFromGrid(sideX, sideY));
        bottomUv.put(block, getUvFromGrid(bottomX, bottomY));
    }

    private void setUv(BlockType block, int gridX, int gridY) {
        float[] uv = getUvFromGrid(gridX, gridY);
        topUv.put(block, uv);
        sideUv.put(block, uv);
        bottomUv.put(block, uv);
    }

    private float[] getUvFromGrid(int gridX, int gridY) {
        float step = 1.0f / GRID_SIZE;
        float u0 = gridX * step;
        float v0 = gridY * step;
        float u1 = u0 + step;
        float v1 = v0 + step;
        return new float[]{u0, v0, u1, v1};
    }

    private void setRegion(ByteBuffer buffer, int cellX, int cellY, int r, int g, int b) {
        for (int pixelY = 0; pixelY < REGION_SIZE; pixelY++) {
            for (int pixelX = 0; pixelX < REGION_SIZE; pixelX++) {
                int x = cellX * REGION_SIZE + pixelX;
                int y = cellY * REGION_SIZE + pixelY;
                int index = (y * ATLAS_SIZE + x) * 4;
                buffer.put(index, (byte) r);
                buffer.put(index + 1, (byte) g);
                buffer.put(index + 2, (byte) b);
                buffer.put(index + 3, (byte) 255);
            }
        }
    }

    private void writePixel(ByteBuffer buffer, int r, int g, int b, int a) {
        buffer.put((byte) r).put((byte) g).put((byte) b).put((byte) a);
    }

    public enum Face {
        TOP,
        BOTTOM,
        SIDE
    }
}
