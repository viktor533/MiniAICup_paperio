package main;

import main.bonus.BonusOnMap;
import main.common.Utils;
import main.common.Coordinate;
import main.plan.Plan;
import main.plan.PlanType;
import main.players.Direction;
import main.players.Me;
import main.players.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Logger;

public class GameMap {
    private Logger log = Logger.getLogger("Main");
    public static int X_CELLS_COUNT;
    public static int Y_CELLS_COUNT;
    public static int SPEED;
    public static int WIGHT;
    private boolean isFirstTick;

    private HashMap<String, Player> players;
    private ArrayList<BonusOnMap> bonuses;
    private Me me;
    private int tick_num;

    public static void setConfig(JSONObject config) {
        config = config.getJSONObject("params");
        GameMap.X_CELLS_COUNT = config.getInt("x_cells_count");
        GameMap.Y_CELLS_COUNT = config.getInt("y_cells_count");
        GameMap.SPEED = config.getInt("speed");
        GameMap.WIGHT = config.getInt("width");
    }

    public GameMap() {
        players = new HashMap<>();
        bonuses = new ArrayList<>();
        isFirstTick = true;
    }

    private void initPlayers(JSONObject gameJson) {
        JSONObject players =  gameJson.getJSONObject("players");
        Iterator<String> keyIterator = players.keys();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if ("i".equals(key)) {
                me = new Me(key);
            } else {
                players.put(key, new Player(key));
            }
        }
    }

    private void updatePlayers(JSONObject playersJson, int tickNum) {
        for (Player player : players.values()) {
            player.updatePlayer(playersJson.getJSONObject(player.key), tickNum);
        }
        me.updatePlayer(playersJson.getJSONObject(me.key), tickNum);

    }

    private void updateGame(JSONObject gameJson) {
        int tickNum = gameJson.getInt("tick_num");
        updatePlayers(gameJson.getJSONObject("players"), tickNum);
        updateBonusOnMap(gameJson.getJSONArray("bonuses"));
    }

    private void updateBonusOnMap(JSONArray bonuses) {

    }

    public Direction onTick(JSONObject gameJson) {
        gameJson = gameJson.getJSONObject("params");
        if (isFirstTick) {
            initPlayers(gameJson);
            isFirstTick = false;
        }
        updateGame(gameJson);

        return tryToDoSomething();
    }

    private Direction tryToDoSomething() {
        if (!me.isHavePlan()) {
            generateFirstPlan();
        } else {
            if (me.isFinishPlan()) {
                if (me.getCurrentPlan().getPlanType().equals(PlanType.TO_ATTACK)) {
                    if (!generatePlanToBound()) {
                        generateAttackPlan();
                    }
                }
                if (me.getCurrentPlan().getPlanType().equals(PlanType.TO_BOUND) ||
                     me.getCurrentPlan().getPlanType().equals(PlanType.FIRST_PLAN)) {
                    generateAttackPlan();
                }
            }
        }
        return me.getCurrentPlan().next(me.getPosition());
    }

    private static final int MIN_SIZE = 3;

    public boolean generateFirstPlan() {
        ArrayList<Coordinate> pointsToPlan = new ArrayList<>();
        pointsToPlan.add(me.getPosition().moveTo(1*WIGHT, Direction.UP));
        me.setCurrentPlan(new Plan(pointsToPlan, PlanType.FIRST_PLAN));
        return true;
    }

    private boolean generateAttackPlan() {
        int distToPlayers = Utils.minDistanceToPlayer(me, players.values());
        int size = Utils.min(distToPlayers/3, MIN_SIZE * WIGHT);
        ArrayList<Coordinate> pointsToPlan = new ArrayList<>();

        Coordinate target1 = me.getPosition().moveTo(size, me.getDirection());
        log.info(">>>>>>>>> generateAttackPlan: ");
        log.info(me.getDirection().toString());
        log.info(target1.toString());

        pointsToPlan.add(target1);
        Coordinate target2;
        int rnd = new Random().nextInt(2);
        if (me.getDirection().isHorizontal()) {
            if (rnd == 0) {
                target2 = target1.moveTo(size, Direction.UP);
            } else {
                target2 = target1.moveTo(size, Direction.DOWN);
            }
        } else {
            if (rnd == 0) {
                target2 = target1.moveTo(size, Direction.LEFT);
            } else {
                target2 = target1.moveTo(size, Direction.RIGHT);
            }
        }
        pointsToPlan.add(target2);

        pointsToPlan.add(Utils.nearestPointToBorder(target2, me.getTerritory()));
        me.setCurrentPlan(new Plan(pointsToPlan, PlanType.TO_ATTACK));
        return true;
    }

    private boolean generatePlanToBound() {
        log.info(">>>>>>>>> generatePlanToBound: ");

        ArrayList<Coordinate> pointsToPlan = new ArrayList<>();
        Coordinate nearestPoint = Utils.nearestBestPointToBorder(me.getPosition(), me.getTerritory());
        log.info(me.getPosition().toString());
        log.info(nearestPoint.toString());
        if (nearestPoint != null && !me.getPosition().equals(nearestPoint)) {
            pointsToPlan.add(nearestPoint);
            me.setCurrentPlan(new Plan(pointsToPlan, PlanType.TO_BOUND));
            return true;
        }
        return false;
    }


}
