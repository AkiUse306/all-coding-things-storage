package com.acities.entities;

import com.acities.components.AIComponent;
import com.acities.components.PlayerComponent;
import com.acities.components.PositionComponent;
import com.acities.components.RenderComponent;
import com.acities.components.VelocityComponent;
import com.acities.ecs.Entity;
import com.acities.ecs.EntityManager;
import com.acities.world.NPCLoader;

import java.io.IOException;

public final class EntityFactory {

    private EntityFactory() {
    }

    public static Entity createPlayer(EntityManager entityManager, String username, float x, float y) {
        Entity player = entityManager.createEntity();
        player.addComponent(new PlayerComponent(username, "citizen"));
        player.addComponent(new PositionComponent(x, y, 0f));
        player.addComponent(new VelocityComponent(0f, 0f, 0f));
        player.addComponent(new RenderComponent(0.1f, 0.6f, 0.9f, 12f));
        return player;
    }

    public static Entity createNPC(EntityManager entityManager, String npcType, float x, float y) {
        Entity npc = entityManager.createEntity();
        try {
            NPCLoader.NPCData data = NPCLoader.loadNPC(npcType + ".json");
            npc.addComponent(new AIComponent(AIComponent.State.IDLE, x, y));
            npc.addComponent(new PositionComponent(x, y, 0f));
            npc.addComponent(new VelocityComponent(0f, 0f, 0f));
            // Color based on personality
            float red, green, blue;
            switch (data.personality) {
                case "friendly":
                    red = 0.9f; green = 0.7f; blue = 0.5f;
                    break;
                case "tired":
                    red = 0.6f; green = 0.6f; blue = 0.6f;
                    break;
                case "neutral":
                    red = 0.7f; green = 0.7f; blue = 0.7f;
                    break;
                default:
                    red = 0.8f; green = 0.4f; blue = 0.2f;
            }
            npc.addComponent(new RenderComponent(red, green, blue, 8f));
        } catch (IOException e) {
            // Fallback if file not found
            npc.addComponent(new AIComponent(AIComponent.State.IDLE, x, y));
            npc.addComponent(new PositionComponent(x, y, 0f));
            npc.addComponent(new VelocityComponent(0f, 0f, 0f));
            npc.addComponent(new RenderComponent(0.8f, 0.4f, 0.2f, 8f));
        }
        return npc;
    }

    public static Entity createVehicle(EntityManager entityManager, float x, float y) {
        Entity vehicle = entityManager.createEntity();
        vehicle.addComponent(new PositionComponent(x, y, 0f));
        vehicle.addComponent(new VelocityComponent(0f, 0f, 0f));
        vehicle.addComponent(new RenderComponent(0.2f, 0.9f, 0.2f, 10f));
        return vehicle;
    }

    public static Entity createBuilding(EntityManager entityManager, float x, float y) {
        Entity building = entityManager.createEntity();
        building.addComponent(new PositionComponent(x, y, 0f));
        building.addComponent(new RenderComponent(0.5f, 0.5f, 0.5f, 32f));
        return building;
    }
}
