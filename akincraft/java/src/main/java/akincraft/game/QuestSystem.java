package akincraft.game;

import java.util.*;

/**
 * Original Akincraft Quest & Achievement System
 * Provides goals and progression tracking for players
 */
public class QuestSystem {
    
    public enum QuestType {
        EXPLORATION("Explore new areas"),
        MINING("Mine specific ores"),
        COMBAT("Defeat mobs"),
        CRAFTING("Craft items"),
        SURVIVAL("Survive challenges");
        
        public final String description;
        QuestType(String description) {
            this.description = description;
        }
    }
    
    public static class Quest {
        public String id;
        public String name;
        public String description;
        public QuestType type;
        public int targetCount;
        public int currentProgress;
        public int rewardXP;
        public boolean completed;
        
        public Quest(String id, String name, String description, QuestType type, 
                    int targetCount, int rewardXP) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.type = type;
            this.targetCount = targetCount;
            this.rewardXP = rewardXP;
            this.currentProgress = 0;
            this.completed = false;
        }
        
        public float getProgress() {
            return (float) currentProgress / targetCount;
        }
        
        public boolean isComplete() {
            return currentProgress >= targetCount;
        }
        
        public void updateProgress(int amount) {
            currentProgress = Math.min(currentProgress + amount, targetCount);
            if (isComplete() && !completed) {
                completed = true;
            }
        }
        
        @Override
        public String toString() {
            return String.format("[%s] %s - %d/%d (%.0f%%)", 
                type.name(), name, currentProgress, targetCount, getProgress() * 100);
        }
    }
    
    public static class Achievement {
        public String id;
        public String title;
        public String description;
        public int rewardXP;
        public boolean unlocked;
        
        public Achievement(String id, String title, String description, int rewardXP) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.rewardXP = rewardXP;
            this.unlocked = false;
        }
    }
    
    private List<Quest> activeQuests;
    private List<Quest> completedQuests;
    private List<Achievement> achievements;
    private int totalXP;
    
    public QuestSystem() {
        this.activeQuests = new ArrayList<>();
        this.completedQuests = new ArrayList<>();
        this.achievements = new ArrayList<>();
        this.totalXP = 0;
        initializeAchievements();
        initializeDefaultQuests();
    }
    
    private void initializeAchievements() {
        achievements.add(new Achievement("first_block", "First Step", "Place your first block", 100));
        achievements.add(new Achievement("wood_collector", "Lumberjack", "Collect 64 wood blocks", 250));
        achievements.add(new Achievement("diamond_finder", "Diamond Seeker", "Find a diamond ore", 500));
        achievements.add(new Achievement("kill_100_mobs", "Monster Hunter", "Defeat 100 mobs", 1000));
        achievements.add(new Achievement("full_armor", "Armored Up", "Craft full armor set", 300));
    }
    
    private void initializeDefaultQuests() {
        addQuest(new Quest("collect_wood", "Collect Wood", "Gather 32 wood blocks", 
                          QuestType.MINING, 32, 250));
        addQuest(new Quest("explore_biome", "Explore Desert", "Travel to the desert biome", 
                          QuestType.EXPLORATION, 1, 300));
        addQuest(new Quest("defeat_mobs", "Mob Hunter", "Defeat 10 mobs", 
                          QuestType.COMBAT, 10, 400));
    }
    
    public void addQuest(Quest quest) {
        activeQuests.add(quest);
    }
    
    public void completeQuest(String questId) {
        for (Quest q : activeQuests) {
            if (q.id.equals(questId) && q.isComplete()) {
                activeQuests.remove(q);
                completedQuests.add(q);
                totalXP += q.rewardXP;
                System.out.println("✓ Quest Complete: " + q.name + " +" + q.rewardXP + " XP");
                return;
            }
        }
    }
    
    public void unlockAchievement(String achievementId) {
        for (Achievement a : achievements) {
            if (a.id.equals(achievementId) && !a.unlocked) {
                a.unlocked = true;
                totalXP += a.rewardXP;
                System.out.println("🏆 Achievement Unlocked: " + a.title + " +" + a.rewardXP + " XP");
                return;
            }
        }
    }
    
    public void updateQuestProgress(String questId, int amount) {
        for (Quest q : activeQuests) {
            if (q.id.equals(questId)) {
                q.updateProgress(amount);
                if (q.isComplete()) {
                    completeQuest(questId);
                }
                return;
            }
        }
    }
    
    public List<Quest> getActiveQuests() {
        return new ArrayList<>(activeQuests);
    }
    
    public List<Achievement> getUnlockedAchievements() {
        List<Achievement> unlocked = new ArrayList<>();
        for (Achievement a : achievements) {
            if (a.unlocked) unlocked.add(a);
        }
        return unlocked;
    }
    
    public int getTotalXP() {
        return totalXP;
    }
    
    public int getLevel() {
        return 1 + (totalXP / 500);  // Level up every 500 XP
    }
}
