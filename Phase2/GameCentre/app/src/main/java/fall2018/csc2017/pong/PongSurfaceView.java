package fall2018.csc2017.pong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import fall2018.csc2017.slidingtiles.SlidingTileScoreboards;
import generalactivities.SettingActivity;
import generalclasses.User;

import static android.content.Context.MODE_PRIVATE;

public class PongSurfaceView extends SurfaceView implements Runnable {

    /**
     * A Thread that we will start and stop from the pause and resume methods.
     */
    Thread thread = null;

    /**
     * Size of vertical screen in pixels.
     */
    int screenWidth;

    /**
     * Size of horizontal screen in pixels.
     */
    int screenHeight;
    /**
     * Controller for this view
     */
    PongGameController controller;

    public static final String SAVE_FILENAME = "master_save_file.ser";

    /**
     * GameInfo for the game
     */
    PongGameInfo gameInfo;

    Context context;
    User user;

    PongScoreBoard scoreboards;

    /**
     * Constructor for PongSurfaceView.
     * @param context context of the PongMainActivity
     * @param screenWidth width of screen
     * @param screenHeight height of screen
     */
    public PongSurfaceView(Context context, int screenWidth, int screenHeight, PongGameInfo gameInfo) {
        super(context);
        this.context = context;
        // Initialize the width and height of the screen.
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        // Initialize the gameInfo
        this.gameInfo = gameInfo;
        //Initialize the controller
        this.controller = new PongGameController(getHolder(), gameInfo);
        user = User.usernameToUser.get(gameInfo.getUserName());
    }

    public PongGameInfo getGameInfo() {
        return controller.gameInfo;
    }

    public PongGameController getController(){
        return controller;
    }

    @Override
    public void run() {
        while (controller.playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // If not paused update the frame
            if(!controller.paused){
                controller.update();
            }

            // Draw the frame
            controller.draw();

            // time it took in millisecond to update and draw
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;

            // updating fps
            if (timeThisFrame >= 1) { // so it doesn't divide by 0
                controller.gameInfo.setFps(1000 / timeThisFrame);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                controller.paused = false;
                if (controller.gameInfo.getLives() == 0){
                    Toast.makeText(this.context, "Game Over! Score: " + controller.gameInfo.getScore(), Toast.LENGTH_SHORT).show();
                    controller.setupAndRestart();
                }

                // Is the touch on the right or left?
                if(motionEvent.getX() > screenWidth / 2){
                    controller.gameInfo.getRacket().setMovementState(controller.gameInfo.getRacket().RIGHT);
                }
                else{
                    controller.gameInfo.getRacket().setMovementState(controller.gameInfo.getRacket().LEFT);
                }

                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:

                controller.gameInfo.getRacket().setMovementState(controller.gameInfo.getRacket().STOPPED);
                break;
        }
        return true;
    }

    /**
     * When the game is paused, change 'playing' into false so the loop stops
     */
    public void pause(){
        controller.playing = false; // Note
        try {
            thread.join();
        } catch (InterruptedException e){
            Log.e("Error: ", "Joining Thread");
        }
    }

    /**
     * Set the playing into true and start updating the view
     */
    public void resume(){
        controller.playing = true;
        thread = new Thread(this);
        thread.start();
    }



}
