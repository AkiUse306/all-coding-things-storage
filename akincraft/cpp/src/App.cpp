#include "App.h"
#include "Entity.h"
#include <GLFW/glfw3.h>
#include <glm/glm.hpp>
#include <algorithm>
#include <cmath>
#include <stdexcept>
#include <cstdlib>
#include <memory>

App::App()
    : window(nullptr), renderer(), startScreen(), entityManager(), currentTool(std::make_shared<Tool>(ToolType::WOODEN_SWORD)), hotbar(), selectedHotbarSlot(0), actionCooldown(0.15f), actionTimer(0.0f), attackCooldown(0.5f), attackTimer(0.0f), spawnTimer(0.0f), yaw(-90.0f), pitch(0.0f), posX(0.0f), posY(48.0f), posZ(0.0f), velY(0.0f), lastMouseX(0.0), lastMouseY(0.0), firstMouse(true), showStartScreen(true) {
    if (!glfwInit()) {
        throw std::runtime_error("Unable to initialize GLFW");
    }
    initWindow();
    startScreen.initialize();
    renderer.initialize();
    world.initialize();
    initializeHotbar();
}

App::~App() {
    if (window) {
        glfwDestroyWindow(window);
    }
    glfwTerminate();
}

int App::run() {
    float lastTime = static_cast<float>(glfwGetTime());
    while (!glfwWindowShouldClose(window)) {
        float currentTime = static_cast<float>(glfwGetTime());
        float delta = currentTime - lastTime;
        lastTime = currentTime;

        if (!showStartScreen) {
            update(delta);
        }
        render();
        if (showStartScreen && startScreen.shouldStart(window)) {
            showStartScreen = false;
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
            firstMouse = true;
        }
        glfwSwapBuffers(window);
        glfwPollEvents();
    }
    return 0;
}

void App::initWindow() {
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    window = glfwCreateWindow(1280, 720, "Akincraft C++", nullptr, nullptr);
    if (!window) {
        throw std::runtime_error("Failed to create GLFW window");
    }
    glfwMakeContextCurrent(window);
    glfwSwapInterval(1);
    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    glfwSetWindowUserPointer(window, this);
    glfwSetCursorPosCallback(window, App::cursorCallback);
}

void App::update(float delta) {
    float speed = 10.0f;
    float dx = 0.0f;
    float dz = 0.0f;
    if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) dz -= 1.0f;
    if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) dz += 1.0f;
    if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) dx -= 1.0f;
    if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) dx += 1.0f;
    if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS && posY <= 2.1f) velY = 8.0f;

    processHotkeys();

    if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS && actionTimer <= 0.0f) {
        if (!breakBlock()) {
            attackNearbyEntities();
        }
        actionTimer = actionCooldown;
    }

    if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS && actionTimer <= 0.0f) {
        placeBlock();
        actionTimer = actionCooldown;
    }

    float radYaw = yaw * 3.1415926f / 180.0f;
    posX += std::cos(radYaw) * dz * speed * delta;
    posZ += std::sin(radYaw) * dz * speed * delta;
    posX += std::cos(radYaw + 1.5708f) * dx * speed * delta;
    posZ += std::sin(radYaw + 1.5708f) * dx * speed * delta;

    velY -= 24.0f * delta;
    posY += velY * delta;
    if (posY < 2.0f) {
        posY = 2.0f;
        velY = 0.0f;
    }

    actionTimer = std::max(0.0f, actionTimer - delta);
    attackTimer = std::max(0.0f, attackTimer - delta);
    spawnMobs(delta);
    world.update(posX, posZ);
    entityManager.update(delta);
}

void App::onMouse(double xpos, double ypos) {
    if (showStartScreen) {
        return;
    }
    if (firstMouse) {
        lastMouseX = xpos;
        lastMouseY = ypos;
        firstMouse = false;
    }

    float dx = static_cast<float>(xpos - lastMouseX);
    float dy = static_cast<float>(lastMouseY - ypos);
    lastMouseX = xpos;
    lastMouseY = ypos;

    yaw += dx * 0.12f;
    pitch += dy * 0.12f;
    if (pitch > 89.0f) pitch = 89.0f;
    if (pitch < -89.0f) pitch = -89.0f;
}

