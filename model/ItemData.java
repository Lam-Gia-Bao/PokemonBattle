package model;

public class ItemData {
    private ItemData() {
    }

    public static Inventory createDefaultInventory() {
        Inventory inventory = new Inventory();
        inventory.addItem(new HealingItem("Potion", 20), 3);
        inventory.addItem(new HealingItem("Super Potion", 50), 2);
        inventory.addItem(new HealingItem("Hyper Potion", 120), 1);
        return inventory;
    }
}
