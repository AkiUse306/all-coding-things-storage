package akincraft.entity;

import org.joml.Vector3f;

/**
 * Zombie mob - hostile, spawns at night
 */
public class Zombie extends Entity {
    private float attackTimer = 0;
    private float attackCooldown = 1.5f;
    private float detectionRange = 32.0f;
    private float attackRange = 1.5f;
    private Vector3f targetPosition = null;
    private float wanderTimer = 0;
    
    public Zombie(Vector3f position) {
        super(position, 0.6f, 1.95f, 20.0f);
        this.velocity.y = 0;
    }
    
    @Override
    public void update(float deltaTime) {
        if (isDead) return;
        
        // Simple AI: wander or chase player
        wanderTimer += deltaTime;
        attackTimer += deltaTime;
        
        // Random wandering
        if (wanderTimer > 5.0f) {
            wanderTimer = 0;
            float angle = (float) (Math.random() * Math.PI * 2);
            velocity.x = (float) Math.cos(angle) * 3;
            velocity.z = (float) Math.sin(angle) * 3;
        }
        
        move(deltaTime);
    }
    
    @Override
    public void render() {
        // TODO: render zombie model
    }
    
    public void attackPlayer(float playerDamage) {
        if (attackTimer >= attackCooldown) {
            attackTimer = 0;
            // Deal damage to player
        }
    }
    
    public boolean isNear(Vector3f otherPos) {
        return position.distance(otherPos) < attackRange;
    }
}
