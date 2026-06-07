package model;

public class ItemUseResult {
    private final boolean success;
    private final String message;

    private ItemUseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static ItemUseResult success(String message) {
        return new ItemUseResult(true, message);
    }

    public static ItemUseResult failure(String message) {
        return new ItemUseResult(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
