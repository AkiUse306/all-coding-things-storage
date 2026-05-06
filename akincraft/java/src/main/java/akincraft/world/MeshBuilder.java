package akincraft.world;

import akincraft.render.TextureAtlasRegistry;

import java.util.ArrayList;
import java.util.List;

public final class MeshBuilder {
    private MeshBuilder() {}

    public static Mesh buildMesh(Chunk chunk) {
        List<Float> vertexList = new ArrayList<>();
        List<Integer> indexList = new ArrayList<>();

        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int y = 0; y < Chunk.CHUNK_HEIGHT; y++) {
                for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                    BlockType block = chunk.getBlock(x, y, z);
                    if (block == BlockType.AIR) continue;
                    addFaces(chunk, x, y, z, block, vertexList, indexList);
                }
            }
        }

        float[] vertices = new float[vertexList.size()];
        int[] indices = new int[indexList.size()];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = vertexList.get(i);
        }
        for (int i = 0; i < indices.length; i++) {
            indices[i] = indexList.get(i);
        }
        return new Mesh(vertices, indices);
    }

    private static void addFaces(Chunk chunk, int x, int y, int z, BlockType block, List<Float> vertices, List<Integer> indices) {
        float vx = chunk.getWorldX() + x;
        float vz = chunk.getWorldZ() + z;
        int startIndex = vertices.size() / 8;

        if (chunk.getBlock(x, y, z - 1).isTransparent()) {
            addFace(vertices, vx, y, vz, 0, 0, -1, block);
        }
        if (chunk.getBlock(x, y, z + 1).isTransparent()) {
            addFace(vertices, vx, y, vz, 0, 0, 1, block);
        }
        if (chunk.getBlock(x - 1, y, z).isTransparent()) {
            addFace(vertices, vx, y, vz, -1, 0, 0, block);
        }
        if (chunk.getBlock(x + 1, y, z).isTransparent()) {
            addFace(vertices, vx, y, vz, 1, 0, 0, block);
        }
        if (chunk.getBlock(x, y - 1, z).isTransparent()) {
            addFace(vertices, vx, y, vz, 0, -1, 0, block);
        }
        if (chunk.getBlock(x, y + 1, z).isTransparent()) {
            addFace(vertices, vx, y, vz, 0, 1, 0, block);
        }

        int quadCount = (vertices.size() / 8) - startIndex;
        for (int i = 0; i < quadCount; i += 4) {
            int base = startIndex + i;
            indices.add(base);
            indices.add(base + 1);
            indices.add(base + 2);
            indices.add(base);
            indices.add(base + 2);
            indices.add(base + 3);
        }
    }

    private static void addFace(List<Float> vertices, float x, int y, float z, int dx, int dy, int dz, BlockType block) {
        float[][] positions = getFacePositions(x, y, z, dx, dy, dz);
        float[] normal = new float[]{dx, dy, dz};
        float[] uv = TextureAtlasRegistry.getUv(block, dx, dy, dz);
        for (int i = 0; i < positions.length; i++) {
            float[] pos = positions[i];
            vertices.add(pos[0]);
            vertices.add(pos[1]);
            vertices.add(pos[2]);
            vertices.add(normal[0]);
            vertices.add(normal[1]);
            vertices.add(normal[2]);
            vertices.add(i == 0 || i == 3 ? uv[0] : uv[2]);
            vertices.add(i == 0 || i == 1 ? uv[1] : uv[3]);
        }
    }

    private static float[][] getFacePositions(float x, int y, float z, int dx, int dy, int dz) {
        float px = x;
        float py = y;
        float pz = z;
        if (dx != 0) {
            float fx = px + (dx > 0 ? 1 : 0);
            return new float[][]{
                    {fx, py, pz}, {fx, py, pz + 1}, {fx, py + 1, pz + 1}, {fx, py + 1, pz}
            };
        }
        if (dz != 0) {
            float fz = pz + (dz > 0 ? 1 : 0);
            return new float[][]{
                    {px, py, fz}, {px + 1, py, fz}, {px + 1, py + 1, fz}, {px, py + 1, fz}
            };
        }
        float fy = py + (dy > 0 ? 1 : 0);
        return new float[][]{
                {px, fy, pz}, {px + 1, fy, pz}, {px + 1, fy, pz + 1}, {px, fy, pz + 1}
        };
    }
}
