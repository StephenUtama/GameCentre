package fall2018.csc2017.pong;
import android.graphics.RectF; // https://developer.android.com/reference/android/graphics/RectF

import java.io.Serializable;
import java.util.Random;

public class Ball implements Serializable {

    /**
     * Coordinates of the ball on the screen
     */
    private SerializableRectF rect;
    /**
     * Velocity in the X direction
     */
    private float XVelocity;
    /**
     * Velocity in the Y direction
     */
    private float YVelocity;
    /**
     * Width of Ball
     */
    private float BallWidth;
    /**
     * Height of Ball
     */
    private float BallHeight;

    private int screenHeight;
    /**
     * Constructor for Ball
     * @param screenX Width of screen
     * @param screenY Height of screen
     */
    public Ball(int screenX, int screenY) {

        // Make the ball size relative to the screen resolution
        BallWidth = screenX / 100;
        BallHeight = BallWidth;
        screenHeight = screenY;
        // Start the ball travelling straight up
        // at a quarter of the screen height per second
        YVelocity = screenY / 4;
        XVelocity = YVelocity;


        // Initialize the rect which is the coordinates of the ball.
        RectF temp = new RectF();
        rect = new SerializableRectF(temp);
        rect.getRect().left = screenX / 2;
        rect.getRect().top = screenY / 10;
        rect.getRect().right = screenX / 2 + BallWidth;
        rect.getRect().bottom = screenY / 10 - BallHeight;

    }

    /**
     * Returns the rect
     * @return rect, the position of the ball.
     */
    public SerializableRectF getRect() {
        return rect;
    }

    /**
     * Updates the position of the ball in each frame
     * @param fps fps of device
     */
    public void update(long fps) {
        // Use fps to calculate so that it moves at consistent speed among all devices.
        rect.getRect().left = rect.getRect().left + (XVelocity / fps);
        rect.getRect().top = rect.getRect().top + (YVelocity / fps);
        rect.getRect().right = rect.getRect().left + BallWidth;
        rect.getRect().bottom = rect.getRect().top - BallHeight;
    }

    /**
     * Reverses the YVelocity
     */
    public void reverseYVelocity(){
        YVelocity = -YVelocity;
    }

    /**
     * Reverses the XVelocity
     */
    public void reverseXVelocity(){
        XVelocity = -XVelocity;
    }

    /**
     * Generate a random velocity
     */
    public void setRandomXVelocity(){
        // Generate either 0 or 1
        Random generator = new Random();
        int num = generator.nextInt(2);

        if(num == 0){
            reverseXVelocity();
        }
    }

    /**
     * Increases velocity by 10%
     */
    public void increaseVelocity(){
        XVelocity += XVelocity / 10;
        YVelocity += YVelocity / 10;
    }

    /**
     * Clears an obstacle on vertical axis
     * @param y coordinate
     */
    public void clearObstacleY(float y){
        rect.getRect().bottom = y;
        rect.getRect().top = y - BallHeight;
    }

    /**
     * Clears an obstacle on horizontal axis
     * @param x coordinate
     */
    public void clearObstacleX(float x){
        rect.getRect().left = x;
        rect.getRect().right = x + BallWidth;
    }

    /**
     * Reset coordinate of ball to the bottom center of the screen
     * @param x coordinate
     * @param y coordinate
     */
    public void reset(int x, int y){
        rect.getRect().left = x / 2;
        rect.getRect().top = y / 10;
        rect.getRect().right = x / 2 + BallWidth;
        rect.getRect().bottom = y / 10 - BallHeight;
        YVelocity = screenHeight / 4;
        XVelocity = YVelocity;
    }
}
