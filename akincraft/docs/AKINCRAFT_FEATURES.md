# 🎮 Akincraft Original Features

Akincraft includes **unique, original gameplay systems** that make it special and different from other voxel games.

## 📊 Systems Overview

### 1. **Enchantment System** ✨
Give your tools and weapons magical powers!

**Available Enchantments:**
- **Efficiency** - Faster block breaking (25% per level)
- **Unbreaking** - Tools last longer
- **Fortune** - Get more resources
- **Sharpness** - Deal more damage
- **Knockback** - Push enemies back
- **Protection** - Reduce damage taken
- **Silk Touch** - Collect blocks as items
- **Aqua Affinity** - Mine underwater normally

```java
// Example usage
EnchantmentSystem.EnchantedItem sword = 
    EnchantmentSystem.enchantItem("Diamond Sword", 
                                 EnchantmentSystem.Enchantment.SHARPNESS, 
                                 3);
float damageBoost = sword.getBonus();  // 1.75x damage
```

---

### 2. **Quest & Achievement System** 🏆
Track progress and unlock achievements for long-term goals.

**Example Quests:**
- Collect 32 wood blocks → +250 XP
- Explore the desert biome → +300 XP
- Defeat 10 mobs → +400 XP
- Find a diamond ore → Achievement!

**Achievement Examples:**
- First Step - Place your first block
- Lumberjack - Collect 64 wood
- Diamond Seeker - Find a diamond
- Monster Hunter - Defeat 100 mobs
- Armored Up - Craft full armor set

```java
// Track progress
QuestSystem quests = new QuestSystem();
quests.updateQuestProgress("collect_wood", 10);
quests.unlockAchievement("wood_collector");
int level = quests.getLevel();  // Increases with XP
```

---

### 3. **Potion System** 🧪
Brew custom potions with unique effects!

**Beneficial Potions:**
- Speed - Move faster
- Strength - Deal more damage
- Resistance - Take less damage
- Regeneration - Heal yourself
- Night Vision - See in the dark
- Water Breathing - Breathe underwater

**Harmful Potions:**
- Poison - Take damage over time
- Weakness - Deal less damage
- Slowness - Move slower
- Blindness - Screen goes dark
- Nausea - Screen distortion

```java
// Brew and drink
Potion speedPotion = PotionSystem.brewPotion("nether_wart", "glowstone", 2);
PotionSystem.applyPotionEffect(speedPotion, player);
```

---

### 4. **Original Mob Types** 👹
Akincraft has unique creatures you won't find elsewhere:

#### **Corrupted Spirit**
- Phases through blocks every 3 seconds
- Emits purple corruption particles
- Drops: Essence of Corruption (crafting ingredient)
- Found in: Dark caves

#### **Crystal Golem**
- High health and durability
- Slowly regenerates health
- Can reflect projectiles
- Glowing crystalline appearance
- Drops: Crystal Shards (rare crafting material)
- Found in: Crystal caves

#### **Echo Knight**
- Teleports around the battlefield
- Creates echo duplicates when damaged
- High melee damage
- Disappears in reality rifts
- Drops: Void Shards, Echo Essence
- Found in: Void temples

---

### 5. **Dungeon System** 🏰
Procedurally-generated dungeons with unique challenges and loot!

**Dungeon Types:**

| Dungeon | Difficulty | Challenges | Treasures |
|---------|-----------|-----------|-----------|
| **Crystal Cave** | 20 | Avoid spikes, Defeat Crystal Golem | Crystal Shards, Diamonds, Enchanted Pickaxe |
| **Void Temple** | 35 | Navigate rifts, Defeat Echo Knight | Void Shards, Ancient Scrolls, Void Armor |
| **Echo Chamber** | 28 | Solve sound puzzles, Flee spirits | Echo Essence, Song of Power, Resonance Crystal |
| **Corrupted Vault** | 40 | Break seals, Defeat Void Lord | Corruption Core, Purification Artifact, Supreme Weapon |

```java
// Find dungeons
DungeonSystem dungeons = new DungeonSystem(world);
List<Dungeon> nearby = dungeons.getNearbyDungeons(playerX, playerZ, 500);

// Discover and attempt
for (Dungeon d : nearby) {
    if (d.getRecommendedPlayerLevel() <= playerLevel) {
        dungeons.discoverDungeon(d);
        // Begin dungeon!
    }
}
```

---

### 6. **Advanced Crafting System** ⚒️
Complex multi-stage recipes to create powerful items!

**Advanced Items:**

**Tools & Weapons:**
- **Void Pickaxe** - Mined void shards, 10+ levels to craft
- **Blade of Echoes** - High damage sword, 12+ levels
- **Crystal Armor** - Protective armor set, 15+ levels

**Magical Items:**
- **Void Chest** - Infinite storage, 25+ levels
- **Shield of Purification** - Defense artifact, 14+ levels
- **Essence Vial** - Rare potion ingredient, 8+ levels

**Potions:**
- **Potion of Ultimate Power** - All effects combined, 20+ levels

```java
// Craft advanced items
AdvancedCrafting crafting = new AdvancedCrafting();
AdvancedCrafting.AdvancedRecipe recipe = crafting.getRecipe("Void Pickaxe");

if (crafting.canCraft(recipe, inventory, playerLevel)) {
    crafting.craft(recipe, inventory);
}
```

---

## 🌟 Progression System

Players progress through **levels** (1-unlimited):
- **Earn XP** from quests and achievements
- **Level up** every 500 XP
- **Unlock recipes** and features at your level
- **Access dungeons** appropriate to your strength
- **Customize gameplay** with enchantments and potions

---

## 🎯 Gameplay Loop

1. **Explore** - Find dungeons and biomes
2. **Complete Quests** - Gather materials and XP
3. **Unlock Achievements** - Reach milestones
4. **Craft Advanced Items** - Create powerful gear
5. **Enchant Weapons** - Enhance with magic
6. **Conquer Dungeons** - Face epic challenges
7. **Grow Stronger** - Level up and repeat!

---

## 🔧 Customization

All systems are fully modifiable! You can:
- Add new enchantments
- Create custom quests
- Design new potions and effects
- Add unique mobs and bosses
- Create new dungeons
- Invent advanced recipes

See **Modding Guide** for implementation examples!

---

These systems work together to create a **complete, original gameplay experience** that's unique to Akincraft!
