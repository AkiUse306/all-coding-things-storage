package akincraft.world;

import akincraft.game.PlayerController;
import akincraft.world.Dimension;
import akincraft.world.DayNightCycle;
import akincraft.world.WeatherSystem;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class WorldManager {
    private static final int ACTIVE_RADIUS = 3;
    private final Map<String, Chunk> chunks = new HashMap<>();
    private final int seed = 15832;
    private Dimension currentDimension = Dimension.OVERWORLD;
    private final DayNightCycle dayNightCycle = new DayNightCycle();
    private final WeatherSystem weatherSystem = new WeatherSystem();

    public void initialize(Vector3f playerPosition) {
        update(playerPosition);
    }

    public void update(Vector3f playerPosition) {
        int centerX = (int) Math.floor(playerPosition.x / Chunk.CHUNK_SIZE);
        int centerZ = (int) Math.floor(playerPosition.z / Chunk.CHUNK_SIZE);
        for (int x = centerX - ACTIVE_RADIUS; x <= centerX + ACTIVE_RADIUS; x++) {
            for (int z = centerZ - ACTIVE_RADIUS; z <= centerZ + ACTIVE_RADIUS; z++) {
                String key = Chunk.key(x, z);
                if (!chunks.containsKey(key)) {
                    Chunk chunk = new Chunk(x, z, seed);
                    chunks.put(key, chunk);
                }
            }
        }
    }

    public void tick(float deltaTime) {
        dayNightCycle.update(deltaTime);
        if (currentDimension == Dimension.OVERWORLD) {
            weatherSystem.update(deltaTime, dayNightCycle.isNight());
        } else {
            weatherSystem.setWeather(WeatherSystem.WeatherType.CLEAR);
        }
    }

    public Map<String, Chunk> getChunks() {
        return chunks;
    }

    public void handleCollision(PlayerController player) {
        if (player.getPosition().y < 2.0f) {
            player.getPosition().y = 2.0f;
        }
    }

    public BlockType getBlock(int worldX, int y, int worldZ) {
        int chunkX = floorDiv(worldX, Chunk.CHUNK_SIZE);
        int chunkZ = floorDiv(worldZ, Chunk.CHUNK_SIZE);
        Chunk chunk = chunks.get(Chunk.key(chunkX, chunkZ));
        if (chunk == null) return BlockType.AIR;
        int localX = mod(worldX, Chunk.CHUNK_SIZE);
        int localZ = mod(worldZ, Chunk.CHUNK_SIZE);
        return chunk.getBlock(localX, y, localZ);
    }

    public String getBiomeAt(float x, float z) {
        float value = Noise.perlin(x * 0.03f, z * 0.03f, seed);
        if (value < -0.2f) return "Desert";
        if (value < 0.25f) return "Plains";
        return "Forest";
    }

    public float getLightLevel() {
        return dayNightCycle.getLightLevel();
    }

    public boolean isNight() {
        return dayNightCycle.isNight();
    }

    public int getWorldTime() {
        return (int) dayNightCycle.getWorldTime();
    }

    public Dimension getDimension() {
        return currentDimension;
    }

    public void setDimension(Dimension dimension) {
        if (dimension == null) {
            return;
        }
        if (this.currentDimension != dimension) {
            this.currentDimension = dimension;
            chunks.clear();
        }
    }

    public WeatherSystem getWeatherSystem() {
        return weatherSystem;
    }

    public void setWorldTime(long ticks) {
        dayNightCycle.setWorldTime(ticks);
    }

    public void addWorldTime(long ticks) {
        dayNightCycle.addTicks(ticks);
    }

    private int floorDiv(int a, int b) {
        int result = a / b;
        if ((a ^ b) < 0 && a % b != 0) {
            result--;
        }
        return result;
    }

    private int mod(int a, int b) {
        int result = a % b;
        return result < 0 ? result + b : result;
    }
}
