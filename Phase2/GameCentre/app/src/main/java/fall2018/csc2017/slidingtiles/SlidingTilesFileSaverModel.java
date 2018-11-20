package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import generalclasses.GameScoreboards;
import generalclasses.User;

import static fall2018.csc2017.slidingtiles.StartingActivity.SAVE_FILENAME;

public class SlidingTilesFileSaverModel {

    private Context context;
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


    public void updateAndSaveScoreboardIfGameOver() {

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
}
