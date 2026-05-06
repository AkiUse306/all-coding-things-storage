package akincraft.game;

import org.joml.Vector3f;

/**
 * Represents a single item in inventory or world
 */
public class Item {
    private String id;
    private String name;
    private int count;
    private int maxStackSize;
    
    public Item(String id, String name, int count, int maxStackSize) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.maxStackSize = maxStackSize;
    }
    
    public void addCount(int amount) {
        this.count = Math.min(this.count + amount, maxStackSize);
    }
    
    public void removeCount(int amount) {
        this.count = Math.max(0, this.count - amount);
    }
    
    public boolean canStack(Item other) {
        return this.id.equals(other.id) && this.count < this.maxStackSize;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public int getCount() { return count; }
    public int getMaxStackSize() { return maxStackSize; }
    public boolean isEmpty() { return count <= 0; }
}
