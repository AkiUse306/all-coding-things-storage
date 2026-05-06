# Akincraft API Reference

Quick reference for key classes and methods you'll be modifying.

## World System

### `World` Class
Main class managing all chunks and terrain.

```java
// Java
class World {
    void generateChunk(int x, int z);           // Create new chunk
    Chunk getChunk(int x, int z);              // Get chunk at coordinates
    void updateChunk(int x, int z);            // Regenerate chunk mesh
    void setBlock(int x, int y, int z, BlockType type);  // Place block
    BlockType getBlock(int x, int y, int z);  // Get block type
    void saveWorld(String filename);           // Save to file
    void loadWorld(String filename);           // Load from file
}
```

```cpp
// C++
class World {
public:
    void generateChunk(int x, int z);
    Chunk* getChunk(int x, int z);
    void updateChunk(int x, int z);
    void setBlock(int x, int y, int z, BlockType type);
    BlockType getBlock(int x, int y, int z);
    void saveWorld(const std::string& filename);
    void loadWorld(const std::string& filename);
};
```

## Chunk System

### `Chunk` Class
Represents a 16x16x128 section of the world.

```java
// Java
class Chunk {
    void setBlock(int x, int y, int z, BlockType type);
    BlockType getBlock(int x, int y, int z);
    void generateMesh();                    // Create renderable geometry
    Mesh getMesh();                         // Get current mesh
    boolean isModified();                   // Check if needs updating
    void save(FileWriter writer);
}
```

## Entity System

### `Entity` Class
Base class for all game objects (players, mobs, items).

```java
// Java
abstract class Entity {
    Vector3f position;
    Vector3f velocity;
    float health;
    float speed;
    
    abstract void update(float deltaTime);
    abstract void render();
    void takeDamage(float amount);
    void heal(float amount);
    void setVelocity(Vector3f vel);
}

// Create custom entities:
class Zombie extends Entity {
    @Override
    void update(float deltaTime) {
        // Your custom AI here
    }
}
```

### `EntityManager` Class
Manages all entities in the world.

```java
class EntityManager {
    void addEntity(Entity entity);
    void removeEntity(Entity entity);
    void updateAll(float deltaTime);
    List<Entity> getNearby(Vector3f position, float radius);
    boolean isColliding(Entity e1, Entity e2);
}
```

## Rendering System

### `Renderer` Class
Handles OpenGL rendering.

```java
class Renderer {
    void renderChunks(Camera camera);
    void renderEntity(Entity entity);
    void setBlockColor(BlockType type, Color color);
    void enableLighting(boolean enabled);
    void setFogDistance(float far, float near);
}
```

## Inventory System

### `Inventory` Class
Player inventory management.

```java
class Inventory {
    static final int SLOT_COUNT = 36;
    static final int HOTBAR_SIZE = 9;
    
    void addItem(Item item);
    void removeItem(int slotIndex);
    Item getItem(int slotIndex);
    boolean isFull();
    void swapSlots(int slot1, int slot2);
}

class Item {
    ItemType type;
    int quantity;
    float durability;      // 0.0 - 1.0
    
    void decreaseDurability(float amount);
    boolean isBroken();
}
```

## Physics System

### Collision Detection

```java
class Physics {
    static boolean checkCollision(AABB box1, AABB box2);
    static float raycastToBlock(Vector3f start, Vector3f direction);
    static void applyGravity(Entity entity, float deltaTime);
}

class AABB {  // Axis-Aligned Bounding Box
    Vector3f min;
    Vector3f max;
    
    boolean intersects(AABB other);
}
```

## Block System

### `BlockType` Enum
All available block types.

```java
enum BlockType {
    AIR(0),
    STONE(1),
    DIRT(2),
    GRASS(3),
    WOOD(4),
    LEAVES(5),
    WATER(6),
    LAVA(7),
    SAND(8),
    GRAVEL(9),
    COAL_ORE(10),
    IRON_ORE(11),
    GOLD_ORE(12),
    // Add custom blocks here
    CUSTOM_BLOCK_1(50);
    
    final int id;
    BlockType(int id) { this.id = id; }
}
```

