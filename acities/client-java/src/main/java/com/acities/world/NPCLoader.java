package com.acities.world;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class NPCLoader {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static NPCData loadNPC(String filename) throws IOException {
        try (InputStream is = NPCLoader.class.getClassLoader().getResourceAsStream("npcs/" + filename)) {
            if (is == null) {
                throw new IOException("NPC file not found: " + filename);
            }
            return mapper.readValue(is, NPCData.class);
        }
    }

    public static Map<String, NPCData> loadAllNPCs() throws IOException {
        Map<String, NPCData> npcs = new HashMap<>();
        // Load predefined NPCs
        npcs.put("shopkeeper", loadNPC("shopkeeper.json"));
        npcs.put("worker", loadNPC("worker.json"));
        npcs.put("citizen", loadNPC("citizen.json"));
        return npcs;
    }

    public static class NPCData {
        public int id;
        public String name;
        public String role;
        public String personality;
        public ScheduleItem[] schedule;
        public Dialogue dialogue;

        public static class ScheduleItem {
            public String time;
            public String activity;
        }

        public static class Dialogue {
            public String greeting;
            public String farewell;
        }
    }
}