package fall2018.csc2017.pong;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

import fall2018.csc2017.slidingtiles.GameActivity;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SlidingTilesGameInfo;
import fall2018.csc2017.slidingtiles.StartingActivity;
import generalactivities.GameSelectionActivity;
import generalactivities.MenuActivity;
import generalclasses.GameInfo;
import generalclasses.User;

public class PongStartingActivity extends AppCompatActivity {

    private Button pongGame;
    private Button pongScore;
    private Button pongLoad;
    private String username;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pong_main_menu);
        declarations();
        addPongGameButtonListener();
        user = User.usernameToUser.get(username);
        addPongLoadButtonListener();
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

    /**
    private void addPongScoreButtonListener() {
        pongScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PongStartingActivity.this, PongMainActivity.class);
                //intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
    **/

    /**
     * Get all the save names that have a given complexity.
     *
     * @param saves      the hash map of all the existing saves
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
                Intent intent = new Intent(PongStartingActivity.this, PongMainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
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
        PongGameController saveToLoad = (PongGameController) saves.get(saveName);

        // start the game with the correct GameInfo
        Intent intent = new Intent(PongStartingActivity.this, PongMainActivity.class);
        intent.putExtra("saveToLoad", saveToLoad);
        startActivity(intent);
    }

    // TODO
    /**
     * 1. Pause button and resume from there
     * 2. Once the game is over, prompt the user with text field
     * 3. Save the game when it is over
     * 4. Add the save button to the paused menu
     * 5. Save that game state into a file when save is clicked
     * 6. Connect scoreboard to saved file and user
     * 7. Add extra functions to pong game (eg. sound and power up items)
     */
}
