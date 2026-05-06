package akincraft.game;

import akincraft.world.WorldManager;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class PlayerController {
    private final long window;
    private final Vector3f position = new Vector3f(0, 48, 0);
    private final Vector3f velocity = new Vector3f();
    private float yaw = -90.0f;
    private float pitch = 0.0f;
    private float cameraSpeed = 12.0f;
    private double lastMouseX;
    private double lastMouseY;
    private boolean firstMouse = true;
    private boolean lookEnabled = true;

    public PlayerController(long window) {
        this.window = window;
        
        // Only set up mouse movement callback if window is valid (non-headless mode)
        if (window != 0) {
            GLFW.glfwSetCursorPosCallback(window, (w, xpos, ypos) -> {
                if (!lookEnabled) {
                    return;
                }
                if (firstMouse) {
                    lastMouseX = xpos;
                    lastMouseY = ypos;
                    firstMouse = false;
                }
                float dx = (float) (xpos - lastMouseX);
                float dy = (float) (lastMouseY - ypos);
                lastMouseX = xpos;
                lastMouseY = ypos;
                yaw += dx * 0.12f;
                pitch += dy * 0.12f;
                pitch = Math.max(-89.0f, Math.min(89.0f, pitch));
            });
        }
    }

    public void update(float delta, WorldManager world) {
        Vector3f direction = new Vector3f();
        
        // Only process input if window is valid (non-headless mode)
        if (window != 0) {
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
                direction.z -= 1;
            }
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
                direction.z += 1;
            }
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
                direction.x -= 1;
            }
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
                direction.x += 1;
            }
            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS) {
                velocity.y = 8.0f;
            }
        }

        if (direction.length() > 0) {
            direction.normalize();
            float radYaw = (float) Math.toRadians(yaw);
            position.x += (float) Math.cos(radYaw) * direction.z * cameraSpeed * delta;
            position.z += (float) Math.sin(radYaw) * direction.z * cameraSpeed * delta;
            position.x += (float) Math.cos(radYaw + Math.PI / 2) * direction.x * cameraSpeed * delta;
            position.z += (float) Math.sin(radYaw + Math.PI / 2) * direction.x * cameraSpeed * delta;
        }

        velocity.y -= 24.0f * delta;
        position.y += velocity.y * delta;
        if (position.y < 2.0f) {
            position.y = 2.0f;
            velocity.y = 0.0f;
        }

        world.handleCollision(this);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void resetMouse() {
        firstMouse = true;
    }

    public void setLookEnabled(boolean enabled) {
        lookEnabled = enabled;
        if (enabled) {
            firstMouse = true;
        }
    }
}
