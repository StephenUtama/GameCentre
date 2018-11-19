package generalactivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import fall2018.csc2017.coldwar.ColdWarMainActivity;
import fall2018.csc2017.pong.PongStartingActivity;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.SlidingTilesComplexityActivity;

/**
 * Selecting the game
 */
public class GameSelectionActivity extends AppCompatActivity {

    private Button slidingTiles;
    private Button battleShip;
    private String username;
    private Button pong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selections);
        declarations();
        addSlidingTileButtonListener();
        addBattleshipButtonListener();
        addPongGameButtonListener();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        int bg = sharedPref.getInt("background_resources", android.R.color.white); // the second parameter will be fallback if the preference is not found
        getWindow().setBackgroundDrawableResource(bg);
    }

    private void declarations() {
        slidingTiles = findViewById(R.id.slidingTileButton);
        battleShip = findViewById(R.id.battleshipButton);
        pong = findViewById((R.id.pong_game));
        username = getIntent().getStringExtra("username"); // retrieve the current user from previous activity
    }


    /**
     * Listener of the slidingtile game
     */
    private void addSlidingTileButtonListener() {
        slidingTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameSelectionActivity.this, SlidingTilesComplexityActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }

    /**
     * Listener for the Battleship game.
     */
    private void addBattleshipButtonListener() {
        battleShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameSelectionActivity.this, ColdWarMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addPongGameButtonListener(){
        pong.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameSelectionActivity.this, PongStartingActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }

}
