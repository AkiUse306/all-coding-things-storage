# Akincraft Enhancement Guide - Minecraft-Like Features

This guide documents the new systems added to make Akincraft more like Minecraft.

## 🎮 New Game Systems

### 1. Mining System (`MiningSystem.java`)
Progressive block breaking with realistic mining times.

**Features:**
- Different blocks have different mining speeds
- Tools affect mining speed significantly
- Efficiency enchantment speeds up mining
- XP rewards vary by ore type

**Tools & Effectiveness:**
- **Pickaxe**: Best for ores and stone
- **Axe**: Fast on logs and wood
- **Shovel**: Fast on dirt, sand, gravel
- **Sword**: For combat (slower on blocks)

**Block Mining Times (seconds):**
- Dirt: 0.5s | Grass: 0.6s
- Stone: 1.5s | Coal Ore: 3.0s
- Iron Ore: 5.0s | Diamond Ore: 8.0s
- Obsidian: 15.0s (hardest)

### 2. Experience & Leveling (`ExperienceSystem.java`)
Earn XP and level up to unlock recipes and abilities.

**Level Progression:**
- Start at Level 0
- Earn XP from mining ores, smelting, crafting
- Max Level: 30
- Each level requires more XP (scaling formula)

**XP Rewards:**
- Coal Ore: 17 XP
- Iron Ore: 25 XP
- Gold Ore: 50 XP
- Diamond Ore: 150 XP
- Mob kills: 5-10 XP
- Crafting: 5 XP

**Usage:**
```java
ExperienceSystem xpSystem = new ExperienceSystem();
xpSystem.addExperience(100);
int level = xpSystem.getCurrentLevel();
float progress = xpSystem.getProgressToNextLevel();
```

### 3. Item Drops (`ItemDrops.java`)
Realistic block breaking - different blocks drop different items.

**Drop Examples:**
- Grass → Dirt (not the block itself)
- Stone → Cobblestone (unless mined with wrong tool)
- Leaves → Sticks (occasionally drops leaves)
- Gravel → occasionally Flint
- Ores → Raw materials (Raw Iron, Raw Gold, etc.)

```java
ItemDrop drop = ItemDrops.getBlockDrop("DIAMOND_ORE");
String item = drop.itemId;      // "DIAMOND"
int quantity = drop.quantity;    // 1
int xp = drop.minXp + (int)(Math.random() * drop.maxXp);
```

### 4. Enhanced Combat (`EnhancedCombat.java`)
Realistic damage calculation with enchantments and armor.

**Weapon Damage:**
- Wooden Sword: 4.0 damage
- Stone Sword: 5.0 damage
- Iron Sword: 6.0 damage
- Diamond Sword: 7.0 damage
- Axes: 7-10 damage (slower but higher)

**Damage Modifiers:**
- Sharpness Enchantment: +1.25 per level
- Critical Hit (mid-air): x1.5 damage multiplier
- Armor Reduction: Each armor point = 4% damage reduction

**Combat Features:**
- Knockback force calculation
- On-hit status effects (Fire Aspect, Frost Walker, etc.)
- Attack cooldown system
- Armor damage reduction

```java
float damage = EnhancedCombat.calculateDamage("DIAMOND_SWORD", 3); // Sharpness III
float knockback = EnhancedCombat.calculateKnockback("AXE", 1);
float effective = EnhancedCombat.applyArmorReduction(damage, 8); // With 8 armor
```

### 5. Game Modes (`GameMode.java`)
Switch between different play styles and difficulty modes.

**Available Modes:**

#### Survival Mode
- Hunger enabled (must eat to survive)
- Mobs can damage you
- Must gather resources
- Mining has difficulty

#### Creative Mode
- No hunger or damage
- Unlimited items
- Instant block breaking
- Can fly freely
- No resource gathering needed

#### Adventure Mode
- Hunger enabled
- Can't be damaged by mobs
- Resource gathering normal
- Good for exploration

**Mode Switching:**
```java
GameMode gameMode = new GameMode(GameMode.Mode.SURVIVAL);
gameMode.setMode(GameMode.Mode.CREATIVE);
boolean flying = gameMode.isFlying();
gameMode.toggleFly();
```

### 6. Advanced Biomes (`AdvancedBiome.java`)
12+ unique biomes with different characteristics.

**Temperature & Humidity System:**
- Temperature affects precipitation (rain/snow)
- Humidity affects vegetation density
- Different lighting for different biomes

**Biome Types:**
- **Temperate**: Plains, Forest, Dense Forest
- **Desert**: Desert, Mesa
- **Mountains**: Mountains, Snowy Mountains
- **Water**: Ocean, Cold Ocean
- **Special**: Volcanic, Mushroom Fields, Savanna

**Biome Features:**
```java
AdvancedBiome biome = new AdvancedBiome(AdvancedBiome.BiomeType.FOREST);
int[] skyColor = biome.getSkyColor();
boolean rain = biome.shouldRain();
float oreMultiplier = biome.getOreFrequency("DIAMOND_ORE");
float treeDensity = biome.getTreeDensity();
```

