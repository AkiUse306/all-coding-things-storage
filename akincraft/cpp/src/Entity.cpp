#include "Entity.h"
#include <GL/gl.h>
#include <glm/gtc/type_ptr.hpp>
#include <cmath>
#include <random>

const float Creeper::EXPLOSION_DELAY = 1.5f;

static void renderUnitCube() {
    glBegin(GL_QUADS);
    // Front
    glVertex3f(-0.5f, -0.5f,  0.5f);
    glVertex3f( 0.5f, -0.5f,  0.5f);
    glVertex3f( 0.5f,  0.5f,  0.5f);
    glVertex3f(-0.5f,  0.5f,  0.5f);
    // Back
    glVertex3f( 0.5f, -0.5f, -0.5f);
    glVertex3f(-0.5f, -0.5f, -0.5f);
    glVertex3f(-0.5f,  0.5f, -0.5f);
    glVertex3f( 0.5f,  0.5f, -0.5f);
    // Left
    glVertex3f(-0.5f, -0.5f, -0.5f);
    glVertex3f(-0.5f, -0.5f,  0.5f);
    glVertex3f(-0.5f,  0.5f,  0.5f);
    glVertex3f(-0.5f,  0.5f, -0.5f);
    // Right
    glVertex3f( 0.5f, -0.5f,  0.5f);
    glVertex3f( 0.5f, -0.5f, -0.5f);
    glVertex3f( 0.5f,  0.5f, -0.5f);
    glVertex3f( 0.5f,  0.5f,  0.5f);
    // Top
    glVertex3f(-0.5f,  0.5f,  0.5f);
    glVertex3f( 0.5f,  0.5f,  0.5f);
    glVertex3f( 0.5f,  0.5f, -0.5f);
    glVertex3f(-0.5f,  0.5f, -0.5f);
    // Bottom
    glVertex3f(-0.5f, -0.5f, -0.5f);
    glVertex3f( 0.5f, -0.5f, -0.5f);
    glVertex3f( 0.5f, -0.5f,  0.5f);
    glVertex3f(-0.5f, -0.5f,  0.5f);
    glEnd();
}

Entity::Entity(const glm::vec3& pos, float w, float h, float maxHP)
    : position(pos), velocity(0.0f), rotation(0.0f), width(w), height(h),
      maxHealth(maxHP), health(maxHP), isDead(false), despawnTimer(0) {}

void Entity::update(float deltaTime) {
    move(deltaTime);
}

void Entity::render() {
    // Placeholder - actual rendering in Renderer
}

void Entity::move(float deltaTime) {
    position += velocity * deltaTime;
    velocity.y -= 9.81f * deltaTime; // Gravity
}

void Entity::damage(float amount) {
    health -= amount;
    if (health <= 0) {
        isDead = true;
        despawnTimer = 300; // 5 minutes
    }
}

void Entity::heal(float amount) {
    health = std::min(health + amount, maxHealth);
}

// === Zombie Implementation ===

Zombie::Zombie(const glm::vec3& pos)
    : Entity(pos, 0.6f, 1.95f, 20.0f) {
    velocity.y = 0;
}

void Zombie::update(float deltaTime) {
    if (isDead) return;
    
    wanderTimer += deltaTime;
    if (wanderTimer > 5.0f) {
        wanderTimer = 0;
        float angle = static_cast<float>(std::rand()) / RAND_MAX * 6.28f;
        velocity.x = std::cos(angle) * 3;
        velocity.z = std::sin(angle) * 3;
    }
    
    Entity::update(deltaTime);
}

void Zombie::render() {
    // TODO: render zombie model
}

// === Creeper Implementation ===

Creeper::Creeper(const glm::vec3& pos)
    : Entity(pos, 0.6f, 1.7f, 20.0f) {}

void Creeper::update(float deltaTime) {
    if (isDead) return;
    
    if (explosionTimer >= 0) {
        explosionTimer -= deltaTime;
        if (explosionTimer <= 0) {
            isDead = true;
        }
    }
    
    Entity::update(deltaTime);
}

void Creeper::render() {
    // TODO: render creeper with fuse animation
}

void Creeper::startExplosion() {
    if (explosionTimer < 0) {
        explosionTimer = EXPLOSION_DELAY;
    }
}

// === Pig Implementation ===

Pig::Pig(const glm::vec3& pos)
    : Entity(pos, 0.9f, 0.9f, 10.0f) {}

void Pig::update(float deltaTime) {
    if (isDead) return;
    
    wanderTimer += deltaTime;
    if (wanderTimer > 3.0f) {
        wanderTimer = 0;
        float angle = static_cast<float>(std::rand()) / RAND_MAX * 6.28f;
        velocity.x = std::cos(angle) * 2;
        velocity.z = std::sin(angle) * 2;
    }
    
    Entity::update(deltaTime);
}

