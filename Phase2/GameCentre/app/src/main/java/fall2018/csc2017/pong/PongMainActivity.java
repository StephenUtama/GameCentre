package fall2018.csc2017.pong;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import fall2018.csc2017.slidingtiles.R;

public class PongMainActivity extends AppCompatActivity {

    /**
     * View of the game
     */
    PongSurfaceView pongView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        // Initialize pongView and set it as the view
        pongView = new PongSurfaceView(this, size.x, size.y);

        //Create your frame layout
        FrameLayout frameLayout = new FrameLayout(this);

        //Adding PongView into frameLayout
        frameLayout.addView(pongView);

        //Creating button
        Button button1 = new Button(this);
        button1.setText("Pause");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PongMainActivity.this, "Paused!", Toast.LENGTH_SHORT).show();
                }
        });

        /// Declaring and initializing LayoutParams for the frameLayout
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        // Setting the location of the button
        params.setMargins(0, 0, 0, 0);
        params.gravity = Gravity.CENTER_HORIZONTAL;

        //Adding button1 to frameLayout
        frameLayout.addView(button1, params);

        setContentView(frameLayout);



    }

    /**
     * Activates when game is started/resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        pongView.resume();
    }

    /**
     * Activates when game is paused/closed
     */
    @Override
    protected void onPause() {
        super.onPause();
        pongView.pause();
    }
}
