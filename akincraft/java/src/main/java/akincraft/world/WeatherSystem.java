package akincraft.world;

/**
 * Weather system - rain, snow, thunder
 */
public class WeatherSystem {
    private float rainStrength = 0;
    private float rainTimer = 0;
    private boolean isRaining = false;
    private boolean isThundering = false;
    private float thunderTimer = 0;
    
    public enum WeatherType {
        CLEAR, RAIN, THUNDER, SNOW
    }
    
    private WeatherType currentWeather = WeatherType.CLEAR;
    
    public void update(float deltaTime, boolean isNight) {
        rainTimer += deltaTime;
        
        if (isRaining) {
            rainStrength = Math.min(rainStrength + deltaTime * 0.5f, 1.0f);
        } else {
            rainStrength = Math.max(rainStrength - deltaTime * 0.2f, 0);
        }
        
        // Random weather changes (roughly every 10-20 seconds)
        if (rainTimer > 10.0f + Math.random() * 10.0f) {
            rainTimer = 0;
            
            if (isNight && Math.random() > 0.5f) {
                isRaining = !isRaining;
                
                if (isRaining && Math.random() > 0.8f) {
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
            currentWeather = WeatherType.THUNDER;
        } else if (isRaining) {
            currentWeather = WeatherType.RAIN;
        } else {
            currentWeather = WeatherType.CLEAR;
        }
    }

    public void setWeather(WeatherType type) {
        if (type == null) {
            return;
        }
        this.currentWeather = type;
        this.isRaining = type == WeatherType.RAIN || type == WeatherType.THUNDER;
        this.isThundering = type == WeatherType.THUNDER;
        if (!isRaining) {
            rainStrength = 0;
            thunderTimer = 0;
        }
    }
    
    public float getRainStrength() {
        return rainStrength;
    }
    
    public boolean isRaining() {
        return isRaining;
    }
    
    public boolean isThundering() {
        return isThundering;
    }
    
    public WeatherType getWeather() {
        return currentWeather;
    }
    
    public void setRaining(boolean raining) {
        this.isRaining = raining;
        if (!raining) {
            isThundering = false;
        }
    }
}