### 7. Structure Generation (`StructureGenerator.java`)
Procedural generation of villages, houses, and structures.

**Structure Types:**
- **Villages**: Plains, Desert - NPCs and trading
- **Pyramids**: Desert - Contains treasures
- **Mineshafts**: Underground networks - Ores and loot
- **Wooden Houses**: Simple structures to explore
- **Mushroom Houses**: Unique structures in mushroom biomes
- **Cave Systems**: Natural caves with ores
- **Lava Tubes**: Underground lava channels

**Tree Variants:**
- Oak: 5-8 blocks, rounded foliage
- Birch: 6-8 blocks, smaller canopy
- Spruce: 7-10 blocks, conical shape

```java
List<int[]> house = StructureGenerator.generateWoodenHouse(100, 64, 100);
List<int[]> tree = StructureGenerator.generateTreeStructure(50, 64, 50, "spruce");
boolean shouldGen = StructureGenerator.shouldGenerateStructure(chunkX, chunkZ, 
                                                StructureGenerator.StructureType.VILLAGE, seed);
```

### 8. Block Registry (`BlockRegistry.java`)
Comprehensive block properties system.

**Properties for Each Block:**
- Hardness (mining time)
- Effective tools
- Drop item and quantity
- Transparency
- Solidity
- Light emission level
- Friction

**Example - Mining Different Blocks:**
```java
float mineTime = BlockRegistry.getMiningTime("STONE", "STONE_PICKAXE"); // 0.75s
mineTime = BlockRegistry.getMiningTime("STONE", "WOODEN_PICKAXE");    // 4.5s

boolean effective = BlockRegistry.isToolEffective("DIAMOND_ORE", "DIAMOND_PICKAXE"); // true
int lightLevel = BlockRegistry.getLightLevel("GLOWSTONE"); // 15
```

---

## 📊 Current Game Systems (Pre-existing)

The game also includes these established systems:

### Quest & Achievement System
Track progression with quests and unlockable achievements.

### Enchantment System
Magical item enhancements with multiple levels.

### Potion System  
Brew and consume temporary buff potions.

### Advanced Crafting System
Multi-stage recipes with level requirements.

### Unique Mob System
Original mob types with special abilities.

### Combat System
Attack cooldowns, damage calculation, hit feedback.

### Inventory System
Grid-based inventory for item management.

---

## 🛠️ Integration Examples

### Complete Mining Sequence
```java
// Player attacks block
MiningSystem mining = new MiningSystem();
MiningSystem.BlockHardness block = MiningSystem.BlockHardness.DIAMOND_ORE;
mining.updateMining(deltaTime, "DIAMOND_PICKAXE", block);

if (mining.isMiningComplete()) {
    // Block broken!
    ItemDrop drop = ItemDrops.getBlockDrop("DIAMOND_ORE");
    int xp = mining.getXpReward(block);
    
    // Update systems
    playerXp.addExperience(xp);
    player.getInventory().addItem(drop.itemId, drop.quantity);
}
```

### Combat Example
```java
// Player attacks mob
float damage = EnhancedCombat.calculateDamage("IRON_SWORD", 2); // Sharpness II
damage = EnhancedCombat.applyCriticalHit(damage, true);        // Jumping
damage = EnhancedCombat.applyArmorReduction(damage, mobArmor);

mob.takeDamage(damage);
// Knockback and effects applied...
```

### Mode-Based Logic
```java
GameMode mode = new GameMode(GameMode.Mode.SURVIVAL);

if (mode.needsFood()) {
    playerStats.decreaseHunger(0.1f);
}

if (mode.canGetDamageFromMobs()) {
    player.takeDamage(mobAttack);
}

if (mode.getMode() == GameMode.Mode.CREATIVE) {
    blockBreakTime = 0; // Instant
}
```

---

## 🎯 Recommended Development Priorities

1. **Integrate Mining System** into World/Chunk breaking
2. **Hook Experience System** to block breaking and mob kills
3. **Connect Item Drops** to mining completion
4. **Implement Game Mode** switching in UI
5. **Add Combat System** to mob interactions
6. **Generate Structures** in world generation
7. **Refine Biomes** with actual generation implementation

---

## 📝 Configuration & Tuning

### Adjust Mining Times
Edit `MiningSystem.BlockHardness` enum values:
```java
DIAMOND_ORE(8.0f, 100),  // 8 seconds, 100 XP
```

### Adjust XP Scaling
Edit `ExperienceSystem.calculateXpForLevel()` formula to make leveling faster/slower.

### Adjust Biome Features
Modify `AdvancedBiome` constructor values for temperature, humidity, visibility.

### Adjust Combat Damage
Edit weapon damage values in `EnhancedCombat.calculateDamage()`.

---

## 🚀 Next Steps

To fully leverage these systems:

1. Integrate into Java GUI/Renderer
2. Connect to C++ engine for cross-platform
3. Add network support (server/client communication)
4. Implement proper meshing integration
5. Add sound effects and particles
6. Create on-screen UI indicators

**Total Lines of Code Added:** ~2,500+ lines of new game systems