void App::attackNearbyEntities() {
    if (attackTimer > 0.0f) {
        return;
    }
    attackTimer = attackCooldown;
    float attackRange = 2.5f;
    float attackDamage = currentTool ? currentTool->getDamage() : 1;

    for (auto& entity : entityManager.getEntities()) {
        float dx = entity->getPosition().x - posX;
        float dz = entity->getPosition().z - posZ;
        float distance = std::sqrt(dx * dx + dz * dz);
        if (distance < attackRange && !entity->isDead_()) {
            entity->damage(attackDamage);
            if (currentTool) {
                currentTool->damageToolByUse();
            }
            break;
        }
    }
}

void App::spawnMobs(float delta) {
    spawnTimer += delta;
    if (spawnTimer < 4.0f) {
        return;
    }
    spawnTimer = 0.0f;
    if (entityManager.getEntityCount() > 30) {
        return;
    }

    float angle = static_cast<float>(std::rand()) / RAND_MAX * 6.28f;
    float dist = 20.0f + static_cast<float>(std::rand()) / RAND_MAX * 20.0f;
    glm::vec3 spawnPos(posX + std::cos(angle) * dist, posY + 2.0f, posZ + std::sin(angle) * dist);

    int mobType = std::rand() % 7;
    switch (mobType) {
        case 0: entityManager.addEntity(std::make_shared<Zombie>(spawnPos)); break;
        case 1: entityManager.addEntity(std::make_shared<Creeper>(spawnPos)); break;
        case 2: entityManager.addEntity(std::make_shared<Skeleton>(spawnPos)); break;
        case 3: entityManager.addEntity(std::make_shared<Spider>(spawnPos)); break;
        case 4: entityManager.addEntity(std::make_shared<Pig>(spawnPos)); break;
        case 5: entityManager.addEntity(std::make_shared<Cow>(spawnPos)); break;
        case 6: entityManager.addEntity(std::make_shared<Enderman>(spawnPos)); break;
        default: entityManager.addEntity(std::make_shared<Chicken>(spawnPos)); break;
    }
}

void App::initializeHotbar() {
    hotbar[0] = { BlockType::GRASS, 16, 64 };
    hotbar[1] = { BlockType::DIRT, 16, 64 };
    hotbar[2] = { BlockType::STONE, 16, 64 };
    hotbar[3] = { BlockType::SAND, 16, 64 };
    hotbar[4] = { BlockType::OAK_LOG, 16, 64 };
    hotbar[5] = { BlockType::OAK_PLANK, 16, 64 };
    hotbar[6] = { BlockType::COBBLESTONE, 16, 64 };
    hotbar[7] = { BlockType::CRAFTING_TABLE, 1, 1 };
    hotbar[8] = { BlockType::FURNACE, 1, 1 };
}

BlockType App::getSelectedBlockType() const {
    return hotbar[selectedHotbarSlot].count > 0 ? hotbar[selectedHotbarSlot].type : BlockType::AIR;
}

std::string App::getBlockName(BlockType block) const {
    switch (block) {
        case BlockType::GRASS: return "Grass";
        case BlockType::DIRT: return "Dirt";
        case BlockType::STONE: return "Stone";
        case BlockType::SAND: return "Sand";
        case BlockType::OAK_LOG: return "Oak Log";
        case BlockType::OAK_PLANK: return "Oak Plank";
        case BlockType::COBBLESTONE: return "Cobblestone";
        case BlockType::CRAFTING_TABLE: return "Crafting Table";
        case BlockType::FURNACE: return "Furnace";
        default: return "Empty";
    }
}

void App::depositBlock(BlockType blockType) {
    if (blockType == BlockType::AIR) {
        return;
    }
    for (auto& slot : hotbar) {
        if (slot.type == blockType && slot.count < slot.maxCount) {
            slot.count++;
            return;
        }
    }
    for (auto& slot : hotbar) {
        if (slot.count == 0) {
            slot.type = blockType;
            slot.count = 1;
            slot.maxCount = 64;
            return;
        }
    }
}

bool App::traceBlock(glm::ivec3& hitBlock, glm::ivec3& emptyBlock) const {
    glm::vec3 direction;
    float radYaw = yaw * 3.1415926f / 180.0f;
    float radPitch = pitch * 3.1415926f / 180.0f;
    direction.x = std::cos(radPitch) * std::cos(radYaw);
    direction.y = std::sin(radPitch);
    direction.z = std::cos(radPitch) * std::sin(radYaw);

    glm::vec3 origin(posX, posY, posZ);
    glm::ivec3 lastBlock(
        static_cast<int>(std::floor(origin.x)),
        static_cast<int>(std::floor(origin.y)),
        static_cast<int>(std::floor(origin.z))
    );
    glm::ivec3 previousAir = lastBlock;

    const float maxDistance = 5.0f;
    const float step = 0.1f;
    for (float d = 0.0f; d <= maxDistance; d += step) {
        glm::vec3 sample = origin + direction * d;
        glm::ivec3 block(
            static_cast<int>(std::floor(sample.x)),
            static_cast<int>(std::floor(sample.y)),
            static_cast<int>(std::floor(sample.z))
        );

        if (block != lastBlock) {
            if (block.y >= 0 && block.y < Chunk::CHUNK_HEIGHT && world.getBlockAt(block.x, block.y, block.z) != BlockType::AIR) {
                hitBlock = block;
                emptyBlock = previousAir;
                return true;
            }
            previousAir = block;
            lastBlock = block;
        }
    }
    return false;
}

