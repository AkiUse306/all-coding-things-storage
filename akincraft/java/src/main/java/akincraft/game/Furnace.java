package akincraft.game;

/**
 * Furnace/smelting functionality
 */
public class Furnace {
    private String inputItem;
    private String outputItem;
    private float fuelTime;
    private float maxFuelTime;
    private float smeltTime;
    private float maxSmeltTime;
    private int outputStacks = 0;
    
    private static final float DEFAULT_SMELT_TIME = 10.0f; // 10 seconds
    
    // Smelting recipes
    private static final java.util.Map<String, String> SMELT_RECIPES = new java.util.HashMap<>();
    static {
        SMELT_RECIPES.put("iron_ore", "iron_ingot");
        SMELT_RECIPES.put("gold_ore", "gold_ingot");
        SMELT_RECIPES.put("oak_log", "charcoal");
        SMELT_RECIPES.put("sand", "glass");
        SMELT_RECIPES.put("stone", "smooth_stone");
        SMELT_RECIPES.put("cobblestone", "stone");
    }
    
    // Fuel values (in seconds)
    private static final java.util.Map<String, Float> FUEL_VALUES = new java.util.HashMap<>();
    static {
        FUEL_VALUES.put("oak_log", 15.0f);
        FUEL_VALUES.put("birch_log", 15.0f);
        FUEL_VALUES.put("spruce_log", 15.0f);
        FUEL_VALUES.put("oak_planks", 7.5f);
        FUEL_VALUES.put("coal", 80.0f);
        FUEL_VALUES.put("charcoal", 80.0f);
    }
    
    public void addFuel(String fuelType) {
        if (FUEL_VALUES.containsKey(fuelType)) {
            Float fuelValue = FUEL_VALUES.get(fuelType);
            maxFuelTime = fuelValue;
            fuelTime = fuelValue;
        }
    }
    
    public void addInput(String itemType) {
        if (SMELT_RECIPES.containsKey(itemType)) {
            this.inputItem = itemType;
            this.outputItem = SMELT_RECIPES.get(itemType);
            this.smeltTime = 0.0f;
        }
    }
    
    public void update(float deltaTime) {
        if (fuelTime <= 0 || inputItem == null) {
            smeltTime = 0.0f;
            return;
        }
        
        fuelTime -= deltaTime;
        smeltTime += deltaTime;
        
        if (smeltTime >= DEFAULT_SMELT_TIME) {
            smeltTime -= DEFAULT_SMELT_TIME;
            outputStacks++;
            // One item smelted
        }
    }
    
    public String getOutput() {
        if (outputStacks > 0) {
            outputStacks--;
            return outputItem;
        }
        return null;
    }
    
    public float getFuelProgress() {
        return fuelTime / maxFuelTime;
    }
    
    public float getSmeltProgress() {
        return smeltTime / DEFAULT_SMELT_TIME;
    }
    
    public boolean hasFuel() {
        return fuelTime > 0;
    }
}
