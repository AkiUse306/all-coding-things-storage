package com.acities.ecs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class EntityManager {
    private final Map<Integer, Entity> entities = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public Entity createEntity() {
        int id = nextId.getAndIncrement();
        Entity entity = new Entity(id);
        entities.put(id, entity);
        return entity;
    }

    public void removeEntity(int entityId) {
        entities.remove(entityId);
    }

    public List<Entity> getAllEntities() {
        return Collections.unmodifiableList(new ArrayList<>(entities.values()));
    }

    @SafeVarargs
    public final List<Entity> getEntitiesWithComponents(Class<? extends Component>... requiredComponents) {
        List<Entity> result = new ArrayList<>();
        for (Entity entity : entities.values()) {
            boolean matches = true;
            for (Class<? extends Component> required : requiredComponents) {
                if (!entity.hasComponent(required)) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                result.add(entity);
            }
        }
        return result;
    }
}
