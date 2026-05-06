package akincraft.entity;

import org.joml.Vector3f;
import java.util.*;

/**
 * Manages all entities in the world - mobs, items, projectiles
 */
public class EntityManager {
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> entitiesToRemove = new ArrayList<>();
    private Random random = new Random();
    
    public void update(float deltaTime) {
        for (Entity entity : entities) {
            entity.update(deltaTime);
        }
        
        // Remove dead entities
        entitiesToRemove.clear();
        for (Entity entity : entities) {
            if (entity.isDead()) {
                entitiesToRemove.add(entity);
            }
        }
        entities.removeAll(entitiesToRemove);
    }
    
    public void render() {
        for (Entity entity : entities) {
            entity.render();
        }
    }
    
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }
    
    public List<Entity> getEntities() {
        return new ArrayList<>(entities);
    }
    
    public List<Entity> getEntitiesNear(Vector3f position, float radius) {
        List<Entity> nearbyEntities = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getPosition().distance(position) < radius) {
                nearbyEntities.add(entity);
            }
        }
        return nearbyEntities;
    }
    
    public int getEntityCount() {
        return entities.size();
    }
}
