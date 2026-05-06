package akincraft.world;

public final class Noise {
    private Noise() {}

    public static float noise(int x, int z, int seed) {
        int n = x + z * 57 + seed * 131;
        n = (n << 13) ^ n;
        int t = (n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff;
        return 1.0f - t / 1073741824f;
    }

    public static float smoothNoise(int x, int z, int seed) {
        float corners = (noise(x - 1, z - 1, seed) + noise(x + 1, z - 1, seed) + noise(x - 1, z + 1, seed) + noise(x + 1, z + 1, seed)) / 16f;
        float sides = (noise(x - 1, z, seed) + noise(x + 1, z, seed) + noise(x, z - 1, seed) + noise(x, z + 1, seed)) / 8f;
        float center = noise(x, z, seed) / 4f;
        return corners + sides + center;
    }

    public static float interpolatedNoise(float x, float z, int seed) {
        int intX = (int) Math.floor(x);
        int intZ = (int) Math.floor(z);
        float fracX = x - intX;
        float fracZ = z - intZ;
        float v1 = smoothNoise(intX, intZ, seed);
        float v2 = smoothNoise(intX + 1, intZ, seed);
        float v3 = smoothNoise(intX, intZ + 1, seed);
        float v4 = smoothNoise(intX + 1, intZ + 1, seed);
        float i1 = interpolate(v1, v2, fracX);
        float i2 = interpolate(v3, v4, fracX);
        return interpolate(i1, i2, fracZ);
    }

    public static float perlin(float x, float z, int seed) {
        float total = 0;
        float persistence = 0.55f;
        float frequency = 1;
        float amplitude = 1;
        for (int i = 0; i < 4; i++) {
            total += interpolatedNoise(x * frequency, z * frequency, seed) * amplitude;
            amplitude *= persistence;
            frequency *= 2;
        }
        return total;
    }

    public static float caveNoise(float x, float y, float z, int seed) {
        float value = perlin(x * 0.25f, z * 0.25f, seed);
        float heightFactor = (float) ((Math.sin(x * 0.14 + seed * 1.3) + Math.cos(z * 0.12 + seed * 0.9)) * 0.3);
        return value + heightFactor - y * 0.01f;
    }

    private static float interpolate(float a, float b, float blend) {
        float theta = blend * (float) Math.PI;
        float f = (1f - (float) Math.cos(theta)) * 0.5f;
        return a * (1 - f) + b * f;
    }
}
