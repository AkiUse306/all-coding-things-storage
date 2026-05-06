# engine-cpp

High-performance native engine modules for acities.

## Features

- CMake-based build system
- Native shared library for physics, pathfinding, and large-scale simulation
- JNI integration with the Java client
- A* pathfinding algorithm for NPC navigation

## Build

```bash
cd engine-cpp
cmake -S . -B build
cmake --build build
```

## Notes

- The build produces a shared library at `engine-cpp/build/libacities_engine.so`.
- The Java client loads this library through `System.loadLibrary("acities_engine")`.
