package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//
import fall2018.csc2017.pong.PongGameInfo;
import generalclasses.GameInfo;
import generalclasses.GameScoreboards;
import generalclasses.User;

import static fall2018.csc2017.slidingtiles.StartingActivity.SAVE_FILENAME;

/**
 * The Model for Sliding Tiles.
 */
public class SlidingTilesFileSaverModel {

    private Context context;
    private GameScoreboards scoreboards;
    private User user;

    public GameScoreboards getScoreboards() {
        return scoreboards;
    }

    public User getUser() {
        return user;
    }

    public SlidingTilesFileSaverModel(Context context) {
        this.context = context;
    }


    /**
     * Save the current hash map with each user's saves to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, context.MODE_PRIVATE));
            outputStream.writeObject(User.usernameToUser);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void saveButtonListener(SlidingTilesGameInfo gameInfo, User user) {

        if (gameInfo.getScore() == 0) {
            makeToastNothingToSave(); // if the player hasn't played yet
        } else {
            // get the current time
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new
                    Date());
            user.addSave(gameInfo.getGame(), currentTime, gameInfo);
            saveToFile(SAVE_FILENAME);
            makeToastSavedText();
        }
    }


    public void updateAndSaveScoreboardIfGameOver(SlidingTilesManager slidingTilesManager) {

        if (slidingTilesManager.isOver()) {
            // Getting the info needed to display on scoreboard
            String username = slidingTilesManager.getInfo().getUserName();
            int score = slidingTilesManager.getInfo().getScore();
            String complexity = slidingTilesManager.getInfo().getComplexity();
            String game = slidingTilesManager.getInfo().getGame();

            // assume we have loaded scoreboards and have the correct scoreboard
            loadScoreboards();
            SlidingTilesScoreBoard scoreboard = (SlidingTilesScoreBoard) scoreboards.getScoreboard(complexity);
//            if (scoreboard == null) {
//                scoreboard = new Sli
//            }
//
            // Adding the score to the scoreboard
            if (scoreboard.getScoreMap().containsKey(username)) {
                // if user already has a score
                scoreboard.addScore(username, score);
            } else { // if user doesn't have a score
                scoreboard.addUserAndScore(username, score);
            }
            // save scoreboard
            scoreboards.addScoreboard(complexity, scoreboard);
            saveScoreboards(scoreboards);
        }
    }


    public void saveScoreboards(GameScoreboards scoreboards) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput("SAVED_SCOREBOARDS", context.MODE_PRIVATE));
            outputStream.writeObject(scoreboards);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    /**
     * Display that there was nothing to save.
     */
    private void makeToastNothingToSave() {
        Toast.makeText(context, "Nothing to Save", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(context, "Game Saved", Toast.LENGTH_SHORT).show();
    }
//
    public void instantiateGameandBegin(String complexity) {
        loadScoreboards();
        if (scoreboards == null) {
            scoreboards = new SlidingTileScoreboards();
        }
        if (scoreboards.getScoreboard(complexity) == null) { // if no one has won a game
//            SlidingTileScoreboards newBoards = new SlidingTileScoreboards();
            scoreboards.addScoreboard(complexity, new SlidingTilesScoreBoard());
            saveScoreboards(scoreboards);
            // in subsequent games, however, there is no need for this
        }
    }
    public void loadScoreboards() {
        try {
            InputStream inputStream = context.openFileInput("SAVED_SCOREBOARDS");
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                scoreboards = (SlidingTileScoreboards) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    public String[] getSaveNamesComplexity(String complexity, HashMap<String, GameInfo> saves) {
        ArrayList<String> tempResult = new ArrayList<>();
        for (String saveName : saves.keySet()) {
            SlidingTilesGameInfo info = (SlidingTilesGameInfo) saves.get(saveName);
            if (complexity.equals(info.getComplexity())) {
                tempResult.add(saveName);
            }
        }
        return tempResult.toArray(new String[tempResult.size()]);
    }

    public void startingResume(String username) {
        try {
            InputStream inputStream = context.openFileInput(SAVE_FILENAME);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                User.usernameToUser = (HashMap<String, User>) input.readObject();
                user = User.usernameToUser.get(username);
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: "
                    + e.toString());
        }
    }
}
