package akincraft.game;

import akincraft.entity.Creeper;
import akincraft.entity.EntityManager;
import akincraft.entity.Zombie;
import akincraft.world.Dimension;
import akincraft.world.WeatherSystem;
import akincraft.world.WorldManager;

public class CommandProcessor {
    private final WorldManager world;
    private final PlayerController player;
    private final Inventory inventory;
    private final Crafting crafting;
    private final EntityManager entityManager;

    public CommandProcessor(WorldManager world, PlayerController player, Inventory inventory, Crafting crafting, EntityManager entityManager) {
        this.world = world;
        this.player = player;
        this.inventory = inventory;
        this.crafting = crafting;
        this.entityManager = entityManager;
    }

    public String execute(String rawCommand) {
        if (rawCommand == null || rawCommand.isBlank()) {
            return "No command entered.";
        }

        String command = rawCommand.trim();
        if (command.startsWith("/")) {
            command = command.substring(1);
        }

        String[] args = command.split("\\s+");
        if (args.length == 0) {
            return "No command entered.";
        }

        switch (args[0].toLowerCase()) {
            case "help":
                return "Commands: help, give, time, weather, dimension, tp, spawn, heal, craft";
            case "give":
                return handleGive(args);
            case "time":
                return handleTime(args);
            case "weather":
                return handleWeather(args);
            case "dimension":
                return handleDimension(args);
            case "tp":
            case "teleport":
                return handleTeleport(args);
            case "spawn":
                return handleSpawn(args);
            case "heal":
                return handleHeal();
            case "craft":
                return handleCraft(args);
            default:
                return "Unknown command: " + args[0] + ". Use /help.";
        }
    }

    private String handleGive(String[] args) {
        if (args.length < 2) {
            return "Usage: /give <itemId> [count]";
        }
        try {
            int itemId = Integer.parseInt(args[1]);
            int count = 1;
            if (args.length >= 3) {
                count = Integer.parseInt(args[2]);
            }
            inventory.addItem(itemId, count);
            return "Gave " + count + " of item " + itemId + " to inventory.";
        } catch (NumberFormatException ex) {
            return "Invalid item id or count. Use numbers.";
        }
    }

    private String handleTime(String[] args) {
        if (args.length < 2) {
            return "Usage: /time set <day|night|noon|midnight> or /time add <ticks>";
        }
        switch (args[1].toLowerCase()) {
            case "set":
                if (args.length < 3) {
                    return "Usage: /time set <day|night|noon|midnight>";
                }
                return setTime(args[2].toLowerCase());
            case "add":
                if (args.length < 3) {
                    return "Usage: /time add <ticks>";
                }
                try {
                    long ticks = Long.parseLong(args[2]);
                    world.addWorldTime(ticks);
                    return "Added " + ticks + " ticks.";
                } catch (NumberFormatException e) {
                    return "Invalid tick count.";
                }
            default:
                return "Unknown time command. Use /time set or /time add.";
        }
    }

    private String setTime(String value) {
        switch (value) {
            case "day":
                world.setWorldTime(6000);
                return "Time set to day.";
            case "night":
                world.setWorldTime(18000);
                return "Time set to night.";
            case "noon":
                world.setWorldTime(6000);
                return "Time set to noon.";
            case "midnight":
                world.setWorldTime(18000);
                return "Time set to midnight.";
            default:
                return "Unknown time value. Use day, night, noon, or midnight.";
        }
    }

    private String handleWeather(String[] args) {
        if (args.length < 2) {
            return "Usage: /weather <clear|rain|thunder>";
        }
        WeatherSystem.WeatherType type = parseWeatherType(args[1]);
        if (type == null) {
            return "Unknown weather type. Use clear, rain, or thunder.";
        }
        world.getWeatherSystem().setWeather(type);
        return "Weather set to " + type.name().toLowerCase() + ".";
    }

    private WeatherSystem.WeatherType parseWeatherType(String value) {
        switch (value.toLowerCase()) {
            case "clear":
                return WeatherSystem.WeatherType.CLEAR;
            case "rain":
                return WeatherSystem.WeatherType.RAIN;
            case "thunder":
            case "thundering":
                return WeatherSystem.WeatherType.THUNDER;
            default:
                return null;
        }
    }

    private String handleDimension(String[] args) {
        if (args.length < 2) {
            return "Usage: /dimension <overworld|nether|end>";
        }
        Dimension target = parseDimension(args[1]);
        if (target == null) {
            return "Unknown dimension. Use overworld, nether, or end.";
        }
        world.setDimension(target);
        return "Changed dimension to " + target.getDisplayName() + ".";
    }

    private Dimension parseDimension(String value) {
        switch (value.toLowerCase()) {
            case "overworld":
                return Dimension.OVERWORLD;
            case "nether":
                return Dimension.NETHER;
            case "end":
                return Dimension.END;
            default:
                return null;
        }
    }

    private String handleTeleport(String[] args) {
        if (args.length < 4) {
            return "Usage: /tp <x> <y> <z>";
        }
        try {
            float x = Float.parseFloat(args[1]);
            float y = Float.parseFloat(args[2]);
            float z = Float.parseFloat(args[3]);
            player.getPosition().set(x, y, z);
            return "Teleported to " + x + ", " + y + ", " + z + ".";
        } catch (NumberFormatException e) {
            return "Invalid coordinates.";
        }
    }

    private String handleSpawn(String[] args) {
        if (args.length < 2) {
            return "Usage: /spawn <zombie|creeper> [count]";
        }
        String type = args[1].toLowerCase();
        int count = 1;
        if (args.length >= 3) {
            try {
                count = Integer.parseInt(args[2]);
            } catch (NumberFormatException ignored) {
            }
        }
        for (int i = 0; i < count; i++) {
            switch (type) {
                case "zombie" -> entityManager.addEntity(new Zombie(player.getPosition()));
                case "creeper" -> entityManager.addEntity(new Creeper(player.getPosition()));
                default -> {
                    return "Unknown entity type. Use zombie or creeper.";
                }
            }
        }
        return "Spawned " + count + " " + type + "(s).";
    }

    private String handleHeal() {
        player.getPosition();
        return "Heal command is not implemented yet.";
    }

    private String handleCraft(String[] args) {
        if (args.length < 2) {
            return "Usage: /craft bridge";
        }
        if (args[1].equalsIgnoreCase("bridge")) {
            if (crafting.craftBridge()) {
                return "Crafted a bridge item.";
            }
            return "Could not craft bridge.";
        }
        return "Unknown craft command. Use /craft bridge.";
    }
}
