package akincraft.entity;

/**
 * Original Akincraft Entity Types
 * Unique mobs and creatures specific to Akincraft
 */
public class EntityTypes {
    
    /**
     * Corrupted Spirit - Original mob that corrupts the environment
     * Spawns in dark caves, phases through blocks, emits toxic particles
     */
    public static class CorruptedSpirit extends Entity {
        private float phaseTimer = 0;
        private boolean isPhased = false;
        
        public CorruptedSpirit(float x, float y, float z) {
            super(x, y, z);
            this.health = 25;
            this.speed = 0.12f;
            this.size = 0.8f;
        }
        
        @Override
        public void update(float deltaTime) {
            phaseTimer += deltaTime;
            
            // Phase in/out every 3 seconds
            if (phaseTimer > 3.0f) {
                isPhased = !isPhased;
                phaseTimer = 0;
            }
            
            // Move toward player
            moveTowardTarget(0.15f);
            
            // Emit corruption particles
            if (!isPhased) {
                spawnCorruptionParticles(3);
            }
        }
        
        private void spawnCorruptionParticles(int count) {
            for (int i = 0; i < count; i++) {
                // Particles fade from purple to black
                // Implementation depends on particle system
            }
        }
        
        @Override
        public void onDeath() {
            // Drop essence of corruption (crafting ingredient)
            dropItem("essence_of_corruption", 1);
        }
    }
    
    /**
     * Crystal Golem - Original mob made of living crystal
     * Spawns in crystal caves, reflects projectiles, slowly regenerates
     */
    public static class CrystalGolem extends Entity {
        private float shineFactor = 0;
        private float regenerationCooldown = 0;
        
        public CrystalGolem(float x, float y, float z) {
            super(x, y, z);
            this.health = 50;  // High health
            this.speed = 0.08f;  // Slow
            this.size = 1.2f;
        }
        
        @Override
        public void update(float deltaTime) {
            regenerationCooldown -= deltaTime;
            shineFactor = (float) Math.sin(System.currentTimeMillis() / 500.0f) * 0.5f + 0.5f;
            
            // Slowly regenerate
            if (regenerationCooldown <= 0 && health < 50) {
                health += 0.5f;
                regenerationCooldown = 2.0f;
            }
            
            // Patrol slowly
            moveTowardTarget(0.08f);
        }
        
        @Override
        public void takeDamage(float damage) {
            // Reduce damage by reflecting/absorbing
            super.takeDamage(damage * 0.7f);
        }
        
        @Override
        public void onDeath() {
            // Drop crystal shards (valuable crafting material)
            dropItem("crystal_shard", 3 + (int)(Math.random() * 3));
        }
    }
    
    /**
     * Echo Knight - Original mob that moves through dimensions
     * Teleports around, duplicates briefly, strong melee damage
     */
    public static class EchoKnight extends Entity {
        private float teleportCooldown = 0;
        private int duplicateCount = 0;
        
        public EchoKnight(float x, float y, float z) {
            super(x, y, z);
            this.health = 40;
            this.speed = 0.15f;
            this.size = 1.0f;
            this.damage = 8;  // High damage
        }
        
        @Override
        public void update(float deltaTime) {
            teleportCooldown -= deltaTime;
            
            // Periodic teleportation
            if (teleportCooldown <= 0 && Math.random() > 0.95f) {
                teleportToRandomLocation();
                createEchoEffect();
                teleportCooldown = 3.0f;
            }
            
            // Chase player aggressively
            moveTowardTarget(0.2f);
            
            // Create echo duplicates when low health
            if (health < 20 && duplicateCount < 2) {
                createDuplicate();
                duplicateCount++;
            }
        }
        
        private void teleportToRandomLocation() {
            // Teleport within 10 blocks
            this.x += (Math.random() - 0.5f) * 20;
            this.z += (Math.random() - 0.5f) * 20;
        }
        
        private void createEchoEffect() {
            // Create afterimage/echo particle effect
        }
        
        private void createDuplicate() {
            // Spawn temporary duplicate that disappears after 5 seconds
        }
        
        @Override
        public void onDeath() {
            dropItem("void_shard", 2);
            dropItem("echo_essence", 1);
        }
    }
}
