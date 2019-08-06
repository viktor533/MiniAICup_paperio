package main.common;

import main.GameMap;
import main.players.Direction;
import main.players.Me;
import main.players.Player;
import main.players.Territory;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Utils {
    public static final int BAD_DIST = 4* GameMap.WIGHT;

    public static int max(int a, int b) {
        if (a >= b) {
            return a;
        }
        return b;
    }

    public static int min(int a, int b) {
        if (a <= b) {
            return a;
        }
        return b;
    }

    public static int mod(int a) {
        if (a >= 0) {
            return a;
        }
        return -a;
    }

    public static int playerDistance(Player playerOne, Player playerTwo) {
        return pointDistance(playerOne.getPosition(), playerTwo.getPosition());
    }

    public static int pointDistance(Coordinate pointOne, Coordinate pointTwo) {
        return mod(pointOne.getX() - pointTwo.getX()) +
                mod(pointOne.getY() - pointTwo.getY());

    }

    public static Direction pointDirection(Coordinate pointFrom, Coordinate pointTo) {
        if (mod(pointFrom.getX() - pointTo.getX()) >
            mod(pointFrom.getY() - pointTo.getY())) {
            if (pointFrom.getX() < pointTo.getX())  {
                return Direction.RIGHT;
            } else  {
                return Direction.LEFT;
            }
        } else {
            if (pointFrom.getY() < pointTo.getY()) {
                return Direction.UP;
            } else {
                return Direction.DOWN;
            }
        }
    }

    public static Coordinate nearestBestPointToBorder(Coordinate position, Territory territory) {
        for (Direction direction : Direction.values()) {
            if (territory.getDistFromBoundToBorder(direction) < BAD_DIST) {
                continue;
            } else {
                return findBoundByDirection(position, territory, direction);
            }
        }
        return nearestPointToBorder(position, territory);
    }

    public static Coordinate findBoundByDirection(Coordinate position, Territory territory, Direction direction) {
        int minDistance = Integer.MAX_VALUE;
        Coordinate minPoint = null;
        for (Coordinate point : territory) {
            if (pointDirection(position, point).equals(direction)) {
                int distanceToPoint = pointDistance(position, point);
                if (distanceToPoint < minDistance) {
                    minDistance = distanceToPoint;
                    minPoint = point;
                }
            }
        }
        return minPoint;
    }

    public static Coordinate nearestPointToBorder(Coordinate position, Territory territory) {
        int minDistance = Integer.MAX_VALUE;
        Coordinate minPoint=null;
        for (Coordinate point : territory) {
            int distanceToPoint = pointDistance(position, point);
            if (distanceToPoint < minDistance) {
                minDistance = distanceToPoint;
                minPoint = point;
            }
        }
        return minPoint;
    }

    public static int minDistanceToPlayer(Me me, Collection<Player> players) {
        int minDistance = Integer.MAX_VALUE;
        for (Player player : players) {
            int distanceToPlayer = playerDistance(me, player);
            if (distanceToPlayer < minDistance) {
                minDistance = distanceToPlayer;
            }
        }
        return minDistance;
    }
}
