package akincraft.world;

/**
 * Day/night cycle system with time progression
 */
public class DayNightCycle {
    private long worldTime = 0; // In ticks (20 ticks = 1 second)
    private static final int TICKS_PER_DAY = 24000;
    private static final int SUNRISE = 0;
    private static final int NOON = 6000;
    private static final int SUNSET = 12000;
    private static final int MIDNIGHT = 18000;
    
    public void update(float deltaTime) {
        // 1 real second = 20 ticks
        worldTime += (long)(deltaTime * 20);
        if (worldTime >= TICKS_PER_DAY) {
            worldTime -= TICKS_PER_DAY;
        }
    }
    
    public void setWorldTime(long worldTime) {
        this.worldTime = worldTime % TICKS_PER_DAY;
        if (this.worldTime < 0) {
            this.worldTime += TICKS_PER_DAY;
        }
    }

    public void addTicks(long ticks) {
        setWorldTime(this.worldTime + ticks);
    }

    public long getWorldTime() {
        return worldTime;
    }
    
    public float getTimeOfDay() {
        return (float) worldTime / TICKS_PER_DAY;
    }
    
    public float getLightLevel() {
        // Light ranges from 0 (midnight) to 1 (noon)
        float timeOfDay = getTimeOfDay();
        
        if (timeOfDay < 0.25f) {
            // Sunrise (0 to 0.25)
            return (timeOfDay / 0.25f) * 0.5f + 0.5f;
        } else if (timeOfDay < 0.75f) {
            // Daytime (0.25 to 0.75)
            return 1.0f;
        } else {
            // Sunset (0.75 to 1.0)
            return ((1.0f - timeOfDay) / 0.25f) * 0.5f + 0.5f;
        }
    }
    
    public boolean isDay() {
        return worldTime < 13000 && worldTime > 1000;
    }
    
    public boolean isNight() {
        return !isDay();
    }
    
    public int getDayCount() {
        return (int)(worldTime / TICKS_PER_DAY);
    }
}
