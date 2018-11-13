package generalclasses;

import java.io.Serializable;
import java.util.HashMap;

public abstract class GameScoreboards implements Serializable{
//    String game;
    private HashMap<String, ScoreBoard> scoreboardsForGame = new HashMap<>();

    public void addScoreboard(String difficulty, ScoreBoard scoreBoard) {
        scoreboardsForGame.put(difficulty, scoreBoard);
    }

    public ScoreBoard getScoreboard(String complexity) {
        if (!scoreboardsForGame.keySet().contains(complexity)) {
            scoreboardsForGame.put(complexity, null);
        }
        return scoreboardsForGame.get(complexity);
    }
}
