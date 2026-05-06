package akincraft.examples;

import akincraft.game.*;
import akincraft.entity.EntityManager;
import akincraft.entity.EntityTypes;
import akincraft.world.DungeonSystem;
import java.util.*;

/**
 * Complete example showing all Akincraft systems working together
 * Copy and adapt this to your game!
 */
public class AkinraftGameExample {
    
    private EnchantmentSystem enchantmentSystem;
    private QuestSystem questSystem;
    private PotionSystem potionSystem;
    private AdvancedCrafting craftingSystem;
    private DungeonSystem dungeonSystem;
    private PlayerStats player;
    private EntityManager entityManager;
    private Map<String, Integer> inventory;
    
    public AkinraftGameExample() {
        initialize();
    }
    
    private void initialize() {
        System.out.println("🎮 Initializing Akincraft Full System Demo...\n");
        
        enchantmentSystem = new EnchantmentSystem();
        questSystem = new QuestSystem();
        potionSystem = new PotionSystem();
        craftingSystem = new AdvancedCrafting();
        player = new PlayerStats();
        inventory = new HashMap<>();
        
        // Create dummy dungeon system (would use real WorldManager in actual game)
        // dungeonSystem = new DungeonSystem(worldManager);
        
        System.out.println("✓ All systems initialized!\n");
    }
    
    /**
     * Example 1: Equip enchanted weapon and deal damage
     */
    public void exampleEnchantment() {
        System.out.println("=== ENCHANTMENT EXAMPLE ===\n");
        
        // Create enchanted sword
        EnchantmentSystem.EnchantedItem sword = 
            EnchantmentSystem.enchantItem("Dragon Slayer", 
                                         EnchantmentSystem.Enchantment.SHARPNESS, 
                                         3);
        
        System.out.println("📍 Created: " + sword);
        
        player.equipMainHandWeapon(sword);
        
        // Calculate damage with enchantment
        float baseDamage = 10f;
        float enchantedDamage = player.getEffectiveDamage(baseDamage);
        
        System.out.println("Base Damage: " + baseDamage);
        System.out.println("Enchanted Damage: " + enchantedDamage);
        System.out.println("Bonus: +" + (enchantedDamage - baseDamage) + "\n");
    }
    
    /**
     * Example 2: Complete quests and gain XP
     */
    public void exampleQuests() {
        System.out.println("=== QUEST EXAMPLE ===\n");
        
        System.out.println("Active Quests:");
        for (QuestSystem.Quest q : questSystem.getActiveQuests()) {
            System.out.println("  " + q);
        }
        System.out.println();
        
        // Simulate collecting wood
        System.out.println("📍 Collecting wood...");
        for (int i = 0; i < 32; i++) {
            questSystem.updateQuestProgress("collect_wood", 1);
        }
        
        System.out.println("\nQuest Completed!");
        System.out.println("Total XP: " + questSystem.getTotalXP());
        System.out.println("Player Level: " + questSystem.getLevel() + "\n");
    }
    
    /**
     * Example 3: Brew and consume potions
     */
    public void examplePotions() {
        System.out.println("=== POTION EXAMPLE ===\n");
        
        // Brew potions
        System.out.println("📍 Brewing potions...");
        
        PotionSystem.Potion speedPotion = 
            PotionSystem.brewPotion("nether_wart", "glowstone", 2);
        System.out.println("Brewed: " + speedPotion);
        
        PotionSystem.Potion nightVisionPotion = 
            PotionSystem.brewPotion("nether_wart", "golden_carrot", 1);
        System.out.println("Brewed: " + nightVisionPotion);
        
        // Consume potions
        System.out.println("\n📍 Consuming potions...");
        PotionSystem.applyPotionEffect(speedPotion, player);
        PotionSystem.applyPotionEffect(nightVisionPotion, player);
        
        System.out.println("\nActive Effects:");
        System.out.println("  Speed Boost: " + player.getSpeedMultiplier() + "x");
        System.out.println("  Night Vision: " + player.hasNightVision() + "\n");
    }
    
