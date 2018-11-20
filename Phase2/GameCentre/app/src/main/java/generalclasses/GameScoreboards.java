package generalclasses;

import java.io.Serializable;
import java.util.HashMap;

public abstract class GameScoreboards implements Serializable{
//    String game;
    private HashMap<String, ScoreBoard> scoreboardsForGame = new HashMap<>();

    public void addScoreboard(String gameName, ScoreBoard scoreBoard) {
        scoreboardsForGame.put(gameName, scoreBoard);
    }

    public ScoreBoard getScoreboard(String gameName) {
        if (!scoreboardsForGame.keySet().contains(gameName)) {
            scoreboardsForGame.put(gameName, null);
        }
        return scoreboardsForGame.get(gameName);
    }
}
