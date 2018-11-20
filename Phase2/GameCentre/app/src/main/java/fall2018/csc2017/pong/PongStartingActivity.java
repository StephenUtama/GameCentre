package fall2018.csc2017.pong;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import fall2018.csc2017.slidingtiles.GameActivity;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SlidingTilesGameInfo;
import fall2018.csc2017.slidingtiles.StartingActivity;
import generalactivities.GameSelectionActivity;
import generalactivities.MenuActivity;
import generalclasses.GameInfo;
import generalclasses.GameScoreboards;
import generalclasses.User;

public class PongStartingActivity extends AppCompatActivity {

    private Button pongGame;
    private Button pongScore;
    private Button pongLoad;
    private String username;
    private User user;
    public static final String SAVE_FILENAME = "master_save_file.ser";
    public static boolean playSaving = false;
    //GameScoreboards scoreboards = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pong_main_menu);
        declarations();
        addPongGameButtonListener();
        user = User.usernameToUser.get(username);
        addPongLoadButtonListener();
        addPongScoreButtonListener();
    }

    private void declarations(){
        pongGame = findViewById(R.id.new_pong);
        pongScore = findViewById(R.id.pong_score);
        pongLoad = findViewById(R.id.pong_load);
        username = getIntent().getStringExtra("username");
    }

    private void addPongGameButtonListener() {
        pongGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PongStartingActivity.this, PongMainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }

    private void addPongScoreButtonListener() {
        pongScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PongStartingActivity.this, PongScoreBoardActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    /**
     * Get all the save names that have a given complexity.
     *
     * @param saves  the hash map of all the existing saves
     * @return a string array of save names that have complexity complexity
     */
    private String[] getSaveNames(HashMap<String, Object> saves) {
        ArrayList<String> tempResult = new ArrayList<>();
        for (String saveName : saves.keySet()) {
            tempResult.add(saveName);
            }
        return tempResult.toArray(new String[tempResult.size()]);
    }

    private void addPongLoadButtonListener() {
        pongLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(PongStartingActivity.this);
                alert.setTitle("Select your save");
                alert.setIcon(android.R.drawable.ic_dialog_alert);

                final HashMap<String, Object> saves = user.getSavesForGame("Pong");
                final String[] saveNames = getSaveNames(saves);
                alert.setItems(saveNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        loadSaveAndBegin(saves, saveNames, i);
                    }
                });
                alert.show();
            }
        });
    }

    /**
     * Get the save that corresponds to index i in saveNamesWithCorrectComplexity, and load that
     * save to begin GameActivity.
     *
     * @param saves                          the hash map that details all the user's saves.
     * @param saveNames string list of all save names with correct complexity.
     * @param i                              the index used to get the desired save in
     *                                       saveNamesWithCorrectComplexity.
     */
    private void loadSaveAndBegin(HashMap<String, Object> saves,String[] saveNames ,int i) {
        String saveName = saveNames[i];

        // get the corresponding save
        PongGameInfo saveToLoad = (PongGameInfo) saves.get(saveName);

        Intent intent = new Intent(PongStartingActivity.this, PongMainActivity.class);
        intent.putExtra("saveToLoad", saveToLoad);
        intent.putExtra("username", username);
        playSaving = true;
        startActivity(intent);
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // load most updated copy of user
        try {
            InputStream inputStream = this.openFileInput(SAVE_FILENAME);
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

    // TODO
    /**
     * 1. Pause button and resume from there /Done
     * 2. Once the game is over, prompt the user with text field
     * 3. Save the game when it is over /Doing
     * 4. Add the save button to the paused menu /Done
     * 5. Save that game state into a file when save is clicked /Done
     * 6. Connect scoreboard to saved file and user /Doing
     * 7. Add extra functions to pong game (eg. sound and power up items)
     */
}
