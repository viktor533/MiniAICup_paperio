package main.plan;

import main.common.Utils;
import main.common.Coordinate;
import main.players.Direction;

import java.util.ArrayList;

public class Plan {
    private ArrayList<Coordinate> points;
    private int numberOfCurrentTarget = 0;
    private PlanType planType;

    public Plan(ArrayList<Coordinate> points, PlanType planType) {
        this.planType = planType;
        this.points = points;
        numberOfCurrentTarget = 0;
    }

    public Direction next(Coordinate position) {
        if (numberOfCurrentTarget == points.size()) {
            return null;
        }
        if (position.equals(points.get(numberOfCurrentTarget))) {
            numberOfCurrentTarget++;
        }
        return generateDirection(position, points.get(numberOfCurrentTarget));
    }

    private Direction generateDirection(Coordinate position, Coordinate target) {
        if (position.getY() < target.getY()) {
            return Direction.UP;
        }
        if (position.getY() > target.getY()) {
            return Direction.DOWN;
        }
        if (position.getX() < target.getX()) {
            return Direction.RIGHT;
        }
        if (position.getX() > target.getX()) {
            return Direction.LEFT;
        }
        return null;
    }

    public boolean isFinish() {
        return numberOfCurrentTarget == points.size();
    }

    public int distanceToFinish(Coordinate position) {
        int distance = Utils.pointDistance(position, points.get(numberOfCurrentTarget));
        for (int i = numberOfCurrentTarget; i < points.size()-1; i++) {
            distance += Utils.pointDistance(points.get(i), points.get(i+1));
        }
        return distance;
    }

    public PlanType getPlanType() {
        return planType;
    }
}
