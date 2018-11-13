package generalclasses;

import java.io.Serializable;
import java.util.LinkedHashMap;

public abstract class ScoreBoard implements Serializable {

    /**
     * LinkedHashMap of scores where the key is the user and the value is his score(s).
     */
    LinkedHashMap scores = new LinkedHashMap();

    /**
     * @return A LinkedHashMap which has all the users and scores that completed the game.
     */
    public abstract LinkedHashMap getScoreMap();

    /**
     * Adds a user that is not in the list and his/her score
     *
     * @param user  the player's username
     * @param score the player's score
     */
    public abstract void addUserAndScore(String user, int score);

    /**
     * Add a score to a existing user.
     *
     * @param user  the player's username
     * @param score the player's score
     */
    public abstract void addScore(String user, int score);
}
