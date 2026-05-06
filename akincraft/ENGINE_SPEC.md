# Akincraft Engine Specification

## 1. Project Overview

### Vision
Akincraft is a scalable voxel engine prototype designed to support infinite procedural worlds, modular subsystems, and future client-server separation. The engine demonstrates both Java and C++ implementations, making it a reference platform for cross-language voxel architecture.

### Target Platforms
- PC (Windows / Linux / macOS)
- Desktop workstations with OpenGL support
- Future web export via engine architecture ports

### Rendering Mode
- Real-time 3D voxel rendering using OpenGL
- Chunk-based mesh generation with face merging
- Perspective first-person camera

### Target Performance
- Maintain 60+ FPS on mid-tier hardware
- Use frustum culling to reduce draw calls
- Optimize chunk updates with greedy mesh generation

## 2. Engine Architecture

### Core Systems
- Engine Core: lifecycle management, game loop, subsystem orchestration
- ECS or modular component architecture: separate data from behavior
- Event System: dispatched state changes, input events, and persistence notifications
- World System: chunk manager, procedural generator, and active chunk cache
- Rendering System: GPU buffer management, shaders, culling, and mesh updates
- Physics System: AABB collision, gravity, raycasting, and movement
- Gameplay Systems: inventory, crafting, health, and AI
- Persistence System: world serialization, save/load, and modified chunk tracking

### Engine Core
- `App` / `Engine` entrypoint initializes subsystems
- Game loop updates input, physics, world, gameplay, and rendering
- Fixed-step or variable-step shared update semantics

### ECS
- Entity IDs reference component collections
- Components hold position, physics state, render metadata, and gameplay state
- Systems query component sets using component masks or registries

### Event System
- Central event bus delivers signals such as `ChunkLoaded`, `BlockChanged`, `SaveRequested`
- Loose coupling between subsystems enables extension

### World System
- `ChunkManager` holds a sliding window of active chunks around the player
- Chunks are defined in discrete coordinates: 16x16x128 voxels
- `VoxelStorage` stores block IDs and metadata for each chunk
- Biome assignment occurs per column based on noise values

### Procedural Generation Pipeline
- Seed-based generation ensures deterministic world layout
- Noise functions compute height, moisture, and cave tunnels
- Biome selection applies color and block rules
- Structures and feature placement use deterministic pseudo-random generation

### Rendering System
- Visible chunk meshes are generated per chunk update
- Greedy meshing reduces face count by merging aligned quad regions
- Mesh data is uploaded to GPU vertex and index buffers
- Frustum culling prevents rendering chunks outside camera view
- Lighting approximates ambient and directional day/night color

### Chunk Mesh Builder
- Convert voxel data to faces only where adjacent voxels are transparent
- Use greedy meshing per axis to collapse adjacent quads
- Maintain continuous vertex buffers for each chunk

### Lighting System
- Basic vertex colors simulate sunlight and dawn/dusk transitions
- Day/night cycle alters ambient light intensity and sky tint

### Physics System
- AABB collision detection for player and mobs
- Gravity and ground detection with simple step handling
- Raycasting for block selection and placement

### Gameplay Systems
- Inventory grid supports a fixed number of item slots
- Crafting uses recipe pairs to produce new items
- Simple health and passive/hard hostile mob AI
- AI can use finite-state machines or behavior states for wandering, chasing, and fleeing

### Networking (Optional)
- Use client-server architecture with state synchronization
- Stream chunk data over TCP/WebSocket
- Predict player movement locally and reconcile with authoritative state

## 3. World Generation Pipeline

### Seed-based Deterministic Generation
- Generation seed is stored in world metadata
- All terrain, biomes, and structures are derived from the seed

### Noise Algorithms
- Height generation: multi-octave 2D noise
- Detail noise: terrain roughness and small hills
- Cave generation: 3D noise thresholding
- Biome assignment: per-column noise mapped to `Plains`, `Forest`, and `Desert`

### Terrain Shaping
- Base height and amplitude define the world silhouette
- Biome influences surface blocks and decoration

### Cave Generation
- 3D noise is evaluated for each voxel to carve tunnels
- Cave density is adjusted by biome and altitude

### Structure Spawning
- Prototype structure placement uses seeded random values
- Structures are rare and deterministic

## 4. Data Structures

### Chunk Size
- Standard chunk dimensions: `16 x 16 x 128`
- Chunk coordinates are grouped by X/Z positions

### Block Representation
- Block ID represented by an integer enum
- Metadata stored separately when needed for orientation, fluid level, etc.

### Mesh Buffers
- Vertex buffer contains position, normal, and color
- Index buffer references triangles for each merged quad

### Spatial Partitioning
- Active chunks stored in a hash map keyed by chunk coordinates
- Player position determines which chunks are loaded

## 5. Rendering Pipeline

1. Determine visible chunks with frustum culling.
2. Generate or refresh chunk meshes using greedy meshing.
3. Upload vertex/index buffers to GPU.
4. Render with a perspective camera and directional lighting.
5. Adjust scene colors for the day/night cycle.

## 6. Performance Strategy

- Chunk streaming: load and unload chunks around the player in a radius
- Multithreading (future): generate terrain and meshes off the main thread
- Memory pooling: reuse mesh buffers when possible
- LOD: farther chunks can use simplified geometry
- Face merging: greedily reduce quad count by combining adjacent faces

## 7. AI System

### Passive Mobs
- Random wandering within a local radius
- Flee from hostile mobs or the player when approached

### Hostile Mobs
- Detect player inside an aggression radius
- Chase the player with direct navigation toward the current position
- Attack and reduce player health when in range

### Pathfinding
- Simplified navigation uses broad-phase movement around obstacles
- Full A* is optional for future expansion

## 8. Persistence System

- Serialize world metadata and modified chunk data
- Save only changed chunks for efficiency
- Restore the player state, inventory, and active seed

## 9. Testing Strategy

- Unit tests for world generation noise and biome assignment
- Load testing for chunk streaming and mesh regeneration
- Performance benchmarks for update and render loops

## 10. Development Roadmap

### Phase 1: Rendering + Chunk System
- Create a renderable voxel chunk system
- Implement procedural generation and camera movement
- Add greedy meshing and frustum culling

### Phase 2: Interaction + Physics
- Add block placement and destruction
- Add collision detection and gravity
- Add inventory and crafting prototypes

### Phase 3: Gameplay Systems
- Add mobs and simple AI
- Add day/night lighting and biome visuals
- Implement persistence and save/load systems

### Phase 4: Optimization + Polish
- Add chunk streaming and asynchronous generation
- Improve mesh optimization and memory usage
- Add networking readiness and multiplayer architecture
