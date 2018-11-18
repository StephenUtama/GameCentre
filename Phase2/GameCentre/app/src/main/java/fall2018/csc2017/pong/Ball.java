package fall2018.csc2017.pong;
import android.graphics.RectF; // https://developer.android.com/reference/android/graphics/RectF

import java.util.Random;

public class Ball {

    /**
     * Coordinates of the ball on the screen
     */
    private RectF Rect;
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

    public Ball(int screenX, int screenY) {

        // Make the mBall size relative to the screen resolution
        BallWidth = screenX / 100;
        BallHeight = BallWidth;

        // Start the ball travelling straight up
        // at a quarter of the screen height per second
        YVelocity = screenY / 4;
        XVelocity = YVelocity;

        // Initialize the Rect which is the coordinates of the ball.
        Rect = new RectF();

    }

    /**
     * Returns the Rect
     * @return Rect, the position of the ball.
     */
    public RectF getRect() {
        return Rect;
    }

    /**
     * Updates the position of the ball in each frame
     * @param fps fps of device
     */
    public void update(long fps) {
        // Use fps to calculate so that it moves at consistent speed among all devices.
        Rect.left = Rect.left + (XVelocity / fps);
        Rect.top = Rect.top + (YVelocity / fps);
        Rect.right = Rect.left + BallWidth;
        Rect.bottom = Rect.top - BallHeight;
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
}
