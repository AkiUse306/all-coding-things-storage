package akincraft.game;

import akincraft.entity.EntityManager;
import akincraft.world.DungeonSystem;

/**
 * Integrates all original Akincraft systems together
 */
public class GameSystems {
    
    private EnchantmentSystem enchantmentSystem;
    private QuestSystem questSystem;
    private PotionSystem potionSystem;
    private AdvancedCrafting craftingSystem;
    private DungeonSystem dungeonSystem;
    private EntityManager entityManager;
    private PlayerStats playerStats;
    
    public GameSystems(EntityManager entityManager, PlayerStats playerStats, DungeonSystem dungeonSystem) {
        this.entityManager = entityManager;
        this.playerStats = playerStats;
        this.dungeonSystem = dungeonSystem;
        
        // Initialize all systems
        this.enchantmentSystem = new EnchantmentSystem();
        this.questSystem = new QuestSystem();
        this.potionSystem = new PotionSystem();
        this.craftingSystem = new AdvancedCrafting();
        
        System.out.println("✓ All Akincraft systems initialized!");
    }
    
    /**
     * Update all systems each game tick
     */
    public void update(float deltaTime) {
        // Update active potions
        updatePotionEffects(deltaTime);
        
        // Check quest progress
        updateQuestProgress();
        
        // Check achievement conditions
        updateAchievements();
    }
    
    private void updatePotionEffects(float deltaTime) {
        // Apply ongoing potion effects
        // This would integrate with player stats
    }
    
    private void updateQuestProgress() {
        // Track player actions for quests
        // - Blocks broken
        // - Mobs killed  
        // - Areas explored
    }
    
    private void updateAchievements() {
        // Check if conditions for achievements are met
        // - Collected items
        // - Crafted items
        // - Defeated mobs
        // - Reached locations
    }
    
    // Getters for accessing systems
    public EnchantmentSystem getEnchantmentSystem() { return enchantmentSystem; }
    public QuestSystem getQuestSystem() { return questSystem; }
    public PotionSystem getPotionSystem() { return potionSystem; }
    public AdvancedCrafting getCraftingSystem() { return craftingSystem; }
    public DungeonSystem getDungeonSystem() { return dungeonSystem; }
    
    /**
     * Display player progress status
     */
    public void displayPlayerStatus() {
        System.out.println("\n=== PLAYER STATUS ===");
        System.out.println("Level: " + questSystem.getLevel());
        System.out.println("XP: " + questSystem.getTotalXP());
        System.out.println("\n=== ACTIVE QUESTS ===");
        for (QuestSystem.Quest q : questSystem.getActiveQuests()) {
            System.out.println(q);
        }
        System.out.println("\n=== ACHIEVEMENTS ===");
        int unlocked = questSystem.getUnlockedAchievements().size();
        System.out.println("Unlocked: " + unlocked);
    }
}
