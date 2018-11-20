package fall2018.csc2017.pong;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import generalclasses.GameScoreboards;
import generalclasses.ScoreBoard;

public class PongScoreBoard extends ScoreBoard implements Serializable {

    private LinkedHashMap<String, ArrayList<Integer>> scores = new LinkedHashMap<>();

    public LinkedHashMap<String, ArrayList<Integer>> getScoreMap() {
        return scores;
    }

    public void addUserAndScore(String user, int score) {
        ArrayList<Integer> scoreArray = new ArrayList<>();
        scoreArray.add(score);
        scores.put(user, scoreArray);

    }

    public void addScore(String user, int score) {
        scores.get(user).add(score);

    }
}
