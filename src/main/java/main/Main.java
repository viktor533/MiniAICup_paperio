package main;

import org.json.*;
import java.util.logging.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    private static final int outJsonStep = 4;
    private static String logPerfix = ">>>>>>>>> ";
    private static GameMap gameMap = new GameMap();

    public static void main(String[] args) {
        Logger log = Logger.getLogger("Main");
        try {
            FileHandler handler = new FileHandler("output.txt");
            handler.setFormatter(new SimpleFormatter());
            log.addHandler(handler);
            log.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("Can not set lggging handler!!!");
            System.err.println(e.getMessage());
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            line = in.readLine();
            JSONObject config = new JSONObject(line);
//            log.info(logPerfix + "Read first line!!");
//            log.info(config.toString(outJsonStep));
            GameMap.setConfig(config);
            while ((line = in.readLine()) != null && line.length() != 0) {
                JSONObject gameJson = new JSONObject(line);
//                log.info(logPerfix + "Read game line!!");
//                log.info(gameJson.toString(outJsonStep));

                if (gameJson.get("type").equals("tick")) {
                    JSONObject command = new JSONObject();
                    command.put("command", gameMap.onTick(gameJson).toString());
                    System.out.println(command.toString());

//                    log.info(logPerfix + "Generate command:");
                    log.info(command.toString());
                }
                if (gameJson.get("type").equals("end_game")) {
                    log.info(logPerfix + "Game over!!!");
                    return;
                }
            }
        }
        catch (IOException e) {
            System.err.println("Something bad happend!");
            System.err.println(e);
        }
    }
}