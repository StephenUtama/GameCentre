package fall2018.csc2017.coldwar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fall2018.csc2017.slidingtiles.R;
import generalclasses.GameScoreboards;
import generalclasses.ScoreBoard;
import generalclasses.User;

public class ColdWarMainActivity extends AppCompatActivity {

    private GridView gridView;
    private TextView userReputationText;
    private TextView guestReputationText;
    private Button endButton, beginButton, saveButton;

    private int selectedPosition = -1; // this is "unselected" by default
    private ColdWarGameInfo coldWarGameInfo;
    private ColdWarSaverModel mSaver;
    private ColdWarGameController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cold_war_main);

        assignments();
        setUpDisplays();
        setUpGridView();

    }

    private void setUpDisplays() {
        // set up some displays
        String guestReputationString = "Guest Global Reputation: " +
                coldWarGameInfo.getPlayer2Reputation().toString();
        String userReputationString = "User Global Reputation: " +
                coldWarGameInfo.getPlayer1Reputation().toString();
        guestReputationText.setText(guestReputationString);
        userReputationText.setText(userReputationString);
    }

    private void setUpGridView() {
        gridView = findViewById(R.id.coldWarGridView);
        gridView.setAdapter(new ImageAdapterGridView(this, ColdWarManager.getImageIDs(coldWarGameInfo)));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                controller.setViews(endButton, guestReputationText, userReputationText);
                controller.touchMove(coldWarGameInfo, selectedPosition, position);
                selectedPosition = controller.getSelectedPosition();
                controller.updateGridView(gridView, coldWarGameInfo);
            }
        });
    }

    private void assignments() {
        endButton = findViewById(R.id.endMoveButton);
        beginButton = findViewById(R.id.beginMoveButton);
        saveButton = findViewById(R.id.saveButton);

        Intent intent = getIntent();
        coldWarGameInfo = (ColdWarGameInfo) intent.getSerializableExtra("gameInfo");
//        coldWarGameInfo = new ColdWarGameInfo("");
        List<Integer> imageIDs = ColdWarManager.getImageIDs(coldWarGameInfo);

        mSaver = new ColdWarSaverModel(this);

        userReputationText = (TextView) findViewById(R.id.userReputation);
        guestReputationText = (TextView) findViewById(R.id.guestReputation);

        controller = new ColdWarGameController(this);

    }

    public void endMoveButtonClicked(View view) {
        ColdWarManager.endTurn(coldWarGameInfo);
        List<Integer> imageIDs = ColdWarManager.getImageIDs(coldWarGameInfo);
        gridView.setAdapter(new ImageAdapterGridView(getBaseContext(), imageIDs));
        endButton.setEnabled(false);
        beginButton.setEnabled(true);
        saveButton.setEnabled(true);
        executeWhenGameOver();
    }

    private void executeWhenGameOver() {
        if (ColdWarManager.isOver(coldWarGameInfo)) {
            saveScoreBoardIfGameOver();
            String message = ColdWarManager.getWinText(coldWarGameInfo);
            showAlert(message);
        }
    }

    /**
     * Show a popup with message.
     *
     * @param message Message to show
     */
    public void showAlert(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        TextView myMessage = new TextView(this);
        myMessage.setText(message);
        myMessage.setTextSize(20);
        myMessage.setGravity(Gravity.CENTER_HORIZONTAL);
        dialog.setView(myMessage);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        dialog.show();

//        Dialog d = dialog.setView(new View(con)).create();
//
//        dialog.setTitle(message);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        dialog.show();
//        d.getWindow().setAttributes(lp);
    }

    private void saveScoreBoardIfGameOver() {
        mSaver.loadScoreboards("COLD_WAR_SAVED_SCOREBOARDS");
        // get the correct scoreboard
        GameScoreboards gameScoreboards = mSaver.getScoreboards();
        ScoreBoard scoreBoard = gameScoreboards.getScoreboard("default");
        String userName = coldWarGameInfo.getUserName();
        int currentScore = coldWarGameInfo.getScore(scoreBoard);

        // update scoreboard with latest score, then save
        scoreBoard.addUserAndScore(userName, currentScore);
        gameScoreboards.addScoreboard("default", scoreBoard);
        mSaver.saveScoreboards(gameScoreboards, "COLD_WAR_SAVED_SCOREBOARDS");
    }

    public void beginMoveButtonClicked(View view) {
        ColdWarManager.beginTurn(coldWarGameInfo);
        List<Integer> imageIDs = ColdWarManager.getImageIDs(coldWarGameInfo);
        gridView.setAdapter(new ImageAdapterGridView(getBaseContext(), imageIDs));
        beginButton.setEnabled(false);
        saveButton.setEnabled(false);
    }

    public void save(View view) {
        // determine current user
        User user = User.usernameToUser.get(coldWarGameInfo.getUserName());
        mSaver.saveButtonListener(coldWarGameInfo, user);
    }
}
