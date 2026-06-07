package model;

public interface Item {
    String getName();

    String getDescription();

    boolean canUse(Pokemon target);

    ItemUseResult use(Pokemon target);
}
