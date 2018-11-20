package fall2018.csc2017.pong;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import fall2018.csc2017.slidingtiles.SlidingTilesGameInfo;
import generalclasses.User;

public class PongMainActivity extends AppCompatActivity {

    /**
     * View of the game
     */
    PongSurfaceView pongView;
    private Button saveButton;
    private String username;
    public static final String SAVE_FILENAME = "master_save_file.ser";
    private User user;
    private PongGameController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get existing controller
        controller = (PongGameController) getIntent().getSerializableExtra("saveToLoad");

        // Get the username
        username = getIntent().getStringExtra("username");

        // Get the User
        user = User.usernameToUser.get(username);

        // Get a Display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        // Initialize pongView and set it as the view
        if (pongView == null){
            pongView = new PongSurfaceView(this, size.x, size.y);
        }
        else {
            pongView = new PongSurfaceView(this, size.x, size.y, controller);
        }
        //Create your frame layout
        FrameLayout frameLayout = new FrameLayout(this);

        //Adding PongView into frameLayout
        frameLayout.addView(pongView);

        //Creating button
        saveButton = new Button(this);
        saveButton.setText("SAVE");
        saveButton.setBackgroundColor(Color.TRANSPARENT);
        addSaveButtonListener();

        /// Declaring and initializing LayoutParams for the frameLayout
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        // Setting the location of the button
        params.setMargins(0, 0, 0, 0);
        params.gravity = Gravity.RIGHT;

        //Adding saveButton to frameLayout
        frameLayout.addView(saveButton, params);
        setContentView(frameLayout);


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

    private void addSaveButtonListener() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PongGameController.paused = true;
                Toast.makeText(PongMainActivity.this, "Paused and Saved!", Toast.LENGTH_SHORT).show();
                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new
                        Date());
                user.addSave("Pong", currentTime, pongView.getController());
                saveToFile(SAVE_FILENAME);
            }
        });
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
