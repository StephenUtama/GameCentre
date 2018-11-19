package fall2018.csc2017.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;

import generalclasses.GameController;

/**
 * Controller acts on both model and view. It controls the data flow into model object and updates
 * the view whenever data changes. It keeps view and model separate.
 */
public class PongGameController implements GameController {

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
     * The player's score.
     */
    int score = 0;

    /**
     * How many lives the player has.
     */
    int lives = 3;

    /**
     * The game's fps.
     */
    long fps;

//     Sound FX
//    SoundPool sp;
//    int beep1ID = -1;
//    int beep2ID = -1;
//    int beep3ID = -1;
//    int loseLifeID = -1;


    /**
     * Initializes PongGameController
     * @param screenWidth Width of the phone screen in pixels
     * @param screenHeight Height of the phone screen in pixels
     * @param ourHolder The holder for our SurfaceView.
     */
    public PongGameController(int screenWidth, int screenHeight, SurfaceHolder ourHolder) {

        // Set the screen width and height
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        // Initialize surfaceHolder and paint objects
        this.surfaceHolder = ourHolder;
        paint = new Paint();

        // A new racket
        racket = new Racket(screenWidth, screenHeight);

        // Create a ball
        ball = new Ball(screenWidth, screenHeight);
    }

    /**
     * Updates the rect of the ball and racket
     */
    public void update() {

        // Move the racket if required
        racket.update(fps);
        ball.update(fps);

        // If ball colliding with racket
        if(RectF.intersects(racket.getRect(), ball.getRect())) {
            ball.setRandomXVelocity();
            ball.reverseYVelocity();
            ball.clearObstacleY(racket.getRect().top - 2);

            score++;
            ball.increaseVelocity();

            // sp.play(beep1ID, 1, 1, 0, 0, 1);
        }

        // If ball hits bottom of the screen
        if(ball.getRect().bottom > screenHeight){
            ball.reverseYVelocity();
            ball.clearObstacleY(screenHeight - 2);

            // Lose a life
            lives--;
            // sp.play(loseLifeID, 1, 1, 0, 0, 1);

            if(lives == 0){
                paused = true;
                setupAndRestart();
            }
        }
        // If ball hits top of the screen
        if(ball.getRect().top < 0){
            ball.reverseYVelocity();
            ball.clearObstacleY(12);

            // sp.play(beep2ID, 1, 1, 0, 0, 1);
        }

        // If ball hits left of the screen
        if(ball.getRect().left < 0){
            ball.reverseXVelocity();
            ball.clearObstacleX(2);

            // sp.play(beep3ID, 1, 1, 0, 0, 1);
        }

        // If ball hits right of the screen
        if(ball.getRect().right > screenWidth){
            ball.reverseXVelocity();
            ball.clearObstacleX(screenWidth - 22);

            // sp.play(beep3ID, 1, 1, 0, 0, 1);
        }

    }

    /**
     * displaying the objects in the screen by using Paint and Canvas.
     * We do this by making sure surfaceHolder and canvas is valid
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
}
