package fall2018.csc2017.pong;

import android.graphics.RectF;

import java.io.Serializable;

/**
 * Racket (bar on the bottom) class of Pong game
 * cited from: http://androidgameprogramming.com/programming-a-pong-game-part-3/
 */
public class Racket implements Serializable {
    /**
     * Four points that represents rectangle (the racket)
     */
    private SerializableRectF rect;

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

        this.length = screenWidth/6;
        this.height = screenHeight/25;

        this.x = screenWidth/2;
        this.y = screenHeight - 150;


        RectF temp = new RectF( this.x, this.y, this.x + this.length, this.y + this.height);
        this.rect = new SerializableRectF(temp);

        this.rectSpeed = screenWidth;
    }

    /**
     * Getter method for rectangle representation of the Racket
     */
    public RectF getRectF(){

        return this.rect.getRectF();
    }


    public void setmRectLeft(float left) {
        rect.getRectF().left = left;
    }
    public void setmRectRight(float right) {
        rect.getRectF().right = right;
    }
    public void setmRectTop(float top) {
        rect.getRectF().top = top;
    }
    public void setmRectBottom(float bottom) {
        rect.getRectF().bottom = bottom;
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
        if (this.rect.getRectF().left < 0){ // this might be wrong. use this.x instead of this.rect
            this.x = 0;
        }
        else if (this.rect.getRectF().right > screenWidth){
            this.x = this.screenWidth - this.length;
        }
        this.rect.getRectF().left = this.x;
        this.rect.getRectF().right = this.x + this.length;
    }
}

