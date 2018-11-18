package fall2018.csc2017.pong;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.slidingtiles.R;
import generalactivities.GameSelectionActivity;
import generalactivities.MenuActivity;

public class PongStartingActivity extends AppCompatActivity {

    private Button pongGame;
    private Button pongScore;
    private Button pongLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pong_main_menu);
        pongGame = findViewById(R.id.new_pong);
        pongScore = findViewById(R.id.pong_score);
        pongLoad = findViewById(R.id.pong_load);
        addPongGameButtonListener();
    }

    private void addPongGameButtonListener() {
        pongGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PongStartingActivity.this, PongMainActivity.class);
                //intent.putExtra("username", username);
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

    private void addPongLoadButtonListener() {
        pongGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PongStartingActivity.this, PongMainActivity.class);
                //intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
     **/
}
