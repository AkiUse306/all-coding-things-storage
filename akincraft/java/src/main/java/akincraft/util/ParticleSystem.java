package akincraft.util;

/**
 * Particle system for effects - breaking, explosion, smoke, etc
 */
public class ParticleSystem {
    private java.util.List<Particle> particles = new java.util.ArrayList<>();
    
    public static class Particle {
        public float x, y, z;
        public float vx, vy, vz;
        public float lifetime;
        public float maxLifetime;
        public int type; // 0=dust, 1=smoke, 2=flame
        
        public Particle(float x, float y, float z, float vx, float vy, float vz, float lifetime, int type) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
            this.lifetime = lifetime;
            this.maxLifetime = lifetime;
            this.type = type;
        }
    }
    
    public void emit(float x, float y, float z, int count, int type) {
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < count; i++) {
            float vx = (random.nextFloat() - 0.5f) * 4;
            float vy = random.nextFloat() * 6;
            float vz = (random.nextFloat() - 0.5f) * 4;
            particles.add(new Particle(x, y, z, vx, vy, vz, 1.0f, type));
        }
    }
    
    public void update(float deltaTime) {
        java.util.List<Particle> toRemove = new java.util.ArrayList<>();
        for (Particle p : particles) {
            p.x += p.vx * deltaTime;
            p.y += p.vy * deltaTime;
            p.z += p.vz * deltaTime;
            p.vy -= 9.81f * deltaTime;
            p.lifetime -= deltaTime;
            
            if (p.lifetime <= 0) {
                toRemove.add(p);
            }
        }
        particles.removeAll(toRemove);
    }
    
    public void render() {
        // Render all particles (would use billboarding in actual renderer)
    }
    
    public java.util.List<Particle> getParticles() {
        return particles;
    }
}
