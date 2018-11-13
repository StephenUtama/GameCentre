package generalclasses;

import java.io.Serializable;
import java.util.HashMap;

public abstract class GameScoreboards implements Serializable{
    String game;
    private static HashMap<String, ScoreBoard> scoreboardsForGame = new HashMap<>();

    public static void addScoreboard(String difficulty, ScoreBoard scoreBoard) {
        scoreboardsForGame.put(difficulty, scoreBoard);
    }

    public static ScoreBoard getScoreboard(String complexity) {
        if (!scoreboardsForGame.keySet().contains(complexity)) {
            scoreboardsForGame.put(complexity, null);
        }
        return scoreboardsForGame.get(complexity);
    }
}
