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
        // UC9.2: Kiem tra Inventory co it nhat mot slot con so luong.
        for (InventorySlot slot : slots) {
            if (slot.hasStock()) {
                return true;
            }
        }
        return false;
    }

    public List<InventorySlot> getSlots() {
        // UC9.3.1: Tra ve danh sach slot de CommandView render tui do.
        return Collections.unmodifiableList(slots);
    }

    public InventorySlot getSlot(int index) {
        // UC9.8.1: Lay slot theo itemIndex duoc chon trong Bag UI.
        if (index < 0 || index >= slots.size()) {
            return null;
        }
        return slots.get(index);
    }

    public ItemUseResult useItem(int index, Pokemon target) {
        // UC9.8: Validate slot va yeu cau Item thuc thi effect len Pokemon target.
        InventorySlot slot = getSlot(index);
        if (slot == null) {
            return ItemUseResult.failure("Vat pham khong hop le!");
        }
        // UC9.8.2: Item phai con quantity moi duoc dung.
        if (!slot.hasStock()) {
            return ItemUseResult.failure("Da het " + slot.getItem().getName() + "!");
        }

        // UC9.9: Goi logic rieng cua item, vi moi loai item co effect khac nhau.
        ItemUseResult result = slot.getItem().use(target);
        if (result.isSuccess()) {
            // UC9.11: Chi tru item khi effect thanh cong.
            slot.consumeOne();
        }
        return result;
    }
}
