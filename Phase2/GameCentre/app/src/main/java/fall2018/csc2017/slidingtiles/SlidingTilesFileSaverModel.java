package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
//

import generalclasses.GameInfo;
import generalclasses.GameScoreboards;
import generalclasses.SaverModel;
import generalclasses.User;

import static fall2018.csc2017.slidingtiles.StartingActivity.SAVE_FILENAME;

//Model class for Slidingtiles GameActivity, not tested
/**
 * The Model for Sliding Tiles.
 */
public class SlidingTilesFileSaverModel extends SaverModel {

    public GameScoreboards getScoreboards() {
        loadScoreboards("SAVED_SCOREBOARDS");
        return scoreboards;
    }


    public SlidingTilesFileSaverModel(Context context) {
        super(context);
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
