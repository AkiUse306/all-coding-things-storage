package akincraft.world;

import java.util.*;

/**
 * Original Akincraft Dungeon System
 * Procedurally generated dungeons with unique challenges
 */
public class DungeonSystem {
    
    public enum DungeonType {
        CRYSTAL_CAVE("Crystal Cave", "Filled with living crystals", 20),
        VOID_TEMPLE("Void Temple", "Ancient temple with void corruption", 35),
        ECHO_CHAMBER("Echo Chamber", "Walls that echo and distort reality", 28),
        CORRUPTED_VAULT("Corrupted Vault", "Repository of dark artifacts", 40);
        
        public final String name;
        public final String description;
        public final int difficulty;  // 1-50 scale
        
        DungeonType(String name, String description, int difficulty) {
            this.name = name;
            this.description = description;
            this.difficulty = difficulty;
        }
    }
    
    public static class Dungeon {
        public DungeonType type;
        public int x, z;  // World coordinates
        public int radius;
        public int level;  // 1-5, difficulty scaling
        public List<DungeonChallenge> challenges;
        public List<String> treasures;
        public boolean discovered;
        
        public Dungeon(DungeonType type, int x, int z, int level) {
            this.type = type;
            this.x = x;
            this.z = z;
            this.level = level;
            this.radius = 50;
            this.challenges = new ArrayList<>();
            this.treasures = new ArrayList<>();
            this.discovered = false;
            generateChallenges();
            generateTreasures();
        }
        
        private void generateChallenges() {
            Random rand = new Random(x * 73856093 ^ z * 19349663);  // Seeded
            
            switch(type) {
                case CRYSTAL_CAVE:
                    challenges.add(new DungeonChallenge("Avoid Crystal Spikes", "Don't touch glowing crystals"));
                    challenges.add(new DungeonChallenge("Defeat Crystal Golem", "Defeat the guardian"));
                    break;
                case VOID_TEMPLE:
                    challenges.add(new DungeonChallenge("Navigate Void Rifts", "Cross the dimensional tears"));
                    challenges.add(new DungeonChallenge("Defeat Echo Knight", "Defeat the ancient guardian"));
                    break;
                case ECHO_CHAMBER:
                    challenges.add(new DungeonChallenge("Solve Echo Puzzle", "Match the sound patterns"));
                    challenges.add(new DungeonChallenge("Escape the Corrupted", "Flee the pursuing spirits"));
                    break;
                case CORRUPTED_VAULT:
                    challenges.add(new DungeonChallenge("Break the Seal", "Destroy 5 corruption anchors"));
                    challenges.add(new DungeonChallenge("Final Trial", "Defeat the Void Lord"));
                    break;
            }
        }
        
        private void generateTreasures() {
            switch(type) {
                case CRYSTAL_CAVE:
                    treasures.add("crystal_shard");
                    treasures.add("diamond_ore");
                    treasures.add("enchanted_pickaxe");
                    break;
                case VOID_TEMPLE:
                    treasures.add("void_shard");
                    treasures.add("ancient_scroll");
                    treasures.add("void_armor");
                    break;
                case ECHO_CHAMBER:
                    treasures.add("echo_essence");
                    treasures.add("song_of_power");
                    treasures.add("resonance_crystal");
                    break;
                case CORRUPTED_VAULT:
                    treasures.add("corruption_core");
                    treasures.add("artifact_of_purification");
                    treasures.add("supreme_weapon");
                    break;
            }
        }
        
        public int getRecommendedPlayerLevel() {
            return level * 5 + type.difficulty / 5;
        }
        
        @Override
        public String toString() {
            return String.format("[%s] Level %d - %s", type.name, level, type.description);
        }
    }
    
    public static class DungeonChallenge {
        public String name;
        public String description;
        public boolean completed;
        
        public DungeonChallenge(String name, String description) {
            this.name = name;
            this.description = description;
            this.completed = false;
        }
    }
    
    private List<Dungeon> dungeons;
    
    public DungeonSystem(WorldManager world) {
        this.dungeons = new ArrayList<>();
        generateDungeons(world);
    }
    
    private void generateDungeons(WorldManager world) {
        Random rand = new Random(world.getSeed());
        
        // Generate dungeons at regular intervals
        int spacing = 200;  // Dungeons 200 blocks apart
        
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                int x = dx * spacing + rand.nextInt(50) - 25;
                int z = dz * spacing + rand.nextInt(50) - 25;
                
                DungeonType[] types = DungeonType.values();
                DungeonType type = types[rand.nextInt(types.length)];
                int level = 1 + rand.nextInt(5);
                
                dungeons.add(new Dungeon(type, x, z, level));
            }
        }
    }
    
    public List<Dungeon> getNearbyDungeons(int playerX, int playerZ, int range) {
        List<Dungeon> nearby = new ArrayList<>();
        for (Dungeon d : dungeons) {
            int dist = (int) Math.sqrt(Math.pow(d.x - playerX, 2) + Math.pow(d.z - playerZ, 2));
            if (dist <= range) {
                nearby.add(d);
            }
        }
        return nearby;
    }
    
    public void discoverDungeon(Dungeon dungeon) {
        dungeon.discovered = true;
        System.out.println("✨ Discovered: " + dungeon.type.name);
    }
}
