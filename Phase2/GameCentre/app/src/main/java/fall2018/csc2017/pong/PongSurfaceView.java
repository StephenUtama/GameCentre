package fall2018.csc2017.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PongSurfaceView extends SurfaceView implements Runnable {

    /**
     * A Thread that we will start and stop from the pause and resume methods.
     */
    Thread thread = null;

    /**
     * A SurfaceHolder allows us to draw.
     */
    SurfaceHolder surfaceHolder;

    /**
     * Whether or not game is running (from thread).
     * Volatile because it is accessed from inside and outside the thread
     */
    volatile boolean playing;

    /**
     * Whether or not the game is paused.
     */
    boolean paused = true;

    /**
     * Canvas to draw on.
     */
    Canvas canvas;

    /**
     * Paint to draw with.
     */
    Paint paint;

    /**
     * The game's fps.
     */
    long fps;

    /**
     * Size of vertical screen in pixels.
     */
    int screenWidth;

    /**
     * Size of horizontal screen in pixels.
     */
    int screenHeight;

    /**
     * The player's racket.
     */
    Racket racket;

    /**
     * The ball.
     */
    Ball ball;

//    /**
//     * Sound FX
//     */
//    SoundPool sp;
//    int beep1ID = -1;
//    int beep2ID = -1;
//    int beep3ID = -1;
//    int loseLifeID = -1;

    /**
     * The player's score.
     */
    int score = 0;

    /**
     * How many lives the player has.
     */
    int lives = 3;

    /**
     * Constructor for PongSurfaceView
     * @param context
     * @param x width of screen
     * @param y height of screen
     */
    public PongSurfaceView(Context context, int x, int y) {

    /*
        The next line of code asks the
        SurfaceView class to set up our object.
    */
        super(context);

        // Set the screen width and height
        screenWidth = x;
        screenHeight = y;

        // Initialize surfaceHolder and paint objects
        this.surfaceHolder = getHolder();
        paint = new Paint();

        // A new racket
        racket = new Racket(screenWidth, screenHeight);

        // Create a ball
        ball = new Ball(screenWidth, screenHeight);

        setupAndRestart();

    }

    /**
     * Start/Restart the game.
     */
    public void setupAndRestart(){
        // Put the ball back to the start
        ball.reset(screenWidth, screenHeight);
        // if game over reset scores and lives
        if(lives == 0) {
            score = 0;
            lives = 3;
        }
    }

    @Override
    public void run() {
        while (playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // If not paused update the frame
            if(!paused){
                update();
            }

            // Draw the frame
            draw();

            // time it took in millisecond to update and draw
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;

            // updating fps
            if (timeThisFrame >= 1) { // so it doesn't divide by 0
                fps = 1000 / timeThisFrame;
            }
        }
    }

    /**
     * displaying the objects in the screen by using Paint and Canvas.
     * We do this by making sure surfaceholder and canvas is valid
     */
    public void draw(){
        if (this.surfaceHolder.getSurface().isValid()){

            this.canvas = this.surfaceHolder.lockCanvas();
            this.canvas.drawColor(Color.argb(255, 120, 197, 87));
            this.paint.setColor(Color.argb(255, 255, 255, 255));
            this.canvas.drawRect(racket.getRect(), this.paint);
            this.canvas.drawRect(this.ball.getRect(), this.paint);
            this.paint.setColor(Color.argb(255, 255, 255, 255));
            this.paint.setTextSize(40);
            this.canvas.drawText("Score: " + this.score + "  Lives: " + this.lives,
                    10,50, this.paint);

            this.surfaceHolder.unlockCanvasAndPost(this.canvas);
        }
    }

    /**
     * When the game is paused, change the p[laying into false so the loop stops
     */
    public void pause(){
        this.playing = false;
        try {
            this.thread.join();
        } catch (InterruptedException e){
            Log.e("Error: ", "Joining Thread");
        }
    }

    /**
     * Set the playing into true and start updating the view
     */
    public void resume(){
        this.playing = true;
        this.thread = new Thread(this);
        this.thread.start();
    }
}
