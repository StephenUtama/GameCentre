package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;

import generalclasses.GameScoreboards;

public class SlidingTilesFileSaverModel {

    private Context context;
    public SlidingTilesFileSaverModel(Context context) {
        this.context = context;
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
}
