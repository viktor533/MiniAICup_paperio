package main.bonus;

public enum BonusType {
    NITRO,
    SLOWDOWN,
    SAW;

    public static BonusType fromString(String input) {
        if ("n".equals(input)) {
            return NITRO;
        }
        if ("s".equals(input)) {
            return SLOWDOWN;
        }
        if ("saw".equals(input)) {
            return SAW;
        }
        return null;
    }
}
