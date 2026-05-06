package akincraft.game;

public class Crafting {
    private final Inventory inventory;

    public Crafting(Inventory inventory) {
        this.inventory = inventory;
    }

    public void update(float delta) {
        // Simple placeholder system for crafting logic.
    }

    public boolean craftBridge() {
        // Example recipe:
        // 1 grass + 1 dirt -> 1 stone block
        inventory.addItem(3);
        return true;
    }
}
