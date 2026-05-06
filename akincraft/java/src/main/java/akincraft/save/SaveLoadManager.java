package akincraft.save;

import akincraft.game.*;
import akincraft.world.WorldManager;
import akincraft.world.Dimension;
import org.joml.Vector3f;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Expanded save/load system with player stats, inventory, and world state
 */
public class SaveLoadManager {
    private final Path saveDirectory = Path.of("saves");
    private final Path worldFile;
    private final Path playerFile;
    
    public SaveLoadManager() {
        Path worldPath = saveDirectory.resolve("world_0");
        this.worldFile = worldPath.resolve("world.json");
        this.playerFile = worldPath.resolve("player.json");
    }
    
    /**
     * Save complete game state
     */
    public void saveState(PlayerController player) {
        try {
            Files.createDirectories(playerFile.getParent());
            
            // Save player position and stats
            String playerJson = buildPlayerJson(player);
            Files.writeString(playerFile, playerJson, StandardCharsets.UTF_8);
            
            // TODO: Save world/chunk data
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Load complete game state
     */
    public void loadState(PlayerController player) {
        try {
            if (Files.exists(playerFile)) {
                String json = Files.readString(playerFile, StandardCharsets.UTF_8);
                loadPlayerJson(json, player);
            }
            // TODO: Load world/chunk data
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private String buildPlayerJson(PlayerController player) {
        Vector3f pos = player.getPosition();
        
        StringBuilder sb = new StringBuilder();
        sb.append("{\"player\":{");
        sb.append(String.format("\"x\":%.2f,\"y\":%.2f,\"z\":%.2f,", pos.x, pos.y, pos.z));
        sb.append(String.format("\"timestamp\":\"%s\"", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));
        sb.append("}}");
        
        return sb.toString();
    }
    
    private void loadPlayerJson(String json, PlayerController player) {
        try {
            Pattern posPattern = Pattern.compile("\\\"x\\\":([\\d.\\-]+),\\\"y\\\":([\\d.\\-]+),\\\"z\\\":([\\d.\\-]+)");
            Matcher posMatcher = posPattern.matcher(json);
            if (posMatcher.find()) {
                player.getPosition().x = Float.parseFloat(posMatcher.group(1));
                player.getPosition().y = Float.parseFloat(posMatcher.group(2));
                player.getPosition().z = Float.parseFloat(posMatcher.group(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
