package com.acities.systems;

import com.acities.components.PositionComponent;
import com.acities.components.VelocityComponent;
import com.acities.ecs.Entity;
import com.acities.ecs.EntityManager;
import com.acities.ecs.SystemBase;

import java.util.List;

public class PhysicsSystem extends SystemBase {

    @Override
    public void update(double deltaSeconds, EntityManager entityManager) {
        List<Entity> movingEntities = entityManager.getEntitiesWithComponents(PositionComponent.class, VelocityComponent.class);
        for (Entity entity : movingEntities) {
            PositionComponent position = entity.getComponent(PositionComponent.class).orElseThrow();
            VelocityComponent velocity = entity.getComponent(VelocityComponent.class).orElseThrow();

            position.x += velocity.dx * deltaSeconds;
            position.y += velocity.dy * deltaSeconds;
            position.z += velocity.dz * deltaSeconds;

            if (position.x < 0) {
                position.x = 0;
                velocity.dx = -velocity.dx * 0.5f;
            }
            if (position.y < 0) {
                position.y = 0;
                velocity.dy = -velocity.dy * 0.5f;
            }
        }
    }
}
