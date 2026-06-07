package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {
    private final List<InventorySlot> slots;

    public Inventory() {
        this.slots = new ArrayList<>();
    }

    public void addItem(Item item, int quantity) {
        if (item == null || quantity <= 0) {
            return;
        }

        for (InventorySlot slot : slots) {
            if (slot.getItem().getName().equals(item.getName())) {
                slot.addQuantity(quantity);
                return;
            }
        }

        slots.add(new InventorySlot(item, quantity));
    }

    public boolean hasUsableItems() {
        for (InventorySlot slot : slots) {
            if (slot.hasStock()) {
                return true;
            }
        }
        return false;
    }

    public List<InventorySlot> getSlots() {
        return Collections.unmodifiableList(slots);
    }

    public InventorySlot getSlot(int index) {
        if (index < 0 || index >= slots.size()) {
            return null;
        }
        return slots.get(index);
    }

    public ItemUseResult useItem(int index, Pokemon target) {
        InventorySlot slot = getSlot(index);
        if (slot == null) {
            return ItemUseResult.failure("Vat pham khong hop le!");
        }
        if (!slot.hasStock()) {
            return ItemUseResult.failure("Da het " + slot.getItem().getName() + "!");
        }

        ItemUseResult result = slot.getItem().use(target);
        if (result.isSuccess()) {
            slot.consumeOne();
        }
        return result;
    }
}