bool App::breakBlock() {
    glm::ivec3 hitBlock;
    glm::ivec3 emptyBlock;
    if (!traceBlock(hitBlock, emptyBlock)) {
        return false;
    }

    BlockType hitType = world.getBlockAt(hitBlock.x, hitBlock.y, hitBlock.z);
    if (hitType == BlockType::AIR || hitType == BlockType::WATER || hitType == BlockType::LAVA) {
        return false;
    }

    if (!world.setBlockAt(hitBlock.x, hitBlock.y, hitBlock.z, BlockType::AIR)) {
        return false;
    }
    depositBlock(hitType);
    return true;
}

bool App::placeBlock() {
    glm::ivec3 hitBlock;
    glm::ivec3 emptyBlock;
    if (!traceBlock(hitBlock, emptyBlock)) {
        return false;
    }

    if (emptyBlock.y < 0 || emptyBlock.y >= Chunk::CHUNK_HEIGHT) {
        return false;
    }

    if (world.getBlockAt(emptyBlock.x, emptyBlock.y, emptyBlock.z) != BlockType::AIR) {
        return false;
    }

    BlockType selected = getSelectedBlockType();
    if (selected == BlockType::AIR) {
        return false;
    }

    if (hotbar[selectedHotbarSlot].count <= 0) {
        return false;
    }

    if (!world.setBlockAt(emptyBlock.x, emptyBlock.y, emptyBlock.z, selected)) {
        return false;
    }

    hotbar[selectedHotbarSlot].count--;
    if (hotbar[selectedHotbarSlot].count == 0) {
        hotbar[selectedHotbarSlot].type = BlockType::AIR;
    }
    return true;
}

void App::processHotkeys() {
    for (int i = 0; i < 9; i++) {
        if (glfwGetKey(window, GLFW_KEY_1 + i) == GLFW_PRESS) {
            selectedHotbarSlot = i;
        }
    }
}

void App::drawHUD(int width, int height) const {
    glDisable(GL_DEPTH_TEST);
    glMatrixMode(GL_PROJECTION);
    glPushMatrix();
    glLoadIdentity();
    glOrtho(0, width, height, 0, -1, 1);
    glMatrixMode(GL_MODELVIEW);
    glPushMatrix();
    glLoadIdentity();

    float centerX = width * 0.5f;
    float centerY = height * 0.5f;
    glColor3f(1.0f, 1.0f, 1.0f);
    glBegin(GL_LINES);
    glVertex2f(centerX - 8.0f, centerY);
    glVertex2f(centerX + 8.0f, centerY);
    glVertex2f(centerX, centerY - 8.0f);
    glVertex2f(centerX, centerY + 8.0f);
    glEnd();

    float baseX = width * 0.5f - 220.0f;
    float baseY = height - 60.0f;
    for (int i = 0; i < 9; i++) {
        float x = baseX + i * 50.0f;
        float y = baseY;
        if (i == selectedHotbarSlot) {
            glColor3f(1.0f, 0.8f, 0.2f);
            glLineWidth(3.0f);
        } else {
            glColor3f(0.8f, 0.8f, 0.8f);
            glLineWidth(1.0f);
        }
        glBegin(GL_LINE_LOOP);
        glVertex2f(x, y);
        glVertex2f(x + 40.0f, y);
        glVertex2f(x + 40.0f, y + 40.0f);
        glVertex2f(x, y + 40.0f);
        glEnd();

        if (hotbar[i].count > 0) {
            float colorR = 0.4f;
            float colorG = 0.4f;
            float colorB = 0.4f;
            switch (hotbar[i].type) {
                case BlockType::GRASS: colorR = 0.4f; colorG = 0.75f; colorB = 0.3f; break;
                case BlockType::DIRT: colorR = 0.55f; colorG = 0.4f; colorB = 0.25f; break;
                case BlockType::STONE: colorR = 0.55f; colorG = 0.55f; colorB = 0.55f; break;
                case BlockType::SAND: colorR = 0.95f; colorG = 0.87f; colorB = 0.55f; break;
                case BlockType::OAK_LOG: colorR = 0.58f; colorG = 0.40f; colorB = 0.21f; break;
                case BlockType::OAK_PLANK: colorR = 0.82f; colorG = 0.66f; colorB = 0.52f; break;
                case BlockType::COBBLESTONE: colorR = 0.45f; colorG = 0.45f; colorB = 0.45f; break;
                case BlockType::CRAFTING_TABLE: colorR = 0.6f; colorG = 0.35f; colorB = 0.15f; break;
                case BlockType::FURNACE: colorR = 0.35f; colorG = 0.35f; colorB = 0.35f; break;
                default: break;
            }
            glColor3f(colorR, colorG, colorB);
            glBegin(GL_QUADS);
            glVertex2f(x + 8.0f, y + 8.0f);
            glVertex2f(x + 32.0f, y + 8.0f);
            glVertex2f(x + 32.0f, y + 32.0f);
            glVertex2f(x + 8.0f, y + 32.0f);
            glEnd();
        }
    }

    glPopMatrix();
    glMatrixMode(GL_PROJECTION);
    glPopMatrix();
    glMatrixMode(GL_MODELVIEW);
    glEnable(GL_DEPTH_TEST);
}

