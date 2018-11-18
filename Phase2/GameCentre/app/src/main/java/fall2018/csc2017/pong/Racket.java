package fall2018.csc2017.pong;

import android.graphics.RectF;

/**
 * Racket (bar on the bottom) class of Pong game
 * cited from: http://androidgameprogramming.com/programming-a-pong-game-part-3/
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
        this.screenWidth = width;
        this.screenHeight = height;

        this.length = screenWidth/8;
        this.height = screenHeight/25;

        this.x = screenWidth/2;
        this.y = screenHeight - 20;

        this.rect = new RectF( this.x, this.y, x + length, y + height);

        this.rectSpeed = screenWidth;
    }

    /**
     * Getter method for rectangle representation of the Racket
     */
    public RectF getRect(){
        return this.rect;
    }

    /**
     * Sets new direction of the Racket
     * @param state new movement direction from final values
     */
    public void setMovementState(int state){
        this.rectMove = state;
    }

    /**
     * Updates the coordinate of the view once it's needed
     * @param fps how smooth the movement is
     */
    public void update (long fps){
        float distance = this.rectSpeed/fps;
        if (this.rectMove == LEFT){
            x = x - distance;
        } else if(this.rectMove == RIGHT){
            x = x + distance;
        }

        if (this.rect.left < 0){ // this might be wrong. use this.x instead of this.rect
            this.x = 0;
        }else if (this.rect.right > screenWidth){
            this.x = this.screenWidth - this.length;
        }

        this.rect.left = this.x;
        this.rect.right = this.x + this.length;
    }





}

