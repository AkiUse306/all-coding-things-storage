package akincraft.game;

import java.util.*;

/**
 * Advanced Particle Effects System
 * Visual effects for combat, magic, and environmental effects
 */
public class ParticleSystem {
    
    public enum ParticleType {
        // Combat
        BLOOD_SPLATTER("Blood", 1.0f, 0.5f),
        IMPACT_SPARK("Spark", 0.5f, 0.3f),
        EXPLOSION("Explosion", 2.0f, 1.0f),
        
        // Magic
        FIREBALL_BURST("Fire", 1.5f, 0.7f),
        FROST_CRYSTAL("Frost", 1.0f, 0.5f),
        LIGHTNING_BOLT("Lightning", 2.0f, 0.2f),
        
        // Environmental
        SMOKE("Smoke", 1.2f, 0.8f),
        DUST("Dust", 0.8f, 0.4f),
        FLAME("Flame", 1.0f, 0.6f),
        
        // Special
        HEAL_GLOW("Heal", 0.8f, 0.4f),
        POISON_CLOUD("Poison", 1.5f, 1.0f),
        CORRUPTION("Corruption", 2.0f, 1.2f);
        
        public final String name;
        public final float size;
        public final float duration;
        
        ParticleType(String name, float size, float duration) {
            this.name = name;
            this.size = size;
            this.duration = duration;
        }
    }
    
    public static class Particle {
        public ParticleType type;
        public float x, y, z;
        public float velocityX, velocityY, velocityZ;
        public float age;
        public float lifespan;
        public float brightness;
        
        public Particle(ParticleType type, float x, float y, float z) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.z = z;
            this.lifespan = type.duration;
            this.age = 0;
            this.brightness = 1.0f;
            
            // Random velocity
            this.velocityX = (float) (Math.random() - 0.5f) * 0.2f;
            this.velocityY = (float) Math.random() * 0.3f;
            this.velocityZ = (float) (Math.random() - 0.5f) * 0.2f;
        }
        
        public void update(float deltaTime) {
            age += deltaTime;
            
            // Physics
            x += velocityX;
            y += velocityY;
            z += velocityZ;
            velocityY -= 0.01f;  // Gravity
            
            // Fade out
            brightness = 1.0f - (age / lifespan);
        }
        
        public boolean isAlive() {
            return age < lifespan;
        }
    }
    
    private List<Particle> particles;
    
    public ParticleSystem() {
        this.particles = new ArrayList<>();
    }
    
    public void emit(ParticleType type, float x, float y, float z, int count) {
        for (int i = 0; i < count; i++) {
            particles.add(new Particle(type, x, y, z));
        }
    }
    
    public void emitExplosion(float x, float y, float z) {
        emit(ParticleType.EXPLOSION, x, y, z, 30);
        emit(ParticleType.IMPACT_SPARK, x, y, z, 15);
        emit(ParticleType.SMOKE, x, y, z, 10);
    }
    
    public void emitMagic(ParticleType effect, float x, float y, float z) {
        emit(effect, x, y, z, 20);
    }
    
    public void update(float deltaTime) {
        particles.removeIf(p -> !p.isAlive());
        for (Particle p : particles) {
            p.update(deltaTime);
        }
    }
    
    public List<Particle> getActiveParticles() {
        return new ArrayList<>(particles);
    }
}
