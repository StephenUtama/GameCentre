package fall2018.csc2017.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.SurfaceHolder;

import java.io.Serializable;

import generalclasses.GameController;

/**
 * Controller acts on both model and view. It controls the data flow into model object and updates
 * the view whenever data changes. It keeps view and model separate.
 */
public class PongGameController implements GameController, Serializable {

    /**
     * PongGameInfo
     */
    PongGameInfo gameInfo;

    /**
     * A SurfaceHolder allows us to draw.
     */
    SurfaceHolder surfaceHolder;

    /**
     * Whether or not game is running (from thread).
     * Volatile because it is accessed from inside and outside the thread
     */
    volatile static boolean playing;

    /**
     * Whether or not the game is paused.
     */
    static boolean paused = true;

    /**
     * Canvas to draw on.
     */
    Canvas canvas;

    /**
     * Paint to draw with.
     */
    Paint paint;


//     Sound FX
//    SoundPool sp;
//    int beep1ID = -1;
//    int beep2ID = -1;
//    int beep3ID = -1;
//    int loseLifeID = -1;


    /**
     * Initializes PongGameController
     */
    public PongGameController(SurfaceHolder ourHolder, PongGameInfo gameInfo) {

        // Initialize surfaceHolder and paint objects
        this.surfaceHolder = ourHolder;
        paint = new Paint();
        // Initialize a PongGameInfo
        this.gameInfo = gameInfo;

    }

    /**
     * Updates the rect of the ball and racket (Make move pretty much)
     */
    public void update() {

        // Move the racket if required
        gameInfo.getRacket().update(gameInfo.getFps());
        gameInfo.getBall().update(gameInfo.getFps());

        // If ball colliding with racket
        if(RectF.intersects(gameInfo.getRacket().getRectF(), gameInfo.getBall().getRectF())) {
            gameInfo.getBall().setRandomXVelocity();
            gameInfo.getBall().reverseYVelocity();
            gameInfo.getBall().clearObstacleY(gameInfo.getRacket().getRectF().top - 2);

            gameInfo.updateScore();
            gameInfo.getBall().increaseVelocity();

            // sp.play(beep1ID, 1, 1, 0, 0, 1);
        }

        // If ball hits bottom of the screen
        if(gameInfo.getBall().getRectF().bottom > gameInfo.getScreenHeight()){
            gameInfo.getBall().reverseYVelocity();
            gameInfo.getBall().clearObstacleY(gameInfo.getScreenHeight() - 2);

            // Lose a life
            gameInfo.updateLife();
            // sp.play(loseLifeID, 1, 1, 0, 0, 1);
            if(isOver()){
                paused = true;
            }

        }
        // If ball hits top of the screen
        if(gameInfo.getBall().getRectF().top < 0){
            gameInfo.getBall().reverseYVelocity();
            gameInfo.getBall().clearObstacleY(12);

            // sp.play(beep2ID, 1, 1, 0, 0, 1);
        }

        // If ball hits left of the screen
        if(gameInfo.getBall().getRectF().left < 0){
            gameInfo.getBall().reverseXVelocity();
            gameInfo.getBall().clearObstacleX(2);

            // sp.play(beep3ID, 1, 1, 0, 0, 1);
        }

        // If ball hits right of the screen
        if(gameInfo.getBall().getRectF().right > gameInfo.getScreenWidth()){
            gameInfo.getBall().reverseXVelocity();
            gameInfo.getBall().clearObstacleX(gameInfo.getScreenWidth() - 22);

            // sp.play(beep3ID, 1, 1, 0, 0, 1);
        }
    }

    /**
     * displaying the objects in the screen by using Paint and Canvas.
     * We do this by making sure surfaceHolder and canvas is valid
     */
    public void draw(){
        if (this.surfaceHolder.getSurface().isValid()){
            if (isOver()){
                this.canvas = this.surfaceHolder.lockCanvas();
                this.paint.setColor(Color.argb(255,255,255,255));
                this.paint.setTextSize(60);
                this.canvas.drawText("Game Over! Score: " + gameInfo.getScore(), gameInfo.screenWidth/4, gameInfo.screenHeight/2, this.paint);
                this.paint.setTextSize(40);
                this.canvas.drawText("Tap to play one more time!", gameInfo.screenWidth/4 + 10, gameInfo.screenHeight/2 + 50, this.paint);
                this.surfaceHolder.unlockCanvasAndPost(this.canvas);
            }else{
                this.canvas = this.surfaceHolder.lockCanvas();
                this.canvas.drawColor(Color.argb(255, 120, 197, 87));
                this.paint.setColor(Color.argb(255, 255, 255, 255));
                this.canvas.drawRect(gameInfo.getRacket().getRectF(), this.paint);
                this.canvas.drawRect(gameInfo.getBall().getRectF(), this.paint);
                this.paint.setColor(Color.argb(255, 255, 255, 255));
                this.paint.setTextSize(40);
                this.canvas.drawText("Score: " + gameInfo.getScore() + "  Lives: " + gameInfo.getLives(),
                        10,50, this.paint);
                this.surfaceHolder.unlockCanvasAndPost(this.canvas);
            }
        }
    }

    /**
     * Restart the game.
     */
    public void setupAndRestart(){
        // Put the ball back to the start
        gameInfo.getBall().reset(gameInfo.getScreenWidth(), gameInfo.getScreenHeight());
        // if game over reset scores and lives
        if(gameInfo.getLives() == 0) {
            gameInfo.setScore(0);
            gameInfo.setLives(3);
        }
    }

    public boolean isOver(){
        return gameInfo.lives == 0;
    }
}
