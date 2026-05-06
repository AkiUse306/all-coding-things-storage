package akincraft.entity;

import org.joml.Vector3f;

/**
 * Passive mobs: Pig, Cow, Sheep, Chicken - neutral, drop items on death
 */
class Pig extends Entity {
    private float wanderTimer = 0;
    private boolean inWater = false;
    
    public Pig(Vector3f position) {
        super(position, 0.9f, 0.9f, 10.0f);
    }
    
    @Override
    public void update(float deltaTime) {
        if (isDead) return;
        
        wanderTimer += deltaTime;
        if (wanderTimer > 3.0f) {
            wanderTimer = 0;
            float angle = (float) (Math.random() * Math.PI * 2);
            velocity.x = (float) Math.cos(angle) * 2;
            velocity.z = (float) Math.sin(angle) * 2;
        }
        
        move(deltaTime);
    }
    
    @Override
    public void render() {
        // TODO: render pig model
    }
}

/**
 * Cow mob - passive, drops beef and leather
 */
class Cow extends Entity {
    private float wanderTimer = 0;
    
    public Cow(Vector3f position) {
        super(position, 0.9f, 1.4f, 10.0f);
    }
    
    @Override
    public void update(float deltaTime) {
        if (isDead) return;
        
        wanderTimer += deltaTime;
        if (wanderTimer > 4.0f) {
            wanderTimer = 0;
            float angle = (float) (Math.random() * Math.PI * 2);
            velocity.x = (float) Math.cos(angle) * 2;
            velocity.z = (float) Math.sin(angle) * 2;
        }
        
        move(deltaTime);
    }
    
    @Override
    public void render() {
        // TODO: render cow model
    }
}

/**
 * Chicken mob - passive, drops raw chicken and feathers
 */
class Chicken extends Entity {
    public Chicken(Vector3f position) {
        super(position, 0.4f, 0.7f, 4.0f);
    }
    
    @Override
    public void update(float deltaTime) {
        if (isDead) return;
        
        // Chickens glide down, flap wings
        if (velocity.y < -1.0f) {
            velocity.y *= 0.95f; // Air resistance
        }
        
        move(deltaTime);
    }
    
    @Override
    public void render() {
        // TODO: render chicken model
    }
}

/**
 * Skeleton mob - hostile, shoots arrows
 */
class Skeleton extends Entity {
    private float bowDrawTime = 0;
    private float shootCooldown = 0;
    
    public Skeleton(Vector3f position) {
        super(position, 0.6f, 1.99f, 20.0f);
    }
    
    @Override
    public void update(float deltaTime) {
        if (isDead) return;
        
        bowDrawTime += deltaTime;
        shootCooldown -= deltaTime;
        
        move(deltaTime);
    }
    
    @Override
    public void render() {
        // TODO: render skeleton with bow
    }
    
    public void shoot() {
        if (shootCooldown <= 0) {
            // TODO: create arrow entity
            shootCooldown = 1.0f;
        }
    }
}
