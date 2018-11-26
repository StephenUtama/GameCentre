package fall2018.csc2017.coldwar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fall2018.csc2017.slidingtiles.R;
import generalclasses.User;

public class ColdWarMainActivity extends AppCompatActivity {

    GridView gridView;

    TextView userReputationText;

    TextView guestReputationText;

    List<Integer> imageIDs;

    int selectedPosition = -1; // this is "unselected" by default

    ColdWarGameInfo coldWarGameInfo;

    private Button endButton, beginButton;

    ColdWarSaverModel mSaver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cold_war_main);
        endButton = findViewById(R.id.endMoveButton);
        beginButton = findViewById(R.id.beginMoveButton);

        Intent intent = getIntent();
        coldWarGameInfo = (ColdWarGameInfo) intent.getSerializableExtra("gameInfo");
//        coldWarGameInfo = new ColdWarGameInfo("");
        imageIDs = ColdWarManager.getImageIDs(coldWarGameInfo);

        mSaver = new ColdWarSaverModel(this);

        userReputationText = (TextView) findViewById(R.id.userReputation);
        guestReputationText = (TextView) findViewById(R.id.guestReputation);

        String guestReputationString = "Guest Global Reputation: " +
                coldWarGameInfo.getPlayer2Reputation().toString();
        String userReputationString = "User Global Reputation: " +
                coldWarGameInfo.getPlayer1Reputation().toString();
        guestReputationText.setText(guestReputationString);
        userReputationText.setText(userReputationString);


        gridView = findViewById(R.id.coldWarGridView);
        gridView.setAdapter(new ImageAdapterGridView(this, imageIDs));


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedPosition == -1){
                    selectedPosition = position;
                }
                else {
                    if ( ColdWarManager.makeMove(coldWarGameInfo, selectedPosition, position)) {
                        endButton.setEnabled(true);
                    }
                    else {
                        Toast.makeText(ColdWarMainActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                    }
                    selectedPosition = -1; // this indicates that selectedPosition is reset to "unselected"
                    String guestReputationString = "Guest Global Reputation: " +
                            coldWarGameInfo.getPlayer2Reputation().toString();
                    String userReputationString = "User Global Reputation: " +
                            coldWarGameInfo.getPlayer1Reputation().toString();
                    guestReputationText.setText(guestReputationString);
                    userReputationText.setText(userReputationString);
                }
                imageIDs = ColdWarManager.getImageIDs(coldWarGameInfo);

                gridView.setAdapter(new ImageAdapterGridView(getBaseContext(), imageIDs));}
        });
    }

    public void endMoveButtonClicked(View view) {
        ColdWarManager.endTurn(coldWarGameInfo);
        imageIDs = ColdWarManager.getImageIDs(coldWarGameInfo);
        gridView.setAdapter(new ImageAdapterGridView(getBaseContext(), imageIDs));
        endButton.setEnabled(false);
        beginButton.setEnabled(true);
    }

    public void beginMoveButtonClicked(View view) {
        ColdWarManager.beginTurn(coldWarGameInfo);
        imageIDs = ColdWarManager.getImageIDs(coldWarGameInfo);
        gridView.setAdapter(new ImageAdapterGridView(getBaseContext(), imageIDs));
        beginButton.setEnabled(false);
    }

    public void save(View view) {
        // determine current user
        User user = User.usernameToUser.get(coldWarGameInfo.getUserName());
        mSaver.saveButtonListener(coldWarGameInfo, user);
    }
}
