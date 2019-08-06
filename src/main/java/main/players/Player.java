package main.players;

import main.common.Coordinate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Player {
    public final String key;
    private int score;
    private Coordinate position;
    private Territory territory;
//    private ArrayList<Coordinate> territory;
    private ArrayList<Coordinate> lines;
    private Direction direction;
    private int updateTick;

    public Player(String key) {
        this.key = key;
        position = new Coordinate(0, 0);
        lines = new ArrayList<>();
        territory = new Territory();
//        territory = new ArrayList<>();
    }

    public void updatePlayer(JSONObject playerJson, int updateTick) {
        this.updateTick = updateTick;
        score = playerJson.getInt("score");
        position.updateCoordinate(playerJson.getJSONArray("position").getInt(0),
                        playerJson.getJSONArray("position").getInt(1));
        if (!playerJson.isNull("direction")) {
            direction = Direction.fromString(playerJson.getString("direction"));
        }
        updateLines(playerJson.getJSONArray("lines"));
        updateTerritory(playerJson.getJSONArray("territory"));
        updateBonuses(playerJson.getJSONArray("bonuses"));
    }

    private void updateLines(JSONArray jsonLines) {
        if (jsonLines.length() < lines.size()) {
            lines = new ArrayList<>();
        }
        for (int i = lines.size(); i < jsonLines.length(); i++) {
            lines.add(new Coordinate(jsonLines.getJSONArray(i).getInt(0),
                                     jsonLines.getJSONArray(i).getInt(1)));
        }
    }

    private void updateTerritory(JSONArray jsonTerritory) {
        for (int i = territory.size(); i < jsonTerritory.length(); i++) {
            territory.add(new Coordinate(jsonTerritory.getJSONArray(i).getInt(0),
                                         jsonTerritory.getJSONArray(i).getInt(1)));
        }
    }

    private void updateBonuses(JSONArray jsonBonuses) {
        // TODO: to be continued...
    }


    public int getScore() {
        return score;
    }

    public Coordinate getPosition() {
        return position;
    }

    public Territory getTerritory() {
        return territory;
    }

    public ArrayList<Coordinate> getLines() {
        return lines;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getUpdateTick() {
        return updateTick;
    }
}
