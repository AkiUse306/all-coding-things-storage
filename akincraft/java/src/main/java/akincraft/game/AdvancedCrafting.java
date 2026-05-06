package akincraft.game;

import java.util.*;

/**
 * Original Akincraft Advanced Crafting System
 * Multi-stage recipes with special items and effects
 */
public class AdvancedCrafting {
    
    public enum RecipeType {
        SHAPED("Shaped"),          // 3x3 grid pattern
        SHAPELESS("Shapeless"),    // Any arrangement
        SMELTING("Smelting"),      // Furnace recipes
        ALCHEMY("Alchemy"),        // Potion brewing
        ENCHANTING("Enchanting");  // Enchantment tables
        
        public final String name;
        RecipeType(String name) { this.name = name; }
    }
    
    public static class AdvancedRecipe {
        public String name;
        public RecipeType type;
        public List<String> inputs;
        public String output;
        public int outputCount;
        public int craftTime;  // Ticks
        public int level;      // Player level required
        
        public AdvancedRecipe(String name, RecipeType type, List<String> inputs, 
                            String output, int outputCount, int craftTime, int level) {
            this.name = name;
            this.type = type;
            this.inputs = new ArrayList<>(inputs);
            this.output = output;
            this.outputCount = outputCount;
            this.craftTime = craftTime;
            this.level = level;
        }
        
        @Override
        public String toString() {
            return String.format("[%s] %s (Level %d, %d ticks)", type.name, name, level, craftTime);
        }
    }
    
    private List<AdvancedRecipe> recipes;
    
    public AdvancedCrafting() {
        this.recipes = new ArrayList<>();
        initializeRecipes();
    }
    
    private void initializeRecipes() {
        // Enchanted Tool Recipes
        recipes.add(new AdvancedRecipe(
            "Void Pickaxe",
            RecipeType.SHAPED,
            Arrays.asList("void_shard", "void_shard", "void_shard", "stick", "stick"),
            "void_pickaxe",
            1,
            200,
            10
        ));
        
        recipes.add(new AdvancedRecipe(
            "Crystal Armor Set",
            RecipeType.SHAPED,
            Arrays.asList("crystal_shard", "crystal_shard", "crystal_shard", "crystal_shard", 
                         "crystal_shard", "essence_of_power"),
            "crystal_armor",
            1,
            300,
            15
        ));
        
        // Alchemy Recipes
        recipes.add(new AdvancedRecipe(
            "Essence Vial",
            RecipeType.ALCHEMY,
            Arrays.asList("essence_of_corruption", "essence_of_power", "void_fluid"),
            "essence_vial",
            1,
            150,
            8
        ));
        
        recipes.add(new AdvancedRecipe(
            "Potion of Ultimate Power",
            RecipeType.ALCHEMY,
            Arrays.asList("essence_vial", "crystal_core", "void_essence", "nether_wart"),
            "potion_of_ultimate_power",
            3,
            250,
            20
        ));
        
        // Enchanted Storage
        recipes.add(new AdvancedRecipe(
            "Void Chest",
            RecipeType.SHAPED,
            Arrays.asList("void_shard", "void_shard", "void_shard", "void_shard", "diamond_block", 
                         "void_shard", "void_shard", "void_shard", "void_shard"),
            "void_chest",
            1,
            400,
            25
        ));
        
        // Weapon Recipes
        recipes.add(new AdvancedRecipe(
            "Blade of Echoes",
            RecipeType.SHAPED,
            Arrays.asList("echo_essence", "echo_essence", "obsidian", "void_shard", "ancient_wood"),
            "blade_of_echoes",
            1,
            180,
            12
        ));
        
        // Defense Items
        recipes.add(new AdvancedRecipe(
            "Shield of Purification",
            RecipeType.SHAPED,
            Arrays.asList("artifact_of_purification", "diamond", "crystal_shard", "gold_block"),
            "shield_of_purification",
            1,
            200,
            14
        ));
    }
    
    public AdvancedRecipe getRecipe(String name) {
        for (AdvancedRecipe r : recipes) {
            if (r.name.equals(name)) return r;
        }
        return null;
    }
    
    public List<AdvancedRecipe> getRecipesByType(RecipeType type) {
        List<AdvancedRecipe> matching = new ArrayList<>();
        for (AdvancedRecipe r : recipes) {
            if (r.type == type) matching.add(r);
        }
        return matching;
    }
    
    public List<AdvancedRecipe> getRecipesByLevel(int playerLevel) {
        List<AdvancedRecipe> available = new ArrayList<>();
        for (AdvancedRecipe r : recipes) {
            if (r.level <= playerLevel) available.add(r);
        }
        return available;
    }
    
    public boolean canCraft(AdvancedRecipe recipe, Map<String, Integer> inventory, int playerLevel) {
        // Check player level
        if (playerLevel < recipe.level) return false;
        
        // Check all required inputs
        for (String ingredient : recipe.inputs) {
            if (!inventory.containsKey(ingredient) || inventory.get(ingredient) <= 0) {
                return false;
            }
        }
        
        return true;
    }
    
    public void craft(AdvancedRecipe recipe, Map<String, Integer> inventory) {
        // Remove inputs
        for (String ingredient : recipe.inputs) {
            inventory.put(ingredient, inventory.getOrDefault(ingredient, 0) - 1);
        }
        
        // Add output
        inventory.put(recipe.output, inventory.getOrDefault(recipe.output, 0) + recipe.outputCount);
        
        System.out.println("✓ Crafted: " + recipe.name + " x" + recipe.outputCount);
    }
}
