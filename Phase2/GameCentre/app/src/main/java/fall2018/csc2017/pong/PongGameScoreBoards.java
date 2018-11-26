package fall2018.csc2017.pong;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

import generalclasses.GameScoreboards;
import generalclasses.ScoreBoard;

public class PongGameScoreBoards extends GameScoreboards implements Serializable {
    private HashMap<String, ScoreBoard> scoreboardsForGame = new HashMap<>();
    public String game;
    PongGameScoreBoards() {
        this.game = "Pong";
    }

    public ScoreBoard getScoreboard(String gameName) {
        if (!scoreboardsForGame.keySet().contains(gameName)) {
            scoreboardsForGame.put(gameName, new ScoreBoard());
        }
        return scoreboardsForGame.get(gameName);
    }
}
