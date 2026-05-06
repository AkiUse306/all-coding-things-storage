package akincraft.game;

public class Inventory {
    private final int[] slots = new int[16];

    public Inventory() {
        slots[0] = 1; // grass block
        slots[1] = 2; // dirt block
    }

    public int getSlot(int index) {
        return slots[index];
    }

    public void addItem(int itemId) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == 0 || slots[i] == itemId) {
                slots[i] = itemId;
                return;
            }
        }
    }

    public void addItem(int itemId, int count) {
        if (count <= 0) {
            return;
        }
        for (int i = 0; i < count; i++) {
            addItem(itemId);
        }
    }

    public int[] getSlots() {
        return slots;
    }

    public void update(float delta) {
        // placeholder for inventory updates or cooldowns
    }
}
