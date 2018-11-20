package fall2018.csc2017.pong;

import java.io.Serializable;

import generalclasses.GameInfo;

public class PongGameInfo extends GameInfo implements Serializable {

    /**
     * width of screen in pixels
     */
    int screenWidth;

    /**
     * height of screen in pixels
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
     * The player's score
     */
    int score;

    /**
     * The number of lives player has.
     */
    int lives;

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public PongGameInfo(int screenWidth, int screenHeight, String username) {
        setUserName(username);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.score = 0;
        this.lives = 3;
        this.racket = new Racket(screenWidth, screenHeight);
        this.ball = new Ball(screenWidth, screenHeight);
    }

    public int getLives() {
        return lives;
    }

    public Racket getRacket() {
        return racket;
    }

    public Ball getBall() {
        return ball;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public int getScore() {
        return 0;

    }

    @Override
    public void updateScore() {
        score ++;
    }

    public void updateLife(){
        lives --;
    }

    @Override
    public String getUserName() {
        return super.getUserName();
    }

    @Override
    public void setUserName(String username) {
        super.setUserName(username);
    }

    @Override
    public String getGame() {
        return null;
    }
}
