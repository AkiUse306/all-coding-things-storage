#pragma once

/**
 * Day/night cycle system
 */
class DayNightCycle {
private:
    long worldTime = 0; // In ticks (20 ticks = 1 second)
    static const int TICKS_PER_DAY = 24000;
    static const int SUNRISE;
    static const int NOON;
    static const int SUNSET;
    static const int MIDNIGHT;
    
public:
    void update(float deltaTime);
    
    long getWorldTime() const { return worldTime; }
    float getTimeOfDay() const;
    float getLightLevel() const;
    bool isDay() const;
    bool isNight() const;
    int getDayCount() const;
};

/**
 * Weather system - rain, snow, thunder
 */
enum class WeatherType {
    CLEAR,
    RAIN,
    THUNDER,
    SNOW
};

class WeatherSystem {
private:
    float rainStrength = 0;
    float rainTimer = 0;
    bool isRaining = false;
    bool isThundering = false;
    float thunderTimer = 0;
    WeatherType currentWeather = WeatherType::CLEAR;
    
public:
    void update(float deltaTime, bool isNight);
    
    float getRainStrength() const { return rainStrength; }
    bool isRaining_() const { return isRaining; }
    bool isThundering_() const { return isThundering; }
    WeatherType getWeather() const { return currentWeather; }
    
    void setRaining(bool raining);
};
