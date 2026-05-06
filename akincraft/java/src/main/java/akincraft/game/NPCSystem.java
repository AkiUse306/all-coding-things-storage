package akincraft.game;

import java.util.*;

/**
 * Advanced NPC & Dialogue System
 * NPCs with dialogue trees, quests, and trading
 */
public class NPCSystem {
    
    public enum NPCType {
        MERCHANT("Merchant", "Buys and sells items"),
        QUEST_GIVER("Quest Giver", "Offers quests"),
        TRAINER("Trainer", "Teaches skills"),
        LORE_KEEPER("Lore Keeper", "Shares world lore"),
        GUARD("Guard", "Protects locations");
        
        public final String name;
        public final String description;
        
        NPCType(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }
    
    public static class DialogueNode {
        public String text;
        public List<DialogueChoice> choices;
        public String npcResponse;
        
        public DialogueNode(String text) {
            this.text = text;
            this.choices = new ArrayList<>();
        }
        
        public void addChoice(String text, DialogueNode nextNode, Runnable action) {
            choices.add(new DialogueChoice(text, nextNode, action));
        }
    }
    
    public static class DialogueChoice {
        public String text;
        public DialogueNode nextNode;
        public Runnable action;
        
        public DialogueChoice(String text, DialogueNode nextNode, Runnable action) {
            this.text = text;
            this.nextNode = nextNode;
            this.action = action;
        }
    }
    
    public static class NPC {
        public String name;
        public NPCType type;
        public float x, y, z;
        public String greeting;
        public DialogueNode dialogueTree;
        public Map<String, Integer> inventory;
        public int level;
        
        public NPC(String name, NPCType type, float x, float y, float z, String greeting) {
            this.name = name;
            this.type = type;
            this.x = x;
            this.y = y;
            this.z = z;
            this.greeting = greeting;
            this.inventory = new HashMap<>();
            this.level = 1;
        }
        
        public void interact() {
            System.out.println("\n=== " + name + " (" + type.name + ") ===");
            System.out.println(greeting);
            if (dialogueTree != null) {
                System.out.println(dialogueTree.text);
            }
        }
        
        @Override
        public String toString() {
            return String.format("[%s] %s at (%.0f, %.0f, %.0f)", 
                type.name, name, x, y, z);
        }
    }
    
    private List<NPC> npcs;
    
    public NPCSystem() {
        this.npcs = new ArrayList<>();
        initializeNPCs();
    }
    
    private void initializeNPCs() {
        // Create merchant
        NPC merchant = new NPC("Blacksmith Bob", NPCType.MERCHANT, 100, 50, 100,
            "Welcome to my forge! I buy ore and craft weapons.");
        merchant.inventory.put("iron_ore", 50);
        merchant.inventory.put("diamond_ore", 10);
        npcs.add(merchant);
        
        // Create quest giver
        NPC questGiver = new NPC("Elder Sage", NPCType.QUEST_GIVER, 200, 50, 200,
            "Welcome, adventurer. I have tasks that need your attention.");
        npcs.add(questGiver);
        
        // Create trainer
        NPC trainer = new NPC("Master Kai", NPCType.TRAINER, 300, 50, 300,
            "Ready to train? I'll teach you combat techniques.");
        npcs.add(trainer);
    }
    
    public void addNPC(NPC npc) {
        npcs.add(npc);
    }
    
    public List<NPC> getNearby(float x, float z, float range) {
        List<NPC> nearby = new ArrayList<>();
        for (NPC npc : npcs) {
            float dist = (float) Math.sqrt(Math.pow(npc.x - x, 2) + Math.pow(npc.z - z, 2));
            if (dist <= range) {
                nearby.add(npc);
            }
        }
        return nearby;
    }
    
    public NPC getNPCByName(String name) {
        for (NPC npc : npcs) {
            if (npc.name.equals(name)) return npc;
        }
        return null;
    }
}