### Block Properties

```java
class BlockProperties {
    float hardness;              // Time to break (seconds)
    float resistance;            // Explosion resistance
    boolean isOpaque;            // Blocks light
    boolean isSolid;            // Blocks movement
    ItemStack[] drops;          // What drops when broken
}
```

## Biome System

### `Biome` Class
Defines environmental characteristics.

```java
class Biome {
    String name;
    float temperature;           // -1.0 to 1.0
    float humidity;             // 0.0 to 1.0
    float height;               // Base height
    Color skyColor;
    Color foliageColor;
    BlockType[] blockPalette;   // Blocks in this biome
    
    float getHeightAt(int x, int z, NoiseGenerator noise);
}
```

## Common Patterns

### Adding a New Block Type

1. **Define the block:**
```java
enum BlockType {
    // ... existing types
    DIAMOND_ORE(25),
}
```

2. **Set properties:**
```java
BlockProperties props = new BlockProperties();
props.hardness = 3.0f;
props.isOpaque = true;
props.drops = new ItemStack[] { new ItemStack(ItemType.DIAMOND, 1) };
```

3. **Add to terrain generation:**
```java
if (y < 16 && random.nextFloat() < 0.01f) {
    chunk.setBlock(x, y, z, BlockType.DIAMOND_ORE);
}
```

### Creating a Custom Entity

```java
class Spider extends Entity {
    private float wanderTimer = 0;
    private float speed = 0.15f;
    
    @Override
    void update(float deltaTime) {
        wanderTimer += deltaTime;
        
        if (wanderTimer > 3.0f) {
            // Pick random direction
            velocity = getRandomDirection().multiply(speed);
            wanderTimer = 0;
        }
        
        // Move entity
        position = position.add(velocity);
        
        // Apply gravity
        Physics.applyGravity(this, deltaTime);
        
        // Attack player if close
        if (distanceToPlayer() < 2.0f) {
            playerEntity.takeDamage(0.5f);
        }
    }
}
```

### Implementing a Crafting Recipe

```java
CraftingRecipe recipe = new CraftingRecipe(
    new ItemStack[][] {
        { new ItemStack(ItemType.WOOD), new ItemStack(ItemType.WOOD), empty },
        { new ItemStack(ItemType.WOOD), new ItemStack(ItemType.WOOD), empty },
        { empty, empty, empty }
    },
    new ItemStack(ItemType.CRAFTING_TABLE, 1)
);
craftingManager.addRecipe(recipe);
```

## Useful Constants

```java
// Chunk dimensions
static final int CHUNK_WIDTH = 16;
static final int CHUNK_LENGTH = 16;
static final int CHUNK_HEIGHT = 128;

// World settings
static final int RENDER_DISTANCE = 10;      // Chunks to render
static final float GRAVITY = -9.81f;
static final float BLOCK_SIZE = 1.0f;

// Time
static final int TICKS_PER_SECOND = 20;
static final int DAY_LENGTH = 1200;         // Ticks
```

## Working with Noise Generation

```java
NoiseGenerator perlin = new PerlinNoise(seed);

// Get terrain height
float height = perlin.noise(x * 0.01f, z * 0.01f) * 64 + 64;

// Multi-octave (more variation)
float detail = perlin.noise(x * 0.1f, z * 0.1f) * 0.5f +
               perlin.noise(x * 0.05f, z * 0.05f) * 0.3f +
               perlin.noise(x * 0.01f, z * 0.01f) * 0.2f;
```

## Performance Optimization Hints

- **Chunk mesh generation** - Cache results to avoid regenerating every frame
- **Entity updates** - Only update entities near player
- **Rendering** - Use frustum culling to skip off-screen chunks
- **Physics** - Use spatial partitioning for faster collision checks

---

**Need more help?** Check the `development-guide.html` for detailed tutorials!
