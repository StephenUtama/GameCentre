package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
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
import java.util.Observable;
import java.util.Observer;

import generalclasses.GameScoreboards;
import generalclasses.ScoreBoard;
import generalclasses.User;

import static fall2018.csc2017.slidingtiles.StartingActivity.SAVE_FILENAME;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private SlidingTilesManager slidingTilesManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * List of split image given by users.
     */
    public static Bitmap[] backgrounds;

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * boolean whether the complexity is Image or not.
     */
    public static boolean image_game = false;
    protected static SlidingTilesGameInfo gameInfo;
    private User user;

    private GameScoreboards scoreboards;

    private GameActivityController mController;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mController = new GameActivityController(this);
        gameInfo = (SlidingTilesGameInfo) getIntent().getSerializableExtra("saveToLoad");
        // make manager, then set game info
        slidingTilesManager = new SlidingTilesManager();
        slidingTilesManager.setInfo(gameInfo);

        // determine current user
        user = User.usernameToUser.get(gameInfo.getUserName());

        createTileButtons(this);
        setContentView(R.layout.activity_main);
        addUndoButtonListener();
        addSaveButtonListener();

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(Board.NUM_COLS);
        gridView.setSlidingTilesManager(slidingTilesManager);
        slidingTilesManager.getBoard().addObserver(this);

        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / Board.NUM_COLS;
                        columnHeight = displayHeight / Board.NUM_ROWS;

                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = slidingTilesManager.getBoard();
        tileButtons = mController.createTileButtons(image_game, board);

//        if (image_game){ // If the complexity is image, creates buttons differently
//            int num;
//            for (int row = 0; row != Board.NUM_ROWS; row++) {
//                for (int col = 0; col != Board.NUM_COLS; col++) {
//                    num = board.getTile(row,col).getId()-1;
//                    helperCreatingButton(num, row, col, context);
//
//                }
//            }
//        } else {
//            for (int row = 0; row != Board.NUM_ROWS; row++) {
//                for (int col = 0; col != Board.NUM_COLS; col++) {
//                    Button tmp = new Button(context);
//                    tmp.setBackgroundResource(board.getTile(row, col).getBackground());
//                    this.tileButtons.add(tmp);
//                }
//            }
//        }

    }
//
//    /**
//     * Helper function for creating a button when complexity is image
//     * @param num id of the tile
//     * @param row row of the creating tile
//     * @param col col of the creating tile
//     * @param context context of the activity
//     */
//    public void helperCreatingButton(int num, int row, int col, Context context) {
//        Board board = slidingTilesManager.getBoard();
//        Button tmp = new Button(context);
//        if (num != 24) {
//            BitmapDrawable bmp = new BitmapDrawable(backgrounds[num]);
//            tmp.setBackground(bmp);
//            this.tileButtons.add(tmp);
//        } else {
//            tmp.setBackgroundResource(board.getTile(row, col).getBackground());
//            this.tileButtons.add(tmp);
//        }
//    }
    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = slidingTilesManager.getBoard();
        mController.updateTileButtons(tileButtons, image_game, board);
//        if (image_game) { // If the complexity is image, updates buttons differently
//            int next = 0;
//            for (Button b : tileButtons) {
//                helperUpdate(b, next);
//                next++;
//            }
////        } else {
//            int nextPos = 0;
//            for (Button b : tileButtons) {
//                int row = nextPos / Board.NUM_ROWS;
//                int col = nextPos % Board.NUM_COLS;
//                b.setBackgroundResource(board.getTile(row, col).getBackground());
//                nextPos++;
//            }
//        }
        autosave();
        updateAndSaveScoreboardIfGameOver();
    }


//    /**
//     * Helper function for updating the background of the tile when complexity is image
//     * @param b current button that is being updated
//     * @param next the position of the tile that is being updated
//     */
//    public void helperUpdate(Button b, int next){
//        Board board = slidingTilesManager.getBoard();
//        int row = next / Board.NUM_ROWS;
//        int col = next % Board.NUM_COLS;
//        int num = board.getTile(row, col).getId() - 1;
//        if(num != 24){
//            BitmapDrawable bmp = new BitmapDrawable(backgrounds[num]);
//            b.setBackground(bmp);
//        }else{
//            b.setBackgroundResource(board.getTile(row, col).getBackground());
//        }
//    }

    private void updateAndSaveScoreboardIfGameOver() {
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



    private void loadScoreboards() {
        try {
            InputStream inputStream = this.openFileInput("SAVED_SCOREBOARDS");
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


    /**
     * Create new or replace a save named "Autosave".
     */
    private void autosave() {
        String game = slidingTilesManager.getInfo().getGame();
        user.addSave(game, "Autosave", gameInfo);
        saveToFile(SAVE_FILENAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Save the current hash map with each user's saves to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(User.usernameToUser);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    /**
     * Add undo button.
     */
    public void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numPreviousMoves = gameInfo.previousMovesList.size(); // get number of moves made

                if ((numPreviousMoves) != 0) { // check whether any moves were made
                    int previousMove = slidingTilesManager.returnPreviousMove();
                    slidingTilesManager.makeMove(previousMove);
                }
            }
        });
    }

    /**
     * Add save button
     */
    public void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // look at the current game info from GameActivity
                SlidingTilesGameInfo gameInfo = GameActivity.gameInfo;
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
        });
    }

    /**
     * Display that there was nothing to save.
     */
    private void makeToastNothingToSave() {
        Toast.makeText(this, "Nothing to Save", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }


}

//