    /**
     * Example 4: Advanced crafting
     */
    public void exampleCrafting() {
        System.out.println("=== ADVANCED CRAFTING EXAMPLE ===\n");
        
        // Populate inventory with ingredients
        System.out.println("📍 Adding crafting ingredients to inventory...");
        inventory.put("void_shard", 5);
        inventory.put("stick", 10);
        inventory.put("diamond_block", 2);
        
        System.out.println("Inventory: " + inventory);
        
        // Check available recipes
        System.out.println("\n📍 Checking recipes at current level...");
        int playerLevel = questSystem.getLevel();
        List<AdvancedCrafting.AdvancedRecipe> available = 
            craftingSystem.getRecipesByLevel(playerLevel);
        
        System.out.println("Available recipes at Level " + playerLevel + ":");
        for (AdvancedCrafting.AdvancedRecipe r : available) {
            System.out.println("  - " + r);
        }
        
        // Try to craft void pickaxe
        System.out.println("\n📍 Attempting to craft Void Pickaxe...");
        AdvancedCrafting.AdvancedRecipe voidPickaxe = 
            craftingSystem.getRecipe("Void Pickaxe");
        
        if (voidPickaxe != null) {
            System.out.println("Required Level: " + voidPickaxe.level);
            System.out.println("Your Level: " + playerLevel);
            
            if (playerLevel >= voidPickaxe.level) {
                System.out.println("✓ Level requirement met");
                // Would normally check ingredients too
            } else {
                System.out.println("✗ Level too low (need " + voidPickaxe.level + ")");
            }
        }
        System.out.println();
    }
    
    /**
     * Example 5: Achievements
     */
    public void exampleAchievements() {
        System.out.println("=== ACHIEVEMENTS EXAMPLE ===\n");
        
        System.out.println("📍 Unlocking achievements...");
        questSystem.unlockAchievement("first_block");
        questSystem.unlockAchievement("wood_collector");
        
        System.out.println("\nUnlocked Achievements:");
        for (QuestSystem.Achievement a : questSystem.getUnlockedAchievements()) {
            System.out.println("  🏆 " + a.title + " - " + a.description);
        }
        System.out.println("\nTotal XP from Achievements: " + questSystem.getTotalXP());
        System.out.println();
    }
    
    /**
     * Example 6: Unique mobs
     */
    public void exampleUniqueMobs() {
        System.out.println("=== UNIQUE MOBS EXAMPLE ===\n");
        
        System.out.println("📍 Creating unique mobs...\n");
        
        // Create instances of unique mobs
        EntityTypes.CorruptedSpirit spirit = 
            new EntityTypes.CorruptedSpirit(100, 50, 100);
        System.out.println("Created: Corrupted Spirit");
        System.out.println("  Health: " + spirit.getHealth());
        System.out.println("  Location: " + spirit.getX() + ", " + spirit.getY() + ", " + spirit.getZ());
        
        EntityTypes.CrystalGolem golem = 
            new EntityTypes.CrystalGolem(200, 50, 200);
        System.out.println("\nCreated: Crystal Golem");
        System.out.println("  Health: " + golem.getHealth());
        System.out.println("  Location: " + golem.getX() + ", " + golem.getY() + ", " + golem.getZ());
        
        EntityTypes.EchoKnight knight = 
            new EntityTypes.EchoKnight(300, 50, 300);
        System.out.println("\nCreated: Echo Knight");
        System.out.println("  Health: " + knight.getHealth());
        System.out.println("  Location: " + knight.getX() + ", " + knight.getY() + ", " + knight.getZ());
        
        System.out.println();
    }
    
    /**
     * Example 7: Player status display
     */
    public void displayPlayerStatus() {
        System.out.println("=== PLAYER STATUS ===\n");
        
        System.out.println("Basic Stats:");
        System.out.println("  Health: " + player.getHealth() + "/" + player.getMaxHealth());
        System.out.println("  Hunger: " + player.getHunger() + "/" + player.getMaxHunger());
        
        System.out.println("\nProgression:");
        System.out.println("  Level: " + questSystem.getLevel());
        System.out.println("  XP: " + questSystem.getTotalXP());
        System.out.println("  Active Quests: " + questSystem.getActiveQuests().size());
        System.out.println("  Achievements: " + questSystem.getUnlockedAchievements().size());
        
        System.out.println("\nEnhancements:");
        System.out.println("  Speed Multiplier: " + player.getSpeedMultiplier() + "x");
        System.out.println("  Damage Multiplier: " + player.getEffectiveDamage(10f) / 10f + "x");
        
        System.out.println("\nActive Effects:");
        System.out.println("  Night Vision: " + (player.hasNightVision() ? "✓" : "✗"));
        System.out.println("  Water Breathing: " + (player.hasWaterBreathing() ? "✓" : "✗"));
        System.out.println("  Regenerating: " + (player.isRegenerating() ? "✓" : "✗"));
        
        System.out.println();
    }
    
    /**
     * Run all examples
     */
    public void runAllExamples() {
        exampleEnchantment();
        exampleQuests();
        examplePotions();
        exampleCrafting();
        exampleAchievements();
        exampleUniqueMobs();
        displayPlayerStatus();
        
        System.out.println("\n✓ All examples completed!");
        System.out.println("Now integrate these systems into your App.java!");
    }
    
    /**
     * Main method - Run this to see the demo
     */
    public static void main(String[] args) {
        AkinraftGameExample demo = new AkinraftGameExample();
        demo.runAllExamples();
    }
}
