package com.acities.world;

import com.acities.components.BuildingComponent;
import com.acities.components.InteriorComponent;
import com.acities.components.PositionComponent;
import com.acities.components.RenderComponent;
import com.acities.components.RoadComponent;
import com.acities.ecs.Entity;
import com.acities.ecs.EntityManager;

import java.util.Random;

public class CityGenerator {
    private final Random random = new Random();
    private final int cityWidth = 1280;
    private final int cityHeight = 720;
    private final int blockSize = 100;
    private final int roadWidth = 20;

    public void generateCity(EntityManager entityManager) {
        // Generate grid-based city layout
        int blocksX = cityWidth / blockSize;
        int blocksY = cityHeight / blockSize;

        for (int x = 0; x < blocksX; x++) {
            for (int y = 0; y < blocksY; y++) {
                if (random.nextFloat() < 0.7f) { // 70% chance to place a building
                    generateBuilding(entityManager, x * blockSize + roadWidth, y * blockSize + roadWidth,
                                   blockSize - roadWidth * 2, blockSize - roadWidth * 2);
                }
            }
        }

        // Generate roads
        generateRoads(entityManager);
    }

    private void generateBuilding(EntityManager entityManager, int x, int y, int maxWidth, int maxHeight) {
        int width = random.nextInt(maxWidth / 2) + maxWidth / 4;
        int height = random.nextInt(maxHeight / 2) + maxHeight / 4;
        String[] types = {"residential", "commercial", "industrial"};
        String type = types[random.nextInt(types.length)];

        Entity building = entityManager.createEntity();
        building.addComponent(new PositionComponent(x + width / 2f, y + height / 2f, 0f));
        building.addComponent(new BuildingComponent(width, height, type));

        // Add interior based on type
        String[] rooms;
        String description;
        switch (type) {
            case "residential":
                rooms = new String[]{"Living Room", "Kitchen", "Bedroom", "Bathroom"};
                description = "A cozy home with basic amenities.";
                break;
            case "commercial":
                rooms = new String[]{"Shop Floor", "Storage", "Office", "Restroom"};
                description = "A bustling business establishment.";
                break;
            case "industrial":
                rooms = new String[]{"Workshop", "Storage Bay", "Control Room", "Loading Dock"};
                description = "A functional industrial facility.";
                break;
            default:
                rooms = new String[]{"Main Room"};
                description = "An empty building.";
        }
        building.addComponent(new InteriorComponent(rooms, description));

        // Color based on type
        float red, green, blue;
        switch (type) {
            case "residential":
                red = 0.8f; green = 0.6f; blue = 0.4f;
                break;
            case "commercial":
                red = 0.4f; green = 0.8f; blue = 0.6f;
                break;
            case "industrial":
                red = 0.6f; green = 0.4f; blue = 0.8f;
                break;
            default:
                red = 0.5f; green = 0.5f; blue = 0.5f;
        }
        building.addComponent(new RenderComponent(red, green, blue, Math.min(width, height) / 2f));
    }

    private void generateRoads(EntityManager entityManager) {
        // Horizontal roads
        for (int y = blockSize; y < cityHeight; y += blockSize) {
            Entity road = entityManager.createEntity();
            road.addComponent(new PositionComponent(cityWidth / 2f, y, 0f));
            road.addComponent(new RoadComponent(cityWidth, false));
            road.addComponent(new RenderComponent(0.3f, 0.3f, 0.3f, roadWidth / 2f));
        }

        // Vertical roads
        for (int x = blockSize; x < cityWidth; x += blockSize) {
            Entity road = entityManager.createEntity();
            road.addComponent(new PositionComponent(x, cityHeight / 2f, 0f));
            road.addComponent(new RoadComponent(cityHeight, true));
            road.addComponent(new RenderComponent(0.3f, 0.3f, 0.3f, roadWidth / 2f));
        }
    }
}