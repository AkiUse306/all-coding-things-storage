package akincraft.world;

/**
 * Biome types with distinct block and weather characteristics
 */
public enum BiomeType {
    PLAINS("Plains", new float[]{0.5f, 0.5f, 0}, new float[]{0.8f, 1.0f, 0.8f}),
    FOREST("Forest", new float[]{0.3f, 0.7f, 0}, new float[]{0.6f, 1.0f, 0.6f}),
    DESERT("Desert", new float[]{1.0f, 0.8f, 0.2f}, new float[]{1.5f, 0.0f, 0}),
    MOUNTAINS("Mountains", new float[]{0.6f, 0.6f, 0.6f}, new float[]{0.3f, 0.0f, 0}),
    OCEAN("Ocean", new float[]{0.2f, 0.4f, 0.8f}, new float[]{0.7f, 0.8f, 1.0f}),
    JUNGLE("Jungle", new float[]{0.1f, 0.8f, 0.1f}, new float[]{0.5f, 1.0f, 0.5f}),
    SNOWY("Snowy", new float[]{0.9f, 0.9f, 0.95f}, new float[]{-0.5f, 0.0f, 0}),
    SWAMP("Swamp", new float[]{0.4f, 0.6f, 0.4f}, new float[]{0.8f, 1.0f, 0.7f});
    
    private final String name;
    private final float[] color;
    private final float[] properties; // tree density, grass density, temperature
    
    BiomeType(String name, float[] color, float[] properties) {
        this.name = name;
        this.color = color;
        this.properties = properties;
    }
    
    public String getName() { return name; }
    public float[] getColor() { return color; }
    public float[] getProperties() { return properties; }
    
    public boolean canSnow() {
        return properties[2] < 0; // Temperature-based snow
    }
    
    public float getTreeDensity() {
        return properties[0];
    }
    
    public float getGrassDensity() {
        return properties[1];
    }
}
