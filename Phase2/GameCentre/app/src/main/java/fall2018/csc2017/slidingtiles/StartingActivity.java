package fall2018.csc2017.slidingtiles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import generalclasses.GameInfo;
import generalclasses.GameScoreboards;
import generalclasses.ScoreBoard;
import generalclasses.User;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "master_save_file.ser";

    /**
     * The board manager.
     */

    private String username;
    private User user;
    private String game = "Sliding Tiles";
    private String complexity;
    private GameScoreboards scoreboards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        username = getIntent().getStringExtra("username");

        super.onCreate(savedInstanceState);

        complexity = getIntent().getStringExtra("complexity");
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        int bg = sharedPref.getInt("background_resources", android.R.color.white); // the second parameter will be fallback if the preference is not found
        getWindow().setBackgroundDrawableResource(bg);
        setContentView(R.layout.activity_starting_);
        addStartButtonListener();
        addLoadButtonListener();
        addScoreBoardButtonListener();
    }

    /**
     * Added a function for the scoreboard
     */
    private void addScoreBoardButtonListener() {
        Button scoreboard = findViewById(R.id.scoreboard);
        scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartingActivity.this,
                        SlidingTilesScoreBoardActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("complexity", complexity);
                startActivity(intent);
            }
        });
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instantiateGameandBegin();
            }
        });
    }


    /**
     * Make a new game, then begin a game activity with the new game.
     */
    private void instantiateGameandBegin() {
        SlidingTilesGameInfo newGameInfo = new SlidingTilesGameInfo();
        newGameInfo.setComplexity(complexity);
        newGameInfo.setUserName(username);
//        User.addScoreboard(game, complexity, new SlidingTilesScoreBoard());

        loadScoreboards();
        if (scoreboards.getScoreboard(complexity) == null) { // if no one has won a game
//            SlidingTileScoreboards newBoards = new SlidingTileScoreboards();
            scoreboards.addScoreboard(complexity, new SlidingTilesScoreBoard());
            saveScoreboards(scoreboards);
            // in subsequent games, however, there is no need for this
        }

        Intent intent = new Intent(StartingActivity.this, GameActivity.class);
        intent.putExtra("saveToLoad", newGameInfo);
        startActivity(intent);
    }

    private void saveScoreboards(GameScoreboards scoreboards) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput("SAVED_SCOREBOARDS", MODE_PRIVATE));
            outputStream.writeObject(scoreboards);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {

        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(StartingActivity.this);
                alert.setTitle("Select your save");
                alert.setIcon(android.R.drawable.ic_dialog_alert);

                final HashMap<String, GameInfo> saves = user.getSavesForGame(game);
                final String[] saveNamesWithCorrectComplexity = getSaveNamesComplexity(complexity,
                        saves);
                alert.setItems(saveNamesWithCorrectComplexity, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        loadSaveAndBegin(saves, saveNamesWithCorrectComplexity, i);
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
     * @param saveNamesWithCorrectComplexity string list of all save names with correct complexity.
     * @param i                              the index used to get the desired save in
     *                                       saveNamesWithCorrectComplexity.
     */
    private void loadSaveAndBegin(HashMap<String, GameInfo> saves,
                                  String[] saveNamesWithCorrectComplexity, int i) {
        // get the corresponding save
        String saveName = saveNamesWithCorrectComplexity[i];
        GameInfo saveToLoad = saves.get(saveName);
        makeToastLoadedText();

        // start the game with the correct GameInfo
        Intent intent = new Intent(StartingActivity.this, GameActivity.class);
        intent.putExtra("saveToLoad", saveToLoad);
        startActivity(intent);
    }


    /**
     * Get all the save names that have a given complexity.
     *
     * @param complexity the complexity to search for
     * @param saves      the hash map of all the existing saves
     * @return a string array of save names that have complexity complexity
     */
    private String[] getSaveNamesComplexity(String complexity, HashMap<String, GameInfo> saves) {
        ArrayList<String> tempResult = new ArrayList<>();
        for (String saveName : saves.keySet()) {
            SlidingTilesGameInfo info = (SlidingTilesGameInfo) saves.get(saveName);
            if (complexity.equals(info.getComplexity())) {
                tempResult.add(saveName);
            }
        }
        return tempResult.toArray(new String[tempResult.size()]);
    }


    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
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
        // also load the most recent copy of scores
        loadScoreboards();

    }

    private void loadScoreboards() {
        try {
            InputStream inputStream = this.openFileInput("SAVED_SCOREBOARDS");
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                scoreboards = (SlidingTileScoreboards) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: " + e.toString());
            saveScoreboards(new SlidingTileScoreboards());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }
}

//