void App::drawTargetHighlight() const {
    glm::ivec3 hitBlock;
    glm::ivec3 emptyBlock;
    if (!traceBlock(hitBlock, emptyBlock)) {
        return;
    }

    glColor3f(1.0f, 1.0f, 0.25f);
    glLineWidth(2.0f);
    glBegin(GL_LINES);
    float x = static_cast<float>(hitBlock.x);
    float y = static_cast<float>(hitBlock.y);
    float z = static_cast<float>(hitBlock.z);

    // Bottom square
    glVertex3f(x, y, z); glVertex3f(x + 1.0f, y, z);
    glVertex3f(x + 1.0f, y, z); glVertex3f(x + 1.0f, y, z + 1.0f);
    glVertex3f(x + 1.0f, y, z + 1.0f); glVertex3f(x, y, z + 1.0f);
    glVertex3f(x, y, z + 1.0f); glVertex3f(x, y, z);

    // Top square
    glVertex3f(x, y + 1.0f, z); glVertex3f(x + 1.0f, y + 1.0f, z);
    glVertex3f(x + 1.0f, y + 1.0f, z); glVertex3f(x + 1.0f, y + 1.0f, z + 1.0f);
    glVertex3f(x + 1.0f, y + 1.0f, z + 1.0f); glVertex3f(x, y + 1.0f, z + 1.0f);
    glVertex3f(x, y + 1.0f, z + 1.0f); glVertex3f(x, y + 1.0f, z);

    // Vertical edges
    glVertex3f(x, y, z); glVertex3f(x, y + 1.0f, z);
    glVertex3f(x + 1.0f, y, z); glVertex3f(x + 1.0f, y + 1.0f, z);
    glVertex3f(x + 1.0f, y, z + 1.0f); glVertex3f(x + 1.0f, y + 1.0f, z + 1.0f);
    glVertex3f(x, y, z + 1.0f); glVertex3f(x, y + 1.0f, z + 1.0f);
    glEnd();
}

void App::cursorCallback(GLFWwindow* window, double xpos, double ypos) {
    App* self = static_cast<App*>(glfwGetWindowUserPointer(window));
    if (self) self->onMouse(xpos, ypos);
}

void App::render() {
    int width, height;
    glfwGetFramebufferSize(window, &width, &height);
    if (showStartScreen) {
        glViewport(0, 0, width, height);
        glClearColor(0.1f, 0.15f, 0.22f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        startScreen.render(width, height);
        return;
    }

    float aspect = static_cast<float>(width) / static_cast<float>(height);
    glViewport(0, 0, width, height);
    glClearColor(0.1f, 0.15f, 0.22f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glEnable(GL_DEPTH_TEST);

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    float fov = 60.0f;
    float top = std::tan(fov * 3.1415926f / 360.0f) * 0.1f;
    float right = top * aspect;
    glFrustum(-right, right, -top, top, 0.1, 1000.0);

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    renderer.render(world, posX, posY, posZ, yaw, pitch);
    entityManager.render();
    drawTargetHighlight();
    drawHUD(width, height);
}
