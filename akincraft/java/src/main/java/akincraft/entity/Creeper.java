package akincraft.entity;

import org.joml.Vector3f;

/**
 * Creeper mob - explodes when near player
 */
public class Creeper extends Entity {
    private float explosionTimer = -1;
    private static final float EXPLOSION_DELAY = 1.5f;
    private static final float EXPLOSION_RADIUS = 16.0f;
    private static final float EXPLOSION_DAMAGE = 49.0f;
    private float detectionRange = 16.0f;
    private Vector3f targetPosition = null;
    
    public Creeper(Vector3f position) {
        super(position, 0.6f, 1.7f, 20.0f);
    }
    
    @Override
    public void update(float deltaTime) {
        if (isDead) return;
        
        if (explosionTimer >= 0) {
            explosionTimer -= deltaTime;
            if (explosionTimer <= 0) {
                detonate();
            }
        }
        
        move(deltaTime);
    }
    
    @Override
    public void render() {
        // TODO: render creeper model with fuse animation if exploding
    }
    
    public void startExplosion() {
        if (explosionTimer < 0) {
            explosionTimer = EXPLOSION_DELAY;
        }
    }
    
    private void detonate() {
        // TODO: create explosion entity, apply damage to nearby players/mobs
        isDead = true;
    }
    
    public boolean isExploding() {
        return explosionTimer >= 0;
    }
    
    public float getExplosionProgress() {
        return 1.0f - (explosionTimer / EXPLOSION_DELAY);
    }
}
