package fall2018.csc2017.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
    int screenX;

    /**
     * Size of horizontal screen in pixels.
     */
    int screenY;

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
        screenX = x;
        screenY = y;

        // Initialize surfaceHolder and paint objects
        surfaceHolder = getHolder();
        paint = new Paint();

        // A new racket
        racket = new Racket(screenX, screenY);

        // Create a ball
        ball = new Ball(screenX, screenY);

//    /*
//        Instantiate our sound pool
//        dependent upon which version
//        of Android is present
//    */
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_MEDIA)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                    .build();
//
//            sp = new SoundPool.Builder()
//                    .setMaxStreams(5)
//                    .setAudioAttributes(audioAttributes)
//                    .build();
//        } else {
//            sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
//        }
//
//
//        try{
//            // Create objects of the 2 required classes
//            AssetManager assetManager = context.getAssets();
//            AssetFileDescriptor descriptor;
//
//            // Load our fx in memory ready for use
//            descriptor = assetManager.openFd("beep1.ogg");
//            beep1ID = sp.load(descriptor, 0);
//
//            descriptor = assetManager.openFd("beep2.ogg");
//            beep2ID = sp.load(descriptor, 0);
//
//            descriptor = assetManager.openFd("beep3.ogg");
//            beep3ID = sp.load(descriptor, 0);
//
//            descriptor = assetManager.openFd("loseLife.ogg");
//            loseLifeID = sp.load(descriptor, 0);
//
//            descriptor = assetManager.openFd("explode.ogg");
//            explodeID = sp.load(descriptor, 0);
//
//        }catch(IOException e){
//            // Print an error message to the console
//            Log.e("error", "failed to load sound files");
//        }
//
        setupAndRestart();

    }

    /**
     * Start/Restart the game.
     */
    public void setupAndRestart(){
        // Put the ball back to the start
        ball.reset(screenX, screenY);
        // if game over reset scores and lives
        if(lives == 0) {
            score = 0;
            lives = 3;
        }
    }

    @Override
    public void run() {
        // TODO Implement update()
        // TODO Implement draw()
        while (playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // If not paused update the frame
            if(!paused){
                // update();
            }

            // Draw the frame
            // draw();

            // time it took in millisecond to update and draw
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;

            // updating fps
            if (timeThisFrame >= 1) { // so it doesn't divide by 0
                fps = 1000 / timeThisFrame;
            }
        }
    }
}
