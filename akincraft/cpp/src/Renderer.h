#pragma once

#include "World.h"

struct Renderer {
    void initialize();
    void render(const World& world, float cameraX, float cameraY, float cameraZ, float yaw, float pitch);
};
