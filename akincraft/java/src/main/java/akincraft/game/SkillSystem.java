package akincraft.game;

import java.util.*;

/**
 * Advanced Skill & Talent Tree System
 * Players unlock and upgrade skills as they level up
 */
public class SkillSystem {
    
    public enum SkillCategory {
        COMBAT("Combat", "Melee & ranged combat"),
        MAGIC("Magic", "Spellcasting & enchantments"),
        MINING("Mining", "Resource extraction"),
        CRAFTING("Crafting", "Item creation"),
        SURVIVAL("Survival", "Environmental challenges");
        
        public final String name;
        public final String description;
        
        SkillCategory(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }
    
    public static class Skill {
        public String name;
        public SkillCategory category;
        public int level;      // 1-20
        public float cooldown;
        public float manaCost;
        public float damage;
        public String effect;
        
        public Skill(String name, SkillCategory category) {
            this.name = name;
            this.category = category;
            this.level = 1;
            this.cooldown = 5.0f;
            this.manaCost = 10.0f;
            this.damage = 5.0f;
        }
        
        public void levelUp() {
            this.level = Math.min(level + 1, 20);
            this.damage *= 1.1f;
            this.cooldown *= 0.95f;
            this.manaCost *= 1.05f;
        }
        
        @Override
        public String toString() {
            return String.format("%s (Lvl %d) - Damage: %.1f, Cooldown: %.1fs", 
                name, level, damage, cooldown);
        }
    }
    
    private Map<String, Skill> skills;
    private int skillPoints;
    
    public SkillSystem() {
        this.skills = new HashMap<>();
        this.skillPoints = 5;
        initializeSkills();
    }
    
    private void initializeSkills() {
        // Combat skills
        skills.put("Slash", new Skill("Slash", SkillCategory.COMBAT));
        skills.put("Power Strike", new Skill("Power Strike", SkillCategory.COMBAT));
        skills.put("Whirlwind", new Skill("Whirlwind", SkillCategory.COMBAT));
        
        // Magic skills
        skills.put("Fireball", new Skill("Fireball", SkillCategory.MAGIC));
        skills.put("Freeze", new Skill("Freeze", SkillCategory.MAGIC));
        skills.put("Lightning", new Skill("Lightning", SkillCategory.MAGIC));
        
        // Mining/Crafting
        skills.put("Power Mining", new Skill("Power Mining", SkillCategory.MINING));
        skills.put("Expert Crafting", new Skill("Expert Crafting", SkillCategory.CRAFTING));
    }
    
    public void upgradeSkill(String skillName) {
        if (skillPoints > 0 && skills.containsKey(skillName)) {
            skills.get(skillName).levelUp();
            skillPoints--;
            System.out.println("✓ Upgraded: " + skillName + " → Lvl " + skills.get(skillName).level);
        }
    }
    
    public Skill getSkill(String name) {
        return skills.get(name);
    }
    
    public void addSkillPoints(int amount) {
        this.skillPoints += amount;
    }
    
    public Map<String, Skill> getSkillsByCategory(SkillCategory category) {
        Map<String, Skill> result = new HashMap<>();
        for (Map.Entry<String, Skill> entry : skills.entrySet()) {
            if (entry.getValue().category == category) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }
}
