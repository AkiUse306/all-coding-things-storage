#include "Weather.h"
#include <algorithm>
#include <cmath>

const int DayNightCycle::SUNRISE = 0;
const int DayNightCycle::NOON = 6000;
const int DayNightCycle::SUNSET = 12000;
const int DayNightCycle::MIDNIGHT = 18000;

void DayNightCycle::update(float deltaTime) {
    worldTime += static_cast<long>(deltaTime * 20);
    if (worldTime >= TICKS_PER_DAY) {
        worldTime -= TICKS_PER_DAY;
    }
}

float DayNightCycle::getTimeOfDay() const {
    return static_cast<float>(worldTime) / TICKS_PER_DAY;
}

float DayNightCycle::getLightLevel() const {
    float timeOfDay = getTimeOfDay();
    
    if (timeOfDay < 0.25f) {
        // Sunrise
        return (timeOfDay / 0.25f) * 0.5f + 0.5f;
    } else if (timeOfDay < 0.75f) {
        // Daytime
        return 1.0f;
    } else {
        // Sunset
        return ((1.0f - timeOfDay) / 0.25f) * 0.5f + 0.5f;
    }
}

bool DayNightCycle::isDay() const {
    return worldTime < 13000 && worldTime > 1000;
}

bool DayNightCycle::isNight() const {
    return !isDay();
}

int DayNightCycle::getDayCount() const {
    return static_cast<int>(worldTime / TICKS_PER_DAY);
}

// === WeatherSystem Implementation ===

void WeatherSystem::update(float deltaTime, bool isNight) {
    rainTimer += deltaTime;
    
    if (isRaining) {
        rainStrength = std::min(rainStrength + deltaTime * 0.5f, 1.0f);
    } else {
        rainStrength = std::max(rainStrength - deltaTime * 0.2f, 0.0f);
    }
    
    // Random weather changes
    if (rainTimer > 10.0f + (std::rand() % 1000) / 100.0f) {
        rainTimer = 0;
        
        if (isNight && std::rand() % 2 == 0) {
            isRaining = !isRaining;
            
            if (isRaining && std::rand() % 10 > 8) {
                isThundering = true;
                thunderTimer = 0.5f;
            }
        }
    }
    
    if (isThundering) {
        thunderTimer -= deltaTime;
        if (thunderTimer <= 0) {
            isThundering = false;
        }
    }
    
    // Update weather type
    if (isThundering) {
        currentWeather = WeatherType::THUNDER;
    } else if (isRaining) {
        currentWeather = WeatherType::RAIN;
    } else {
        currentWeather = WeatherType::CLEAR;
    }
}

void WeatherSystem::setRaining(bool raining) {
    this->isRaining = raining;
    if (!raining) {
        isThundering = false;
    }
}
