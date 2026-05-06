#include "PlayerStats.h"

const float PlayerStats::INVULN_TIME = 0.5f;

void PlayerStats::update(float deltaTime) {
    // Hunger decreases over time
    exhaustion += deltaTime * 0.05f;
    
    if (exhaustion >= 1.0f) {
        exhaustion -= 1.0f;
        hunger -= 0.1f;
    }
    
    // Passive health regeneration if well-fed
    if (hunger >= 18.0f && health < maxHealth) {
        health += 0.1f * deltaTime;
        health = std::min(health, maxHealth);
    }
    
    // Starvation damage
    if (hunger <= 0.0f && health > 0.5f) {
        health -= 0.1f * deltaTime;
    }
    
    hunger = std::max(0.0f, std::min(hunger, maxHunger));
    health = std::max(0.0f, std::min(health, maxHealth));
    
    damageInvulnerability = std::max(0.0f, damageInvulnerability - deltaTime);
}

void PlayerStats::takeDamage(float amount) {
    if (damageInvulnerability <= 0) {
        health -= amount;
        damageInvulnerability = INVULN_TIME;
        if (health < 0) health = 0;
    }
}

void PlayerStats::eat(const std::string& food) {
    if (food == "apple") {
        hunger += 4;
        saturation += 2;
    } else if (food == "bread") {
        hunger += 5;
        saturation += 6;
    } else if (food == "cooked_beef" || food == "cooked_pork") {
        hunger += 8;
        saturation += 12;
    } else if (food == "cooked_chicken") {
        hunger += 6;
        saturation += 7;
    } else if (food == "golden_apple") {
        hunger += 4;
        saturation += 10;
        health = std::min(health + 4.0f, maxHealth);
    }
    
    hunger = std::min(hunger, maxHunger);
    saturation = std::min(saturation, maxHunger);
}
