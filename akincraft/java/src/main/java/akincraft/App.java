package akincraft;

import akincraft.entity.EntityManager;
import akincraft.entity.MobSpawner;
import akincraft.game.CommandConsole;
import akincraft.game.CommandProcessor;
import akincraft.game.Crafting;
import akincraft.game.Inventory;
import akincraft.game.PlayerController;
import akincraft.render.Renderer;
import akincraft.render.StartScreen;
import akincraft.save.SaveLoadManager;
import akincraft.world.WorldManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class App {
    private long window;
    private Renderer renderer;
    private StartScreen startScreen;
    private WorldManager world;
    private PlayerController player;
    private Inventory inventory;
    private Crafting crafting;
    private SaveLoadManager saveLoad;
    private CommandConsole commandConsole;
    private CommandProcessor commandProcessor;
    private EntityManager entityManager;
    private MobSpawner mobSpawner;
    private boolean headless;
    private boolean showStartScreen = true;

    public static void main(String[] args) {
        new App().run();
    }

    public void run() {
        // Check if running in headless mode (for containers/CI)
        headless = System.getenv("HEADLESS") != null || System.getenv("CI") != null;
        
        if (!headless) {
            initWindow();
        }
        initSystems();
        loop();
        
        if (!headless) {
            GLFW.glfwTerminate();
        }
    }

    private void initWindow() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        window = GLFW.glfwCreateWindow(1280, 720, "Akincraft Java", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
        GL.createCapabilities();
    }

    private void initSystems() {
        world = new WorldManager();
        player = new PlayerController(headless ? 0 : window);
        inventory = new Inventory();
        crafting = new Crafting(inventory);
        saveLoad = new SaveLoadManager();
        saveLoad.loadState(player);
        
        if (!headless) {
            renderer = new Renderer(world, player);
            startScreen = new StartScreen();
            commandConsole = new CommandConsole();
            entityManager = new EntityManager();
            mobSpawner = new MobSpawner(entityManager);
            commandProcessor = new CommandProcessor(world, player, inventory, crafting, entityManager);
            setupInputCallbacks();
        }
        
        world.initialize(player.getPosition());
    }

    private void setupInputCallbacks() {
        GLFW.glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
            if (commandConsole == null) {
                return;
            }

            if (key == GLFW.GLFW_KEY_SLASH && action == GLFW.GLFW_PRESS && !showStartScreen) {
                commandConsole.toggleActive();
                GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, commandConsole.isActive() ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_DISABLED);
                player.setLookEnabled(!commandConsole.isActive());
            }

            if (commandConsole.isActive()) {
                if ((key == GLFW.GLFW_KEY_BACKSPACE) && (action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT)) {
                    commandConsole.backspace();
                }
                if (key == GLFW.GLFW_KEY_ENTER && action == GLFW.GLFW_PRESS) {
                    String output = commandProcessor.execute(commandConsole.submit());
                    if (output != null && !output.isEmpty()) {
                        commandConsole.addOutput(output);
                    }
                    GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
                    player.setLookEnabled(true);
                }
                if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
                    if (commandConsole.isActive()) {
                        commandConsole.toggleActive();
                        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
                        player.setLookEnabled(true);
                    }
                }
            }
        });

        GLFW.glfwSetCharCallback(window, (win, codepoint) -> {
            if (commandConsole != null && commandConsole.isActive()) {
                commandConsole.appendChar((char) codepoint);
            }
        });
    }

    private void loop() {
        long lastTime = System.nanoTime();
        int ticks = 0;
        
        if (headless) {
            // Headless mode: run for 3 seconds and exit
            long startTime = System.currentTimeMillis();
            System.out.println("[HEADLESS MODE] Akincraft engine running...");
            System.out.println("Player at: " + player.getPosition());
            System.out.println("Inventory slots: " + inventory.getSlots().length);
            
            while (System.currentTimeMillis() - startTime < 3000) {
                long now = System.nanoTime();
                float delta = (now - lastTime) / 1_000_000_000.0f;
                lastTime = now;

                player.update(delta, world);
                world.update(player.getPosition());
                inventory.update(delta);
                crafting.update(delta);
                
                ticks++;
                if (ticks % 20 == 0) {
                    System.out.println("Tick " + ticks + " - Player Y: " + String.format("%.2f", player.getPosition().y));
                }
                
                try {
                    Thread.sleep(16); // ~60 FPS simulation
                } catch (InterruptedException e) {
                    break;
                }
            }
            
            System.out.println("[HEADLESS MODE] Engine test complete. Total ticks: " + ticks);
            saveLoad.saveState(player);
            
        } else {
            // Graphical mode
            while (!GLFW.glfwWindowShouldClose(window)) {
                long now = System.nanoTime();
                float delta = (now - lastTime) / 1_000_000_000.0f;
                lastTime = now;

                int[] width = new int[1];
                int[] height = new int[1];
                GLFW.glfwGetFramebufferSize(window, width, height);

                if (showStartScreen) {
                    startScreen.render(width[0], height[0]);
                    if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ENTER) == GLFW.GLFW_PRESS) {
                        showStartScreen = false;
                        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
                        player.resetMouse();
                    }
                } else {
                    world.tick(delta);
                    world.update(player.getPosition());
                    if (!commandConsole.isActive()) {
                        player.update(delta, world);
                    }
                    entityManager.update(delta);
                    mobSpawner.update(delta, player.getPosition(), world.getLightLevel(), world.isNight());
                    renderer.render(delta);
                    commandConsole.render(width[0], height[0]);
                    inventory.update(delta);
                    crafting.update(delta);
                }

                GLFW.glfwSwapBuffers(window);
                GLFW.glfwPollEvents();
            }
            saveLoad.saveState(player);
        }
    }
}
