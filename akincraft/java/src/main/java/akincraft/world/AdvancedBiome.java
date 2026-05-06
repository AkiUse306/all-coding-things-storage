package akincraft.world;

/**
 * Enhanced biome system with more variety and features
 */
public class AdvancedBiome {
    
    public enum BiomeType {
        // Temperate
        PLAINS("Plains", 0.8f, 0.4f, new String[]{"GRASS", "FLOWERS", "OAK_TREE"}),
        FOREST("Forest", 0.7f, 0.8f, new String[]{"OAK_TREE", "BIRCH_TREE", "SPRUCE_TREE"}),
        DENSE_FOREST("Dense Forest", 0.7f, 0.9f, new String[]{"SPRUCE_TREE", "DARK_OAK_TREE"}),
        
        // Desert
        DESERT("Desert", 1.2f, 0.0f, new String[]{"SAND", "DEAD_BUSH", "CACTUS"}),
        MESA("Mesa", 1.5f, 0.0f, new String[]{"COLORED_SAND", "ORANGE_SAND"}),
        
        // Mountain
        MOUNTAINS("Mountains", 0.5f, 0.5f, new String[]{"STONE", "SNOW"}),
        SNOWY_MOUNTAINS("Snowy Mountains", 0.0f, 0.8f, new String[]{"STONE", "SNOW", "SPRUCE_TREE"}),
        
        // Water
        OCEAN("Ocean", 0.8f, 0.9f, new String[]{"WATER", "SAND", "GRAVEL"}),
        COLD_OCEAN("Cold Ocean", 0.0f, 0.85f, new String[]{"WATER", "PACKED_ICE"}),
        
        // Nether-like
        VOLCANIC("Volcanic", 2.0f, 0.0f, new String[]{"LAVA", "OBSIDIAN", "DARK_STONE"}),
        
        // Special
        MUSHROOM("Mushroom Fields", 0.9f, 0.9f, new String[]{"MYCELIUM", "MUSHROOM"}),
        SAVANNA("Savanna", 1.5f, 0.0f, new String[]{"GRASS", "ACACIA_TREE"});
        
        private final String displayName;
        private final float temperature;  // 0.0 = cold, 1.0 = normal, 2.0 = hot
        private final float humidity;     // 0.0 = dry,  1.0 = normal
        private final String[] features;
        
        BiomeType(String displayName, float temperature, float humidity, String[] features) {
            this.displayName = displayName;
            this.temperature = temperature;
            this.humidity = humidity;
            this.features = features;
        }
        
        public String getDisplayName() { return displayName; }
        public float getTemperature() { return temperature; }
        public float getHumidity() { return humidity; }
        public String[] getFeatures() { return features; }
    }
    
    private BiomeType biomeType;
    private int precipitation; // 0-100, affects rainfall/snow
    private float visibility;  // Fog distance
    
    public AdvancedBiome(BiomeType type) {
        this.biomeType = type;
        this.precipitation = (int)(type.humidity * 100);
        this.visibility = 128.0f; // Default
    }
    
    /**
     * Get appropriate sky color for biome
     */
    public int[] getSkyColor() {
        float temp = biomeType.getTemperature();
        
        if (temp < 0.15) {
            return new int[]{135, 206, 250}; // Snowy blue
        } else if (temp < 0.5) {
            return new int[]{135, 206, 235}; // Light blue
        } else if (temp < 1.5) {
            return new int[]{135, 206, 235}; // Normal blue
        } else {
            return new int[]{180, 200, 100}; // Hotter, more yellowish
        }
    }
    
    /**
     * Get appropriate fog color
     */
    public int[] getFogColor() {
        float temp = biomeType.getTemperature();
        
        if (temp < 0.15) {
            return new int[]{180, 180, 200}; // Snowy fog
        } else if (temp > 1.5) {
            return new int[]{220, 120, 80};  // Hot fog
        }
        return new int[]{200, 200, 200};
    }
    
    /**
     * Should rain in this biome
     */
    public boolean shouldRain() {
        return biomeType.getTemperature() > 0.15 && biomeType.getTemperature() < 1.5;
    }
    
    /**
     * Should snow in this biome
     */
    public boolean shouldSnow() {
        return biomeType.getTemperature() < 0.15;
    }
    
    /**
     * Get ambient lighting for time of day
     */
    public float getAmbientLight(float dayProgress) {
        // dayProgress: 0.0 = midnight, 0.25 = sunrise, 0.5 = noon, 0.75 = sunset, 1.0 = midnight
        if (dayProgress < 0.25) {
            return 0.3f + dayProgress * 2.8f; // 0.3 to 1.0 during sunrise
        } else if (dayProgress < 0.75) {
            return 1.0f; // Full brightness during day
        } else {
            return 1.0f - (dayProgress - 0.75f) * 2.8f; // 1.0 to 0.3 during sunset
        }
    }
    
    /**
     * Get ore frequency multiplier for this biome
     */
    public float getOreFrequency(String oreType) {
        if (biomeType == BiomeType.VOLCANIC) return 1.5f; // More ores in volcanic
        if (biomeType == BiomeType.MOUNTAINS) return 1.2f;
        if (biomeType == BiomeType.DESERT) return 0.8f;  // Fewer ores in desert
        return 1.0f; // Default
    }
    
    /**
     * Get tree generation density
     */
    public float getTreeDensity() {
        return biomeType.getHumidity();
    }
    
    public BiomeType getBiomeType() {
        return biomeType;
    }
}
