package com.acities.systems;

import com.acities.components.AIComponent;
import com.acities.components.PositionComponent;
import com.acities.components.VelocityComponent;
import com.acities.ecs.Entity;
import com.acities.ecs.EntityManager;
import com.acities.ecs.SystemBase;
import com.acities.engine.NativeEngine;

import java.util.List;

public class PathfindingSystem extends SystemBase {
    private static final float PATH_UPDATE_DISTANCE = 10.0f;

    @Override
    public void update(double deltaSeconds, EntityManager entityManager) {
        List<Entity> agents = entityManager.getEntitiesWithComponents(AIComponent.class, PositionComponent.class, VelocityComponent.class);
        for (Entity agent : agents) {
            AIComponent ai = agent.getComponent(AIComponent.class).orElseThrow();
            PositionComponent position = agent.getComponent(PositionComponent.class).orElseThrow();
            VelocityComponent velocity = agent.getComponent(VelocityComponent.class).orElseThrow();

            if (ai.currentState == AIComponent.State.MOVING) {
                float distanceToTarget = (float) Math.sqrt(
                    Math.pow(ai.targetX - position.x, 2) + Math.pow(ai.targetY - position.y, 2)
                );

                if (distanceToTarget < PATH_UPDATE_DISTANCE) {
                    // Use native pathfinding to find a path to the target
                    float[] path = NativeEngine.findPath(position.x, position.y, ai.targetX, ai.targetY);
                    if (path != null && path.length >= 4) {
                        // Move towards the next waypoint in the path
                        float nextX = path[2]; // Skip the starting point
                        float nextY = path[3];
                        float deltaX = nextX - position.x;
                        float deltaY = nextY - position.y;
                        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                        if (distance > 0) {
                            float speed = 40f;
                            velocity.dx = deltaX / distance * speed;
                            velocity.dy = deltaY / distance * speed;
                        }
                    } else {
                        // No path found, fall back to direct movement
                        float deltaX = ai.targetX - position.x;
                        float deltaY = ai.targetY - position.y;
                        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                        if (distance > 0) {
                            float speed = 40f;
                            velocity.dx = deltaX / distance * speed;
                            velocity.dy = deltaY / distance * speed;
                        }
                    }
                }
            }
        }
    }
}