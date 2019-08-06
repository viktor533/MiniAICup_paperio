package main.players;

import main.GameMap;
import main.common.Coordinate;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;

public class Territory implements Iterable<Coordinate>{
    private ArrayList<Coordinate> territoryPoints;
    private int size;
    private EnumMap<Direction, Integer> bounds;

    public Territory() {
        territoryPoints = new ArrayList<>();
        bounds = new EnumMap<>(Direction.class);
        size = 0;
    }

    public int size() {
        return size;
    }

    public void add(Coordinate point) {
        territoryPoints.add(point);
        size++;
        if (size == 1) {
            bounds.put(Direction.LEFT, point.getX());
            bounds.put(Direction.RIGHT, point.getX());
            bounds.put(Direction.UP, point.getY());
            bounds.put(Direction.DOWN, point.getY());
        } else {
            if (point.getX() < bounds.get(Direction.LEFT)) {
                bounds.put(Direction.LEFT, point.getX());
            }
            if (point.getX() > bounds.get(Direction.RIGHT)) {
                bounds.put(Direction.RIGHT, point.getX());
            }
            if (point.getY() > bounds.get(Direction.UP)) {
                bounds.put(Direction.UP, point.getY());
            }
            if (point.getY() < bounds.get(Direction.DOWN)) {
                bounds.put(Direction.DOWN, point.getY());
            }
        }
    }

    public int getBound(Direction direction) {
        return bounds.get(direction);
    }

    public int getDistFromBoundToBorder(Direction direction) {
        if (direction.equals(Direction.DOWN) || direction.equals(Direction.LEFT) ) {
            return bounds.get(direction);
        }
        if (direction.equals(Direction.RIGHT)) {
            return GameMap.X_CELLS_COUNT - bounds.get(direction);
        }
        if (direction.equals(Direction.UP)) {
            return GameMap.Y_CELLS_COUNT - bounds.get(direction);
        }
        return 0;
    }

    @Override
    public Iterator<Coordinate> iterator() {
        return territoryPoints.iterator();
    }
}
