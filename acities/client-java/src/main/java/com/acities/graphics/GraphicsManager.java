package com.acities.graphics;

import com.acities.components.BuildingComponent;
import com.acities.components.PositionComponent;
import com.acities.components.RenderComponent;
import com.acities.components.RoadComponent;
import com.acities.ecs.Entity;
import com.acities.ecs.EntityManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GraphicsManager {
    private final int width;
    private final int height;
    private final String title;
    private long windowHandle;
    private boolean initialized;

    public GraphicsManager(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void initialize() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 2);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 0);

        windowHandle = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (windowHandle == 0) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        GLFW.glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(windowHandle);
        GLFW.glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(window, true);
            }
        });

        GL11.glViewport(0, 0, width, height);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        initialized = true;
    }

    public boolean isRunning() {
        return initialized && !GLFW.glfwWindowShouldClose(windowHandle);
    }

    public boolean windowShouldClose() {
        return GLFW.glfwWindowShouldClose(windowHandle);
    }

    public void render(EntityManager entityManager) {
        if (!initialized) {
            return;
        }

        GL11.glClearColor(0.05f, 0.08f, 0.12f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, 0, height, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        // Render roads first
        List<Entity> roads = entityManager.getEntitiesWithComponents(PositionComponent.class, RoadComponent.class, RenderComponent.class);
        GL11.glBegin(GL11.GL_QUADS);
        for (Entity road : roads) {
            PositionComponent position = road.getComponent(PositionComponent.class).orElseThrow();
            RoadComponent roadComp = road.getComponent(RoadComponent.class).orElseThrow();
            RenderComponent render = road.getComponent(RenderComponent.class).orElseThrow();

            float halfWidth = roadComp.width / 2f;
            float halfHeight = render.size;

            GL11.glColor3f(render.red, render.green, render.blue);
            if (roadComp.isHorizontal) {
                GL11.glVertex2f(position.x - halfWidth, position.y - halfHeight);
                GL11.glVertex2f(position.x + halfWidth, position.y - halfHeight);
                GL11.glVertex2f(position.x + halfWidth, position.y + halfHeight);
                GL11.glVertex2f(position.x - halfWidth, position.y + halfHeight);
            } else {
                GL11.glVertex2f(position.x - halfHeight, position.y - halfWidth);
                GL11.glVertex2f(position.x + halfHeight, position.y - halfWidth);
                GL11.glVertex2f(position.x + halfHeight, position.y + halfWidth);
                GL11.glVertex2f(position.x - halfHeight, position.y + halfWidth);
            }
        }
        GL11.glEnd();

        // Render buildings
        List<Entity> buildings = entityManager.getEntitiesWithComponents(PositionComponent.class, BuildingComponent.class, RenderComponent.class);
        GL11.glBegin(GL11.GL_QUADS);
        for (Entity building : buildings) {
            PositionComponent position = building.getComponent(PositionComponent.class).orElseThrow();
            BuildingComponent buildingComp = building.getComponent(BuildingComponent.class).orElseThrow();
            RenderComponent render = building.getComponent(RenderComponent.class).orElseThrow();

            float halfWidth = buildingComp.width / 2f;
            float halfHeight = buildingComp.height / 2f;

            GL11.glColor3f(render.red, render.green, render.blue);
            GL11.glVertex2f(position.x - halfWidth, position.y - halfHeight);
            GL11.glVertex2f(position.x + halfWidth, position.y - halfHeight);
            GL11.glVertex2f(position.x + halfWidth, position.y + halfHeight);
            GL11.glVertex2f(position.x - halfWidth, position.y + halfHeight);
        }
        GL11.glEnd();

        // Render other entities (players, NPCs, etc.)
        List<Entity> entities = entityManager.getEntitiesWithComponents(PositionComponent.class, RenderComponent.class);
        entities.removeAll(buildings);
        entities.removeAll(roads);
        GL11.glBegin(GL11.GL_QUADS);
        for (Entity entity : entities) {
            PositionComponent position = entity.getComponent(PositionComponent.class).orElseThrow();
            RenderComponent render = entity.getComponent(RenderComponent.class).orElseThrow();

            float size = render.size;
            float half = size * 0.5f;
            float x = position.x;
            float y = position.y;

            GL11.glColor3f(render.red, render.green, render.blue);
            GL11.glVertex2f(x - half, y - half);
            GL11.glVertex2f(x + half, y - half);
            GL11.glVertex2f(x + half, y + half);
            GL11.glVertex2f(x - half, y + half);
        }
        GL11.glEnd();

        GLFW.glfwSwapBuffers(windowHandle);
        GLFW.glfwPollEvents();
    }

    public void shutdown() {
        if (!initialized) {
            return;
        }

        GLFW.glfwDestroyWindow(windowHandle);
        GLFW.glfwTerminate();
        initialized = false;
    }
}
