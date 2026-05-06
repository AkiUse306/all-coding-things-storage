# 🔧 Integrating Akincraft Systems - Step by Step

This guide shows how to implement each system in your game.

## 1️⃣ Enchantment System Integration

### In Your Main App Class:

```java
// Initialize enchantment system
EnchantmentSystem enchantmentSystem = new EnchantmentSystem();
PlayerStats player = new PlayerStats();

// Create and equip an enchanted sword
EnchantmentSystem.EnchantedItem sword = EnchantmentSystem.enchantItem(
    "Diamond Sword",
    EnchantmentSystem.Enchantment.SHARPNESS,
    3  // Level 3 (max)
);

player.equipMainHandWeapon(sword);

// Now damage is boosted!
float damage = player.getEffectiveDamage(10f);  // Returns ~17.5f with 75% boost
```

### Applying Enchantment Effects in Combat:

```java
// In your entity damage code
public void dealDamage(Entity attacker, Entity target, float baseDamage) {
    float effectiveDamage = baseDamage;
    
    // Apply attacker's weapon enchantments
    if (attacker.player != null) {
        effectiveDamage = attacker.player.getEffectiveDamage(baseDamage);
    }
    
    // Apply target's armor enchantments  
    if (target.player != null) {
        float reduction = target.player.getEffectiveDamageReduction();
        effectiveDamage *= reduction;
    }
    
    target.takeDamage(effectiveDamage);
}
```

---

## 2️⃣ Quest System Integration

### Initialize and Track Quests:

```java
// In your App.java
QuestSystem questSystem = new QuestSystem();

// During gameplay, update quest progress
void onBlockBroken(BlockType type) {
    if (type == BlockType.OAK_LOG) {
        questSystem.updateQuestProgress("collect_wood", 1);
    }
}

void onMobDefeated() {
    questSystem.updateQuestProgress("defeat_mobs", 1);
}

// Display progress
void renderUI() {
    List<QuestSystem.Quest> active = questSystem.getActiveQuests();
    for (QuestSystem.Quest q : active) {
        drawQuestProgress(q.name, q.getProgress());
    }
}
```

### Achievement Unlock Conditions:

```java
// Check achievements in your game loop
void checkAchievements(Inventory inventory) {
    // Check first block placed
    if (inventory.isBlockPlaced() && !alreadyUnlocked("first_block")) {
        questSystem.unlockAchievement("first_block");
    }
    
    // Check wood collected
    int woodCount = inventory.count("OAK_LOG") + 
                    inventory.count("BIRCH_LOG") + 
                    inventory.count("SPRUCE_LOG");
    if (woodCount >= 64) {
        questSystem.unlockAchievement("wood_collector");
    }
    
    // Check player level
    if (questSystem.getLevel() >= 5) {
        questSystem.unlockAchievement("leveled_up");
    }
}
```

---

## 3️⃣ Potion System Integration

### Brewing Potions:

```java
// In your crafting/alchemy bench code
class AlchemyBench {
    public void brewPotion(String base, String modifier, int level) {
        Potion potion = PotionSystem.brewPotion(base, modifier, level);
        
        if (potion != null) {
            addToInventory(potion);
            System.out.println("Brewed: " + potion);
        }
    }
}
```

### Applying Potion Effects:

```java
// When player drinks potion
void drinkPotion(Potion potion, PlayerStats player) {
    PotionSystem.applyPotionEffect(potion, player);
    
    // Potion effects are automatically applied via:
    // - player.enableNightVision()
    // - player.addSpeedBoost()
    // - player.addDamageBoost()
    // - etc.
}

// In your game loop, apply active effects each frame
void updatePlayerEffects(PlayerStats player, float deltaTime) {
    if (player.hasNightVision()) {
        renderNightVisionEffect();
    }
    
    if (player.isRegenerating()) {
        player.heal(0.1f * deltaTime);
    }
}
```

---

## 4️⃣ Dungeon System Integration

### Finding Dungeons:

```java
// In your WorldManager
DungeonSystem dungeonSystem = new DungeonSystem(worldManager);

// Find dungeons near player
void updateNearbyDungeons(int playerX, int playerZ) {
    List<DungeonSystem.Dungeon> nearby = 
        dungeonSystem.getNearbyDungeons(playerX, playerZ, 500);
    
    for (DungeonSystem.Dungeon d : nearby) {
        if (!d.discovered) {
            displayDiscoveryAlert(d);  // "You found a " + d.type.name
            dungeonSystem.discoverDungeon(d);
        }
    }
}

// Show dungeon information
void showDungeonInfo(DungeonSystem.Dungeon dungeon) {
    int playerLevel = questSystem.getLevel();
    int recommendedLevel = dungeon.getRecommendedPlayerLevel();
    
    System.out.println("=== " + dungeon.type.name + " ===");
    System.out.println("Difficulty: " + dungeon.type.difficulty);
    System.out.println("Recommended Level: " + recommendedLevel);
    System.out.println("Your Level: " + playerLevel);
    System.out.println("Can Enter: " + (playerLevel >= recommendedLevel - 5));
    System.out.println("\nChallenges:");
    for (DungeonSystem.DungeonChallenge c : dungeon.challenges) {
        System.out.println("  - " + c.name);
    }
    System.out.println("\nTreasures:");
    for (String t : dungeon.treasures) {
        System.out.println("  - " + t);
    }
}
```

