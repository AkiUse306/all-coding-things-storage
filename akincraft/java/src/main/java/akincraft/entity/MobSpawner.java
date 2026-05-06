package akincraft.entity;

import org.joml.Vector3f;
import java.util.*;

/**
 * Spawns hostile and passive mobs based on light level and time
 */
public class MobSpawner {
    private EntityManager entityManager;
    private float spawnTimer = 0;
    private float spawnInterval = 5.0f; // Attempt spawn every 5 seconds
    private int maxEntities = 200;
    private Random random = new Random();
    
    public MobSpawner(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public void update(float deltaTime, Vector3f playerPos, float lightLevel, boolean isNight) {
        spawnTimer += deltaTime;
        
        if (spawnTimer >= spawnInterval && entityManager.getEntityCount() < maxEntities) {
            spawnTimer = 0;
            
            // Only spawn hostile mobs at night or in dark areas
            if (isNight || lightLevel < 0.3f) {
                spawnHostileMob(playerPos);
            }
            
            // Passive mobs spawn anytime
            if (random.nextFloat() > 0.7f) {
                spawnPassiveMob(playerPos);
            }
        }
    }
    
    private void spawnHostileMob(Vector3f playerPos) {
        float radius = 50.0f;
        float angle = (float) (random.nextFloat() * Math.PI * 2);
        float dist = 30.0f + random.nextFloat() * 20.0f;
        
        Vector3f spawnPos = new Vector3f(
            playerPos.x + (float) Math.cos(angle) * dist,
            playerPos.y + 5,
            playerPos.z + (float) Math.sin(angle) * dist
        );
        
        int mobType = random.nextInt(3);
        Entity mob = switch (mobType) {
            case 0 -> new Zombie(spawnPos);
            case 1 -> new Creeper(spawnPos);
            case 2 -> new Skeleton(spawnPos);
            default -> new Zombie(spawnPos);
        };
        
        entityManager.addEntity(mob);
    }
    
    private void spawnPassiveMob(Vector3f playerPos) {
        float angle = (float) (random.nextFloat() * Math.PI * 2);
        float dist = 20.0f + random.nextFloat() * 30.0f;
        
        Vector3f spawnPos = new Vector3f(
            playerPos.x + (float) Math.cos(angle) * dist,
            playerPos.y + 2,
            playerPos.z + (float) Math.sin(angle) * dist
        );
        
        int mobType = random.nextInt(4);
        Entity mob = switch (mobType) {
            case 0 -> new Pig(spawnPos);
            case 1 -> new Cow(spawnPos);
            case 2 -> new Chicken(spawnPos);
            case 3 -> new Pig(spawnPos);
            default -> new Pig(spawnPos);
        };
        
        entityManager.addEntity(mob);
    }
}
