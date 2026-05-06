package akincraft.entity;

import org.joml.Vector3f;

/**
 * Base entity class for all entities in the world
 */
public abstract class Entity {
    protected Vector3f position;
    protected Vector3f velocity;
    protected Vector3f rotation;
    protected float width;
    protected float height;
    protected float health;
    protected float maxHealth;
    protected boolean isDead;
    protected float despawnTimer;
    
    public Entity(Vector3f position, float width, float height, float maxHealth) {
        this.position = new Vector3f(position);
        this.velocity = new Vector3f();
        this.rotation = new Vector3f();
        this.width = width;
        this.height = height;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.isDead = false;
        this.despawnTimer = 0;
    }
    
    public abstract void update(float deltaTime);
    public abstract void render();
    
    public void move(float deltaTime) {
        position.add(velocity.x * deltaTime, velocity.y * deltaTime, velocity.z * deltaTime);
        // Apply gravity
        velocity.y -= 9.81f * deltaTime;
    }
    
    public void damage(float amount) {
        health -= amount;
        if (health <= 0) {
            isDead = true;
            despawnTimer = 300; // 5 minutes in seconds
        }
    }
    
    public void heal(float amount) {
        health = Math.min(health + amount, maxHealth);
    }
    
    public Vector3f getPosition() { return position; }
    public Vector3f getVelocity() { return velocity; }
    public float getHealth() { return health; }
    public boolean isDead() { return isDead; }
}
