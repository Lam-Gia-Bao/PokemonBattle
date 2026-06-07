package model;

public class InventorySlot {
    private final Item item;
    private int quantity;

    public InventorySlot(Item item, int quantity) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        this.item = item;
        this.quantity = Math.max(0, quantity);
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean hasStock() {
        // UC9.2.1 / UC9.8.2: Slot con so luong thi item moi kha dung.
        return quantity > 0;
    }

    public void addQuantity(int amount) {
        if (amount > 0) {
            quantity += amount;
        }
    }

    public boolean consumeOne() {
        // UC9.11: Tru 1 item sau khi su dung thanh cong.
        if (quantity <= 0) {
            return false;
        }
        quantity--;
        return true;
    }
}
