package model;

public class HealingItem implements Item {
    private final String name;
    private final int healAmount;

    public HealingItem(String name, int healAmount) {
        this.name = name;
        this.healAmount = Math.max(1, healAmount);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Hoi " + healAmount + " HP";
    }

    public int getHealAmount() {
        return healAmount;
    }

    @Override
    public boolean canUse(Pokemon target) {
        // UC9.9.1: Healing item chi dung duoc cho Pokemon con song va chua day HP.
        return target != null && !target.isFainted() && target.getHp() < target.getMaxHp();
    }

    @Override
    public ItemUseResult use(Pokemon target) {
        // UC9.9: Thuc thi effect cua item hoi mau len Pokemon target.
        if (target == null) {
            return ItemUseResult.failure("Khong co Pokemon de dung vat pham!");
        }
        if (target.isFainted()) {
            return ItemUseResult.failure(target.getName() + " da guc, khong the dung " + name + "!");
        }
        if (target.getHp() >= target.getMaxHp()) {
            return ItemUseResult.failure(target.getName() + " da day HP!");
        }

        int beforeHp = target.getHp();
        // UC9.10: Goi Pokemon.heal de cap nhat HP va clamp theo max HP.
        target.heal(healAmount);
        int healed = target.getHp() - beforeHp;
        return ItemUseResult.success(name + " da hoi " + healed + " HP cho " + target.getName() + "!");
    }
}
