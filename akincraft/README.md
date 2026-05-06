# Akincraft

Akincraft is a dual-stack voxel sandbox engine project with two implementation branches and an accompanying website:

- `java/` — Java + LWJGL + OpenGL
- `cpp/` — C++ + GLFW + OpenGL
- `website/` — C# ASP.NET Core website front-end

Each project provides a runnable prototype for chunk-based procedural terrain, biome-driven generation, block placement/destruction, greedy meshing, first-person controls, physics, and persistence.

## Getting Started

### Java stack

```bash
cd java
./gradlew run
```

### C++ stack

```bash
cd cpp
mkdir -p build && cd build
cmake ..
cmake --build .
./akincraft_cpp
```

### Website

```bash
cd website
dotnet run
```

## Features

- Procedural chunk terrain generation
- 3 biome types: plains, forest, desert
- Chunk management with LOD-friendly update scope
- Mesh optimization using face merging
- Block placement and destruction
- Inventory and crafting prototypes
- First-person camera with collision support
- Day/night cycle and basic lighting
- Save/load persistence architecture
- Build pipelines for Java and C++

## Repository Layout

- `java/` — Java engine using LWJGL and OpenGL
- `cpp/` — C++ engine using GLFW, GLAD, and OpenGL
- `ENGINE_SPEC.md` — full studio-level technical specification
- `.github/workflows/ci.yml` — CI build and test pipeline
- `CONTRIBUTING.md` — contribution guidelines
- `LICENSE` — MIT license

## Contribution

See [CONTRIBUTING.md](./CONTRIBUTING.md)
