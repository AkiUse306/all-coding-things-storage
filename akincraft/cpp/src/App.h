#pragma once

#include "StartScreen.h"
#include "World.h"
#include "Renderer.h"
#include "EntityManager.h"
#include "Tool.h"
#include "BlockType.h"
#include <GLFW/glfw3.h>
#include <memory>
#include <array>
#include <glm/glm.hpp>

struct HotbarSlot {
    BlockType type;
    int count;
    int maxCount;
};

class App {
public:
    App();
    ~App();
    int run();

private:
    void initWindow();
    void update(float delta);
    void render();
    void onMouse(double xpos, double ypos);
    void attackNearbyEntities();
    void spawnMobs(float delta);
    void processHotkeys();
    bool traceBlock(glm::ivec3& hitBlock, glm::ivec3& emptyBlock) const;
    void initializeHotbar();
    BlockType getSelectedBlockType() const;
    std::string getBlockName(BlockType block) const;
    void depositBlock(BlockType blockType);
    bool breakBlock();
    bool placeBlock();
    void drawHUD(int width, int height) const;
    void drawTargetHighlight() const;
    static void cursorCallback(GLFWwindow* window, double xpos, double ypos);

    GLFWwindow* window;
    World world;
    Renderer renderer;
    StartScreen startScreen;
    EntityManager entityManager;
    std::shared_ptr<Tool> currentTool;
    std::array<HotbarSlot, 9> hotbar;
    int selectedHotbarSlot;
    float actionCooldown;
    float actionTimer;
    float attackCooldown;
    float attackTimer;
    float spawnTimer;
    float yaw;
    float pitch;
    float posX;
    float posY;
    float posZ;
    float velY;
    double lastMouseX;
    double lastMouseY;
    bool firstMouse;
    bool leftMouseDown;
    bool rightMouseDown;
    bool showStartScreen;
};
