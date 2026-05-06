package com.acities.engine;

import com.acities.entities.EntityFactory;
import com.acities.ecs.EntityManager;
import com.acities.graphics.GraphicsManager;
import com.acities.systems.AISystem;
import com.acities.systems.PathfindingSystem;
import com.acities.systems.PhysicsSystem;
import com.acities.systems.RenderingSystem;
import com.acities.ecs.SystemBase;
import com.acities.engine.NativeEngine;
import com.acities.world.CityGenerator;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    private final GraphicsManager graphicsManager;
    private final EntityManager entityManager = new EntityManager();
    private final List<SystemBase> systems = new ArrayList<>();
    private final CityGenerator cityGenerator = new CityGenerator();
    private boolean running;

    public Engine(GraphicsManager graphicsManager) {
        this.graphicsManager = graphicsManager;
    }

    public void initialize() {
        System.out.println("[acities] Initializing simulation engine...");
        createSystems();
        createEntities();
        NativeEngine.initialize();
        running = true;
    }

    public void run() {
        long lastFrameTime = System.nanoTime();

        while (running && graphicsManager.isRunning()) {
            long now = System.nanoTime();
            double deltaSeconds = Math.max(0.001, (now - lastFrameTime) / 1_000_000_000.0);
            lastFrameTime = now;

            update(deltaSeconds);
            graphicsManager.render(entityManager);

            if (graphicsManager.windowShouldClose()) {
                running = false;
            }
        }
    }

    private void createSystems() {
        systems.add(new AISystem());
        systems.add(new PathfindingSystem());
        systems.add(new PhysicsSystem());
        systems.add(new RenderingSystem(graphicsManager));
    }

    private void createEntities() {
        // Generate the city layout
        cityGenerator.generateCity(entityManager);

        // Add dynamic entities
        EntityFactory.createPlayer(entityManager, "PlayerOne", 100f, 100f);
        EntityFactory.createNPC(entityManager, "shopkeeper", 300f, 200f);
        EntityFactory.createNPC(entityManager, "worker", 450f, 180f);
        EntityFactory.createVehicle(entityManager, 200f, 300f);
        for (int i = 0; i < 10; i++) {
            EntityFactory.createNPC(entityManager, "citizen", 120f + i * 40f, 400f);
        }
    }

    public void update(double deltaSeconds) {
        NativeEngine.tick(deltaSeconds);
        for (SystemBase system : systems) {
            system.update(deltaSeconds, entityManager);
        }
    }

    public void shutdown() {
        System.out.println("[acities] Shutting down simulation engine...");
        NativeEngine.shutdown();
        graphicsManager.shutdown();
    }
}
