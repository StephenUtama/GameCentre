package fall2018.csc2017.pong;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import fall2018.csc2017.slidingtiles.SlidingTileScoreboards;
import generalclasses.GameScoreboards;
import generalclasses.SaverModel;
import generalclasses.User;

import static fall2018.csc2017.pong.PongStartingActivity.SAVE_FILENAME;

public class PongFileSaverModel {

    private Context context;
    private GameScoreboards scoreboards;
    private User user;

    public PongFileSaverModel(Context context) {
        this.context = context;
    }

    public GameScoreboards getScoreboards() {
        return scoreboards;
    }

    public User getUser() {
        return user;
    }

    public void saveButtonListener(PongGameInfo gameInfo, User user) {

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

    private void makeToastNothingToSave() {
        Toast.makeText(context, "Nothing to Save", Toast.LENGTH_SHORT).show();
    }

    private void makeToastSavedText() {
        Toast.makeText(context, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    public void updateAndSaveScoreboardIfGameOver(PongGameController controller) {

        if (controller.isOver()) {
            // Getting the info needed to display on scoreboard
            String username = controller.gameInfo.getPongGameInfo().getUserName();
            int score = controller.gameInfo.getPongGameInfo().getScore();
            String game = controller.gameInfo.getPongGameInfo().getGame();
            // assume we have loaded scoreboards and have the correct scoreboard
            loadScoreboards();
            if (scoreboards == null) {
                scoreboards = new PongGameScoreBoards();
            }
            PongScoreBoard scoreboard = (PongScoreBoard) scoreboards.getScoreboard(game);

            // Adding the score to the scoreboard
            if (scoreboard.getScoreMap().containsKey(username)) {
                // if user already has a score
                scoreboard.addScore(username, score);
            } else { // if user doesn't have a score
                scoreboard.addUserAndScore(username, score);
            }
            // save scoreboard
            scoreboards.addScoreboard(game, scoreboard);
            saveScoreboards(scoreboards);
        }
    }

    public void saveScoreboards(GameScoreboards scoreboards) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput("SAVED_PONG_SCOREBOARDS", context.MODE_PRIVATE));
            outputStream.writeObject(scoreboards);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

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

    public void loadScoreboards() {
        try {
            InputStream inputStream = context.openFileInput("SAVED_PONG_SCOREBOARDS");
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                scoreboards = (PongGameScoreBoards) input.readObject();
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
}