void Pig::render() {
    glColor3f(0.9f, 0.6f, 0.5f);
    glPushMatrix();
    glTranslatef(position.x, position.y + 0.45f, position.z);
    glScalef(0.9f, 0.9f, 0.9f);
    renderUnitCube();
    glPopMatrix();
}

// === Cow ===

Cow::Cow(const glm::vec3& pos)
    : Entity(pos, 0.9f, 1.4f, 16.0f) {}

void Cow::update(float deltaTime) {
    if (isDead) return;
    wanderTimer += deltaTime;
    if (wanderTimer > 4.0f) {
        wanderTimer = 0;
        float angle = static_cast<float>(std::rand()) / RAND_MAX * 6.28f;
        velocity.x = std::cos(angle) * 1.8f;
        velocity.z = std::sin(angle) * 1.8f;
    }
    Entity::update(deltaTime);
}

void Cow::render() {
    glColor3f(0.6f, 0.5f, 0.4f);
    glPushMatrix();
    glTranslatef(position.x, position.y + 0.7f, position.z);
    glScalef(1.0f, 1.4f, 0.7f);
    renderUnitCube();
    glPopMatrix();
}

// === Chicken ===

Chicken::Chicken(const glm::vec3& pos)
    : Entity(pos, 0.4f, 0.7f, 6.0f) {}

void Chicken::update(float deltaTime) {
    if (isDead) return;
    bobTimer += deltaTime;
    if (bobTimer > 2.0f) {
        bobTimer = 0;
        velocity.y = 2.0f;
    }
    Entity::update(deltaTime);
}

void Chicken::render() {
    glColor3f(1.0f, 1.0f, 0.8f);
    glPushMatrix();
    glTranslatef(position.x, position.y + 0.35f, position.z);
    glScalef(0.4f, 0.7f, 0.4f);
    renderUnitCube();
    glPopMatrix();
}

// === Skeleton ===

Skeleton::Skeleton(const glm::vec3& pos)
    : Entity(pos, 0.6f, 1.9f, 18.0f) {}

void Skeleton::update(float deltaTime) {
    if (isDead) return;
    wanderTimer += deltaTime;
    shootTimer += deltaTime;
    if (wanderTimer > 4.0f) {
        wanderTimer = 0;
        float angle = static_cast<float>(std::rand()) / RAND_MAX * 6.28f;
        velocity.x = std::cos(angle) * 2.5f;
        velocity.z = std::sin(angle) * 2.5f;
    }
    Entity::update(deltaTime);
}

void Skeleton::render() {
    glColor3f(0.9f, 0.9f, 0.9f);
    glPushMatrix();
    glTranslatef(position.x, position.y + 0.95f, position.z);
    glScalef(0.4f, 1.9f, 0.4f);
    renderUnitCube();
    glPopMatrix();
}

// === Spider ===

Spider::Spider(const glm::vec3& pos)
    : Entity(pos, 0.8f, 0.6f, 12.0f) {}

void Spider::update(float deltaTime) {
    if (isDead) return;
    leapTimer += deltaTime;
    if (leapTimer > 3.0f) {
        leapTimer = 0;
        float angle = static_cast<float>(std::rand()) / RAND_MAX * 6.28f;
        velocity.x = std::cos(angle) * 4.0f;
        velocity.z = std::sin(angle) * 4.0f;
        velocity.y = 3.0f;
    }
    Entity::update(deltaTime);
}

void Spider::render() {
    glColor3f(0.1f, 0.1f, 0.1f);
    glPushMatrix();
    glTranslatef(position.x, position.y + 0.3f, position.z);
    glScalef(1.2f, 0.6f, 1.2f);
    renderUnitCube();
    glPopMatrix();
}

// === Enderman ===

Enderman::Enderman(const glm::vec3& pos)
    : Entity(pos, 0.6f, 2.9f, 40.0f) {}

void Enderman::update(float deltaTime) {
    if (isDead) return;
    teleportTimer += deltaTime;
    if (teleportTimer > 8.0f) {
        teleportTimer = 0;
        position.x += (static_cast<float>(std::rand()) / RAND_MAX - 0.5f) * 20.0f;
        position.z += (static_cast<float>(std::rand()) / RAND_MAX - 0.5f) * 20.0f;
    }
    Entity::update(deltaTime);
}

void Enderman::render() {
    glColor3f(0.0f, 0.0f, 0.0f);
    glPushMatrix();
    glTranslatef(position.x, position.y + 1.45f, position.z);
    glScalef(0.6f, 2.9f, 0.6f);
    renderUnitCube();
    glPopMatrix();
    glColor3f(0.2f, 0.0f, 0.6f);
    glPointSize(5.0f);
    glBegin(GL_POINTS);
    glVertex3f(position.x - 0.15f, position.y + 2.4f, position.z + 0.33f);
    glVertex3f(position.x + 0.15f, position.y + 2.4f, position.z + 0.33f);
    glEnd();
}
