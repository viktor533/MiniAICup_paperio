package main.players;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static Direction fromString(String input) {
        for (Direction elem : Direction.values()) {
            if (elem.toString().equals(input)) {
                return elem;
            }
        }
        return null;
    }

    public boolean isHorizontal() {
        return (this.equals(LEFT) || this.equals(RIGHT));
    }

    public boolean isVertical() {
        return (this.equals(UP) || this.equals(DOWN));
    }

    public Direction reverce() {
        if (this.equals(UP)) {
            return DOWN;
        }
        if (this.equals(DOWN)) {
            return UP;
        }
        if (this.equals(LEFT)) {
            return RIGHT;
        }
        if (this.equals(RIGHT)) {
            return LEFT;
        }
        return this;
    }
}
