package com.acities.systems;

import com.acities.components.AIComponent;
import com.acities.components.PositionComponent;
import com.acities.components.VelocityComponent;
import com.acities.ecs.Entity;
import com.acities.ecs.EntityManager;
import com.acities.ecs.SystemBase;

import java.util.List;
import java.util.Random;

public class AISystem extends SystemBase {
    private final Random random = new Random();

    @Override
    public void update(double deltaSeconds, EntityManager entityManager) {
        List<Entity> agents = entityManager.getEntitiesWithComponents(AIComponent.class, PositionComponent.class, VelocityComponent.class);
        for (Entity agent : agents) {
            AIComponent ai = agent.getComponent(AIComponent.class).orElseThrow();
            PositionComponent position = agent.getComponent(PositionComponent.class).orElseThrow();
            VelocityComponent velocity = agent.getComponent(VelocityComponent.class).orElseThrow();

            switch (ai.currentState) {
                case IDLE -> {
                    if (random.nextDouble() < 0.01) {
                        ai.currentState = AIComponent.State.MOVING;
                        ai.targetX = random.nextFloat() * 800f;
                        ai.targetY = random.nextFloat() * 600f;
                    }
                    velocity.dx = 0;
                    velocity.dy = 0;
                }
                case MOVING -> {
                    float deltaX = ai.targetX - position.x;
                    float deltaY = ai.targetY - position.y;
                    float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                    if (distance < 8f) {
                        ai.currentState = AIComponent.State.IDLE;
                        velocity.dx = 0;
                        velocity.dy = 0;
                    } else {
                        float speed = 40f;
                        velocity.dx = deltaX / distance * speed;
                        velocity.dy = deltaY / distance * speed;
                    }
                }
                default -> {
                    velocity.dx = 0;
                    velocity.dy = 0;
                }
            }
        }
    }
}
