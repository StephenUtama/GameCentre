package fall2018.csc2017.pong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;

import generalactivities.SettingActivity;

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

    Context context;

    /**
     * Constructor for PongSurfaceView when controller exists
     * @param context context of the PongMainActivity
     * @param screenWidth width of screen
     * @param screenHeight height of screen
     */
    public PongSurfaceView(Context context, int screenWidth, int screenHeight, Object controller) {
        super(context);
        this.context = context;
        // Initialize the width and height of the screen.
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        // Initialize the controller
        this.controller = (PongGameController) controller;
        this.controller.setupAndRestart();

    }

    public PongSurfaceView(Context context, int screenWidth, int screenHeight) {
        super(context);
        this.context = context;
        // Initialize the width and height of the screen.
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        // Initialize the controller
        this.controller = new PongGameController(screenWidth, screenHeight, getHolder());

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
                controller.fps = 1000 / timeThisFrame;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                controller.paused = false;
                if (controller.lives == 0){
                    Toast.makeText(this.context, "Game Over! Score: " + controller.score, Toast.LENGTH_SHORT).show();
                    controller.setupAndRestart();
                }

                // Is the touch on the right or left?
                if(motionEvent.getX() > screenWidth / 2){
                    controller.racket.setMovementState(controller.racket.RIGHT);
                }
                else{
                    controller.racket.setMovementState(controller.racket.LEFT);
                }

                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:

                controller.racket.setMovementState(controller.racket.STOPPED);
                break;
        }
        return true;
    }

    /**
     * When the game is paused, change 'playing' into false so the loop stops
     */
    public void pause(){
        controller.playing = false;
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
