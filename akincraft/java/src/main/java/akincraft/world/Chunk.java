package akincraft.world;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Chunk {
    public static final int CHUNK_SIZE = 16;
    public static final int CHUNK_HEIGHT = 128;

    private final int chunkX;
    private final int chunkZ;
    private final BlockType[] blocks = new BlockType[CHUNK_SIZE * CHUNK_HEIGHT * CHUNK_SIZE];
    private final Mesh mesh;

    public Chunk(int chunkX, int chunkZ, int seed) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        generateTerrain(seed);
        this.mesh = MeshBuilder.buildMesh(this);
    }

    private void generateTerrain(int seed) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                float worldX = (chunkX * CHUNK_SIZE + x);
                float worldZ = (chunkZ * CHUNK_SIZE + z);
                float height = 40 + Noise.perlin(worldX * 0.08f, worldZ * 0.08f, seed) * 16;
                String biome = getBiome(worldX, worldZ, seed);
                for (int y = 0; y < CHUNK_HEIGHT; y++) {
                    float cave = Noise.caveNoise(worldX * 0.09f, y * 0.27f, worldZ * 0.09f, seed);
                    if (y > height || cave > 0.40f) {
                        setBlock(x, y, z, BlockType.AIR);
                    } else if (y == Math.floor(height)) {
                        setBlock(x, y, z, biome.equals("Desert") ? BlockType.SAND : BlockType.GRASS);
                    } else if (y > height - 4) {
                        setBlock(x, y, z, BlockType.DIRT);
                    } else {
                        setBlock(x, y, z, BlockType.STONE);
                    }
                }
                float treeSeed = Noise.noise((int) worldX, (int) worldZ, seed);
                if (biome.equals("Forest") && treeSeed > 0.62f) {
                    generateTree(x, (int) Math.floor(height) + 1, z);
                }
            }
        }
    }

    private String getBiome(float x, float z, int seed) {
        float noise = Noise.perlin(x * 0.02f, z * 0.02f, seed);
        if (noise < -0.2f) return "Desert";
        if (noise < 0.3f) return "Plains";
        return "Forest";
    }

    private void generateTree(int x, int y, int z) {
        for (int dy = 0; dy < 4; dy++) {
            if (y + dy < CHUNK_HEIGHT) {
                setBlock(x, y + dy, z, BlockType.OAK_LOG);
            }
        }
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                int ax = x + dx;
                int az = z + dz;
                int ay = y + 4;
                if (ax >= 0 && ax < CHUNK_SIZE && az >= 0 && az < CHUNK_SIZE && ay < CHUNK_HEIGHT) {
                    if (Math.abs(dx) + Math.abs(dz) < 4) {
                        setBlock(ax, ay, az, BlockType.OAK_LEAVES);
                    }
                }
            }
        }
    }

    public static String key(int x, int z) {
        return x + "," + z;
    }

    public BlockType getBlock(int x, int y, int z) {
        if (x < 0 || x >= CHUNK_SIZE || y < 0 || y >= CHUNK_HEIGHT || z < 0 || z >= CHUNK_SIZE) {
            return BlockType.AIR;
        }
        return blocks[y * CHUNK_SIZE * CHUNK_SIZE + z * CHUNK_SIZE + x];
    }

    public void setBlock(int x, int y, int z, BlockType type) {
        blocks[y * CHUNK_SIZE * CHUNK_SIZE + z * CHUNK_SIZE + x] = type;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public int getWorldX() {
        return chunkX * CHUNK_SIZE;
    }

    public int getWorldZ() {
        return chunkZ * CHUNK_SIZE;
    }
}
