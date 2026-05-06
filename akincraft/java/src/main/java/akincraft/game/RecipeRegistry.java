package akincraft.game;

import java.util.*;

/**
 * Expanded crafting recipe system with multiple recipe types
 */
public class RecipeRegistry {
    private final Map<String, Recipe> recipes = new HashMap<>();
    
    public RecipeRegistry() {
        registerAllRecipes();
    }
    
    private void registerAllRecipes() {
        // Tools
        recipes.put("wooden_pickaxe", new Recipe("Wooden Pickaxe",
            new String[]{"WWW", " S ", " S "},
            new String[]{"W:oak_planks", "S:stick"}));
        
        recipes.put("stone_pickaxe", new Recipe("Stone Pickaxe",
            new String[]{"SSS", " T ", " T "},
            new String[]{"S:stone", "T:stick"}));
        
        recipes.put("iron_pickaxe", new Recipe("Iron Pickaxe",
            new String[]{"III", " T ", " T "},
            new String[]{"I:iron_ingot", "T:stick"}));
        
        recipes.put("diamond_pickaxe", new Recipe("Diamond Pickaxe",
            new String[]{"DDD", " T ", " T "},
            new String[]{"D:diamond", "T:stick"}));
        
        // Swords
        recipes.put("wooden_sword", new Recipe("Wooden Sword",
            new String[]{" W ", " W ", " S "},
            new String[]{"W:oak_planks", "S:stick"}));
        
        recipes.put("stone_sword", new Recipe("Stone Sword",
            new String[]{" S ", " S ", " T "},
            new String[]{"S:stone", "T:stick"}));
        
        recipes.put("iron_sword", new Recipe("Iron Sword",
            new String[]{" I ", " I ", " T "},
            new String[]{"I:iron_ingot", "T:stick"}));
        
        recipes.put("diamond_sword", new Recipe("Diamond Sword",
            new String[]{" D ", " D ", " T "},
            new String[]{"D:diamond", "T:stick"}));
        
        // Basic blocks
        recipes.put("oak_planks", new Recipe("Oak Planks (4)",
            new String[]{"W"},
            new String[]{"W:oak_log"}));
        
        recipes.put("stick", new Recipe("Sticks (4)",
            new String[]{"W", "W"},
            new String[]{"W:oak_planks"}));
        
        recipes.put("crafting_table", new Recipe("Crafting Table",
            new String[]{"WW", "WW"},
            new String[]{"W:oak_planks"}));
        
        recipes.put("furnace", new Recipe("Furnace",
            new String[]{"CCC", "C C", "CCC"},
            new String[]{"C:stone"}));
        
        recipes.put("chest", new Recipe("Chest",
            new String[]{"WCW", "W W", "WCW"},
            new String[]{"W:oak_planks", "C:oak_log"}));
        
        // Food
        recipes.put("bread", new Recipe("Bread",
            new String[]{"WWW"},
            new String[]{"W:wheat"}));
    }
    
    public Recipe getRecipe(String name) {
        return recipes.get(name);
    }
    
    public Collection<Recipe> getAllRecipes() {
        return recipes.values();
    }
    
    /**
     * Simple recipe class
     */
    public static class Recipe {
        private final String result;
        private final String[] pattern;
        private final String[] ingredients;
        
        public Recipe(String result, String[] pattern, String[] ingredients) {
            this.result = result;
            this.pattern = pattern;
            this.ingredients = ingredients;
        }
        
        public String getResult() { return result; }
        public String[] getPattern() { return pattern; }
        public String[] getIngredients() { return ingredients; }
    }
}
