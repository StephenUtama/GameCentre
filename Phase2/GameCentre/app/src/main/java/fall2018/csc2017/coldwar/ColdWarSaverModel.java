package fall2018.csc2017.coldwar;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

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
}