package com.acities;

import com.acities.engine.Engine;
import com.acities.graphics.GraphicsManager;

public class GameClient {
    private final GraphicsManager graphicsManager;
    private final Engine engine;

    public GameClient() {
        this.graphicsManager = new GraphicsManager(1280, 720, "acities");
        this.engine = new Engine(graphicsManager);
    }

    public void launch() {
        System.out.println("[acities] Starting game client...");
        graphicsManager.initialize();
        engine.initialize();
        engine.run();
        System.out.println("[acities] Game client stopped.");
    }
}
