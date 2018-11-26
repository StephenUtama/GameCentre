package fall2018.csc2017.coldwar;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import fall2018.csc2017.slidingtiles.SlidingTilesGameInfo;
import generalclasses.SaverModel;
import generalclasses.User;

import static fall2018.csc2017.slidingtiles.StartingActivity.SAVE_FILENAME;

public class ColdWarSaverModel extends SaverModel {
    public ColdWarSaverModel(Context context) {
        super(context);
    }

    public void saveButtonListener(ColdWarGameInfo gameInfo, User user) {
        // get the current time
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new
                Date());
        user.addSave(gameInfo.getGame(), currentTime, gameInfo);
        saveToFile(SAVE_FILENAME);
        makeToastSavedText();
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