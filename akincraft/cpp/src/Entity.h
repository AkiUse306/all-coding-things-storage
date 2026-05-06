#pragma once

#include <glm/glm.hpp>
#include <vector>
#include <memory>

/**
 * Base entity class for all entities
 */
class Entity {
protected:
    glm::vec3 position;
    glm::vec3 velocity;
    glm::vec3 rotation;
    float width, height;
    float health, maxHealth;
    bool isDead;
    float despawnTimer;
    
public:
    Entity(const glm::vec3& pos, float w, float h, float maxHP);
    virtual ~Entity() = default;
    
    virtual void update(float deltaTime);
    virtual void render();
    
    void move(float deltaTime);
    void damage(float amount);
    void heal(float amount);
    
    glm::vec3 getPosition() const { return position; }
    float getHealth() const { return health; }
    bool isDead_() const { return isDead; }
};

/**
 * Zombie mob
 */
class Zombie : public Entity {
private:
    float attackTimer = 0;
    float attackCooldown = 1.5f;
    float detectionRange = 32.0f;
    float wanderTimer = 0;
    
public:
    Zombie(const glm::vec3& pos);
    void update(float deltaTime) override;
    void render() override;
};

/**
 * Creeper mob that explodes
 */
class Creeper : public Entity {
private:
    float explosionTimer = -1;
    static const float EXPLOSION_DELAY;
    
public:
    Creeper(const glm::vec3& pos);
    void update(float deltaTime) override;
    void render() override;
    
    void startExplosion();
    bool isExploding() const { return explosionTimer >= 0; }
};

/**
 * Passive mobs - Pig, Cow, Chicken
 */
class Pig : public Entity {
private:
    float wanderTimer = 0;
    
public:
    Pig(const glm::vec3& pos);
    void update(float deltaTime) override;
    void render() override;
};

class Cow : public Entity {
private:
    float wanderTimer = 0;

public:
    Cow(const glm::vec3& pos);
    void update(float deltaTime) override;
    void render() override;
};

class Chicken : public Entity {
private:
    float bobTimer = 0;

public:
    Chicken(const glm::vec3& pos);
    void update(float deltaTime) override;
    void render() override;
};

class Skeleton : public Entity {
private:
    float shootTimer = 0;
    float wanderTimer = 0;

public:
    Skeleton(const glm::vec3& pos);
    void update(float deltaTime) override;
    void render() override;
};

class Spider : public Entity {
private:
    float leapTimer = 0;

public:
    Spider(const glm::vec3& pos);
    void update(float deltaTime) override;
    void render() override;
};

class Enderman : public Entity {
private:
    float teleportTimer = 0;

public:
    Enderman(const glm::vec3& pos);
    void update(float deltaTime) override;
    void render() override;
};
