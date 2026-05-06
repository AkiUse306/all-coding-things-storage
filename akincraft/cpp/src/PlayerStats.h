#pragma once

#include <glm/glm.hpp>
#include <string>

/**
 * Player health, hunger, and status effects
 */
class PlayerStats {
private:
    float health = 20.0f;
    float maxHealth = 20.0f;
    float hunger = 20.0f;
    float maxHunger = 20.0f;
    float exhaustion = 0.0f;
    float saturation = 5.0f;
    float damageInvulnerability = 0.0f;
    static const float INVULN_TIME;
    
public:
    void update(float deltaTime);
    void takeDamage(float amount);
    void eat(const std::string& food);
    
    float getHealth() const { return health; }
    float getHunger() const { return hunger; }
    float getMaxHealth() const { return maxHealth; }
    float getMaxHunger() const { return maxHunger; }
    bool isAlive() const { return health > 0; }
    bool canAttack() const { return damageInvulnerability <= 0; }
};
