# C++ Akincraft Prototype

This folder contains the C++ + GLFW + OpenGL implementation of Akincraft.

## Requirements

- CMake 3.24 or newer
- A C++17 compiler
- Python 3 (for CMake and FetchContent)

## Build and Run

```bash
cd cpp
mkdir -p build && cd build
cmake ..
cmake --build .
./akincraft_cpp
```

The project uses GLFW for windowing and GLAD for OpenGL function loading. It demonstrates chunk-based terrain generation, greedy face merging, and a first-person camera.
