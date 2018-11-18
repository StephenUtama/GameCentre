package fall2018.csc2017.pong;

import android.graphics.RectF;

/**
 * Racket (bar on the bottom) class of Pong game
 */
public class Racket {
    /**
     * Four points that represents rectangle (the racket)
     */
    private RectF rect;

    /**
     * length of the rectangle
     */
    private float length;

    /**
     * height of the rectangle
     */
    private float height;

    /**
     * x,y coordinate of the racket
     */
    private float x;
    private float y;

    /**
     * speed of the racket
     */
    private float rectSpeed;

    /**
     * These four variables represents the direction of the racket
     */
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;
    private int rectMove = STOPPED;

    /**
     * Size of the screen
     */
    private int screenWidth;
    private int screenHeight;

    /**
     * Constructor of the Racket.
     * @param width width of the screen
     * @param height height of the screen
     */
    public Racket(int width, int height){

    }

    /**
     * Getter method for rectangle representation of the Racket
     */
    public RectF getRect(){
        return null;
    }

    /**
     * Sets new direction of the Racket
     * @param state new movement direction from final values
     */
    public void setMovementState(int state){

    }

    /**
     * Updates the coordinate of the view once it's needed
     * @param fps how smooth the movement is
     */
    public void update (long fps){

    }





}

