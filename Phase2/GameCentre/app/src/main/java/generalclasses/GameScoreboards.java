package generalclasses;

import java.io.Serializable;
import java.util.HashMap;

public class GameScoreboards implements Serializable{
    private HashMap<String, ScoreBoard> scoreboardsForGame = new HashMap<>();

    public void addScoreboard(String difficulty, ScoreBoard scoreBoard) {
        scoreboardsForGame.put(difficulty, scoreBoard);
    }

    public ScoreBoard getScoreboard(String gameName) {
        if (!scoreboardsForGame.keySet().contains(gameName)) {
            scoreboardsForGame.put(gameName, null);
        }
        return scoreboardsForGame.get(gameName);
    }
}
