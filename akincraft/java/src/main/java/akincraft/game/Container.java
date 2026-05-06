package akincraft.game;

import java.util.*;

/**
 * Container/chest for storing items
 */
public class Container {
    private List<Item> items;
    private int slotCount;
    private String name;
    
    public Container(String name, int slotCount) {
        this.name = name;
        this.slotCount = slotCount;
        this.items = new ArrayList<>(slotCount);
        for (int i = 0; i < slotCount; i++) {
            this.items.add(null);
        }
    }
    
    public boolean addItem(Item item) {
        // Try to stack first
        for (Item existingItem : items) {
            if (existingItem != null && existingItem.canStack(item)) {
                int spaceLeft = existingItem.getMaxStackSize() - existingItem.getCount();
                int toAdd = Math.min(spaceLeft, item.getCount());
                existingItem.addCount(toAdd);
                item.removeCount(toAdd);
                
                if (item.isEmpty()) {
                    return true;
                }
            }
        }
        
        // Find empty slot
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == null) {
                items.set(i, item);
                return true;
            }
        }
        
        return false;
    }
    
    public Item getItem(int slot) {
        if (slot >= 0 && slot < items.size()) {
            return items.get(slot);
        }
        return null;
    }
    
    public void setItem(int slot, Item item) {
        if (slot >= 0 && slot < items.size()) {
            items.set(slot, item);
        }
    }
    
    public void removeItem(int slot) {
        if (slot >= 0 && slot < items.size()) {
            items.set(slot, null);
        }
    }
    
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }
    
    public String getName() { return name; }
    public int getSlotCount() { return slotCount; }
}
