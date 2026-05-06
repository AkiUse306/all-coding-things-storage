package akincraft.world;

/**
 * Dimension types - Overworld, Nether, End
 */
public enum Dimension {
    OVERWORLD("Overworld", 0, true, 0),
    NETHER("Nether", 1, false, -0.5f),
    END("The End", 2, false, -1.0f);
    
    private final String displayName;
    private final int id;
    private final boolean hasWeather;
    private final float baseTemperature;
    
    Dimension(String displayName, int id, boolean hasWeather, float baseTemperature) {
        this.displayName = displayName;
        this.id = id;
        this.hasWeather = hasWeather;
        this.baseTemperature = baseTemperature;
    }
    
    public String getDisplayName() { return displayName; }
    public int getId() { return id; }
    public boolean hasWeather() { return hasWeather; }
    public float getBaseTemperature() { return baseTemperature; }
    
    public static Dimension fromId(int id) {
        for (Dimension d : values()) {
            if (d.id == id) return d;
        }
        return OVERWORLD;
    }
}
