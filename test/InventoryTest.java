import model.HealingItem;
import model.Inventory;
import model.InventorySlot;
import model.ItemData;
import model.ItemUseResult;
import model.Pokemon;
import model.PokemonType;

public class InventoryTest {
    public static void main(String[] args) {
        TestCase[] tests = {
            new TestCase("Add item va merge quantity theo ten", InventoryTest::shouldAddItemAndMergeQuantityByName),
            new TestCase("Dung healing item thanh cong va tru quantity", InventoryTest::shouldUseHealingItemAndConsumeOneQuantity),
            new TestCase("Healing khong vuot qua max HP", InventoryTest::shouldNotHealAboveMaxHp),
            new TestCase("Khong consume item khi Pokemon day HP", InventoryTest::shouldNotConsumeItemWhenPokemonIsAlreadyFullHp),
            new TestCase("Khong consume item khi Pokemon da fainted", InventoryTest::shouldNotConsumeItemWhenPokemonIsFainted),
            new TestCase("Reject item index khong hop le", InventoryTest::shouldRejectInvalidItemIndex),
            new TestCase("Tao default inventory dung du lieu", InventoryTest::shouldCreateDefaultInventory)
        };

        int passed = 0;
        int failed = 0;

        System.out.println("InventoryTest result:");
        for (TestCase test : tests) {
            try {
                test.run();
                passed++;
                System.out.println("[PASS] " + test.name);
            } catch (Throwable error) {
                failed++;
                System.out.println("[FAIL] " + test.name);
                System.out.println("       " + error.getMessage());
            }
        }

        System.out.println("Summary: " + passed + " passed, " + failed + " failed, " + tests.length + " total.");
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static class TestCase {
        private final String name;
        private final Runnable test;

        private TestCase(String name, Runnable test) {
            this.name = name;
            this.test = test;
        }

        private void run() {
            test.run();
        }
    }

    private static void shouldAddItemAndMergeQuantityByName() {
        Inventory inventory = new Inventory();

        inventory.addItem(new HealingItem("Potion", 20), 2);
        inventory.addItem(new HealingItem("Potion", 20), 3);

        assertEquals(1, inventory.getSlots().size(), "Same item name should share one slot");
        assertEquals(5, inventory.getSlot(0).getQuantity(), "Quantity should be merged");
        assertTrue(inventory.hasUsableItems(), "Inventory should report usable items");
    }

    private static void shouldUseHealingItemAndConsumeOneQuantity() {
        Inventory inventory = new Inventory();
        inventory.addItem(new HealingItem("Potion", 20), 2);
        Pokemon pokemon = damagedPokemon(40);

        ItemUseResult result = inventory.useItem(0, pokemon);

        assertTrue(result.isSuccess(), "Potion should be usable on damaged Pokemon");
        assertEquals(80, pokemon.getHp(), "Potion should restore 20 HP");
        assertEquals(1, inventory.getSlot(0).getQuantity(), "Successful use should consume one item");
    }

    private static void shouldNotHealAboveMaxHp() {
        Inventory inventory = new Inventory();
        inventory.addItem(new HealingItem("Super Potion", 50), 1);
        Pokemon pokemon = damagedPokemon(10);

        ItemUseResult result = inventory.useItem(0, pokemon);

        assertTrue(result.isSuccess(), "Healing item should still succeed when partially healing");
        assertEquals(100, pokemon.getHp(), "Healing should stop at max HP");
        assertEquals(0, inventory.getSlot(0).getQuantity(), "Successful use should consume item");
        assertFalse(inventory.hasUsableItems(), "Inventory should be empty after last item is consumed");
    }

    private static void shouldNotConsumeItemWhenPokemonIsAlreadyFullHp() {
        Inventory inventory = new Inventory();
        inventory.addItem(new HealingItem("Potion", 20), 1);
        Pokemon pokemon = fullHpPokemon();

        ItemUseResult result = inventory.useItem(0, pokemon);

        assertFalse(result.isSuccess(), "Potion should fail on full HP Pokemon");
        assertEquals(100, pokemon.getHp(), "Full HP Pokemon should stay unchanged");
        assertEquals(1, inventory.getSlot(0).getQuantity(), "Failed use should not consume item");
    }

    private static void shouldNotConsumeItemWhenPokemonIsFainted() {
        Inventory inventory = new Inventory();
        inventory.addItem(new HealingItem("Potion", 20), 1);
        Pokemon pokemon = fullHpPokemon();
        pokemon.receiveDmg(100);

        ItemUseResult result = inventory.useItem(0, pokemon);

        assertFalse(result.isSuccess(), "Healing item should fail on fainted Pokemon");
        assertEquals(0, pokemon.getHp(), "Fainted Pokemon should stay at 0 HP");
        assertEquals(1, inventory.getSlot(0).getQuantity(), "Failed use should not consume item");
    }

    private static void shouldRejectInvalidItemIndex() {
        Inventory inventory = new Inventory();
        Pokemon pokemon = damagedPokemon(40);

        ItemUseResult result = inventory.useItem(0, pokemon);

        assertFalse(result.isSuccess(), "Invalid item index should fail");
        assertEquals(60, pokemon.getHp(), "Invalid item index should not change Pokemon HP");
    }

    private static void shouldCreateDefaultInventory() {
        Inventory inventory = ItemData.createDefaultInventory();

        assertEquals(3, inventory.getSlots().size(), "Default inventory should include three item types");
        assertSlot(inventory.getSlot(0), "Potion", 3);
        assertSlot(inventory.getSlot(1), "Super Potion", 2);
        assertSlot(inventory.getSlot(2), "Hyper Potion", 1);
    }

    private static Pokemon damagedPokemon(int damage) {
        Pokemon pokemon = fullHpPokemon();
        pokemon.receiveDmg(damage);
        return pokemon;
    }

    private static Pokemon fullHpPokemon() {
        return new Pokemon("Testmon", PokemonType.NORMAL, 50, 50, 50, 100);
    }

    private static void assertSlot(InventorySlot slot, String expectedName, int expectedQuantity) {
        assertEquals(expectedName, slot.getItem().getName(), "Unexpected slot item name");
        assertEquals(expectedQuantity, slot.getQuantity(), "Unexpected slot quantity");
    }

    private static void assertTrue(boolean value, String message) {
        if (!value) {
            throw new AssertionError(message);
        }
    }

    private static void assertFalse(boolean value, String message) {
        if (value) {
            throw new AssertionError(message);
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " Expected: " + expected + ", actual: " + actual);
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + " Expected: " + expected + ", actual: " + actual);
        }
    }
}
