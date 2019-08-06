package main.common;

import main.players.Direction;

import java.util.Objects;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(int x, int y) {
        return (this.x == x && this.y == y);
    }

    public void updateCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate moveTo(int length, Direction direction) {
        if (Direction.DOWN.equals(direction)) {
            return new Coordinate(getX(), getY() - length);
        }
        if (Direction.UP.equals(direction)) {
            return new Coordinate(getX(), getY() + length);
        }
        if (Direction.LEFT.equals(direction)) {
            return new Coordinate(getX() - length, getY());
        }
        if (Direction.RIGHT.equals(direction)) {
            return new Coordinate(getX() + length, getY());
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
