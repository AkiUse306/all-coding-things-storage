package com.acities.systems;

import com.acities.components.BuildingComponent;
import com.acities.components.PositionComponent;
import com.acities.components.RenderComponent;
import com.acities.components.RoadComponent;
import com.acities.ecs.Entity;
import com.acities.ecs.EntityManager;
import com.acities.ecs.SystemBase;
import com.acities.graphics.GraphicsManager;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class RenderingSystem extends SystemBase {
    private final GraphicsManager graphicsManager;

    public RenderingSystem(GraphicsManager graphicsManager) {
        this.graphicsManager = graphicsManager;
    }

    @Override
    public void update(double deltaSeconds, EntityManager entityManager) {
        graphicsManager.render(entityManager);
    }
}