---

## 5️⃣ Advanced Crafting Integration

### Display Available Recipes:

```java
// Show crafting recipes based on player level
AdvancedCrafting crafting = new AdvancedCrafting();
int playerLevel = questSystem.getLevel();

List<AdvancedCrafting.AdvancedRecipe> available = 
    crafting.getRecipesByLevel(playerLevel);

System.out.println("=== AVAILABLE RECIPES ===");
for (AdvancedCrafting.AdvancedRecipe r : available) {
    System.out.println(r);
}
```

### Craft Items:

```java
// In your crafting table UI
void attemptCraft(String recipeName, 
                 Map<String, Integer> inventory, 
                 int playerLevel) {
    AdvancedCrafting crafting = new AdvancedCrafting();
    AdvancedCrafting.AdvancedRecipe recipe = crafting.getRecipe(recipeName);
    
    if (recipe == null) {
        System.out.println("Recipe not found");
        return;
    }
    
    if (!crafting.canCraft(recipe, inventory, playerLevel)) {
        System.out.println("Cannot craft - missing ingredients or low level");
        return;
    }
    
    crafting.craft(recipe, inventory);
    questSystem.updateQuestProgress("crafting_quest", 1);
}
```

---

## 6️⃣ Unique Mob System Integration

### Spawn Mobs in Your World:

```java
// In your EntityManager or MobSpawner
void spawnUniqueEntities() {
    // Spawn corrupted spirits in dark caves
    if (getCurrentBiome() == BiomeType.CAVES && getCurrentLight() < 5) {
        createEntity(new EntityTypes.CorruptedSpirit(x, y, z));
    }
    
    // Spawn crystal golems in crystal caves
    if (getCurrentBiome() == BiomeType.CRYSTAL_CAVE) {
        if (Math.random() < 0.02f) {  // 2% chance per block
            createEntity(new EntityTypes.CrystalGolem(x, y, z));
        }
    }
    
    // Spawn echo knights in void temples
    if (getCurrentBiome() == BiomeType.VOID_TEMPLE && 
        getCurrentLight() < 8) {
        if (Math.random() < 0.01f) {  // 1% chance per block
            createEntity(new EntityTypes.EchoKnight(x, y, z));
        }
    }
}
```

### Handle Mob Drops:

```java
// When a unique mob dies
void onUniqueEntityDeath(Entity mob) {
    if (mob instanceof EntityTypes.CorruptedSpirit) {
        // Drop essence of corruption
        for (int i = 0; i < 1 + random.nextInt(2); i++) {
            dropItem("essence_of_corruption", playerPos);
        }
    }
    
    if (mob instanceof EntityTypes.CrystalGolem) {
        // Drop crystal shards
        for (int i = 0; i < 3 + random.nextInt(3); i++) {
            dropItem("crystal_shard", playerPos);
        }
    }
    
    if (mob instanceof EntityTypes.EchoKnight) {
        // Drop void shards
        dropItem("void_shard", playerPos);
        dropItem("echo_essence", playerPos);
    }
}
```

---

## 🔗 Complete Integration Example

```java
// In your App.java render/update loop
public void updateGameSystems(float deltaTime) {
    // Update base player stats (health, hunger)
    player.update(deltaTime);
    
    // Update quests and achievements
    questSystem.update();
    
    // Update potion effects
    if (player.hasNightVision()) {
        renderUI.applyNightVisionShader();
    }
    
    // Check for nearby dungeons
    updateNearbyDungeons(player.getX(), player.getZ());
    
    // Detect mob interactions
    for (Entity mob : world.getEntitiesNear(player, 20)) {
        if (mob instanceof EntityTypes.CrystalGolem) {
            // Crystal Golem regenerates
        }
    }
    
    // Display player HUD
    renderPlayerHUD(player);
}

public void renderPlayerHUD(PlayerStats player) {
    // Health bar
    drawHealthBar(player.getHealth(), player.getMaxHealth());
    
    // Hunger bar
    drawHungerBar(player.getHunger(), player.getMaxHunger());
    
    // Active quests
    for (QuestSystem.Quest q : questSystem.getActiveQuests()) {
        drawQuestProgress(q);
    }
    
    // Player level
    drawText("Level: " + questSystem.getLevel(), 10, 30);
    
    // Active effects
    if (player.hasNightVision()) {
        drawText("👁️ Night Vision", 10, 50);
    }
    if (player.isRegenerating()) {
        drawText("🩹 Regenerating", 10, 70);
    }
}
```

---

## ✅ Testing Checklist

- [ ] Enchant a weapon and verify damage increase
- [ ] Complete a quest and verify XP gain  
- [ ] Brew and drink a potion
- [ ] Unlock an achievement
- [ ] Find a dungeon
- [ ] Craft an advanced recipe
- [ ] Encounter a unique mob
- [ ] Check player stats display all bonuses

---

Now implement these systems in your game!
