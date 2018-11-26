package pong;

import android.graphics.RectF;

import org.junit.Before;
import org.junit.Test;

import fall2018.csc2017.pong.PongGameController;
import fall2018.csc2017.pong.PongGameInfo;
import fall2018.csc2017.pong.SerializableRectF;

import static org.junit.Assert.*;

public class PongGameControllerTest {
    PongGameController testController;
    PongGameInfo testInfo;

    @Before
    public void setUp(){
        testInfo = new PongGameInfo(1080, 1920, "tester");
        //testInfo.setFps((long)0.25); // So that it will for sure collide with walls
        testController = new PongGameController(testInfo);
    }

    @Test
    public void updateRacketCollision() {
        int tempScore = testInfo.getScore();
        PongGameInfo tempInfo = testInfo;
        float previousXVelocity = testInfo.getBall().getXVelocity();
        float previousYVelocity = testInfo.getBall().getYVelocity();

        testInfo.getBall().reset(1020, 1080);
        testInfo.getRacket().getRectF().left = testInfo.getBall().getRectF().left; // Force collide
        testInfo.getRacket().getRectF().right = testInfo.getBall().getRectF().right;
        testInfo.getRacket().getRectF().top = testInfo.getBall().getRectF().top;
        testInfo.getRacket().getRectF().bottom = testInfo.getBall().getRectF().bottom;

        assertTrue(RectF.intersects(testInfo.getBall().getRectF(), testInfo.getRacket().getRectF()));

        testInfo.getRacket().getRectF().bottom = testInfo.getBall().getRectF().bottom;
        testController.update();
        // Check whether the ball's coordinate was updated properly
        System.out.println("Post Score is: " + testInfo.getScore());

        assertEquals(true, tempScore + 1 == testInfo.getScore());
        //testing both increase and random
        assertEquals(true, previousXVelocity + previousXVelocity/10 == testInfo.getBall().getXVelocity()
        || -previousXVelocity + previousXVelocity/10 == -testInfo.getBall().getXVelocity());
        //testing increase velocity, reverse direction
        assertEquals(true, -(previousYVelocity + previousYVelocity/10) == -testInfo.getBall().getYVelocity());
        //test the position of the ball (clearObstacleY)
        assertEquals(true, testInfo.getBall().getRectF().bottom == tempInfo.getBall().getRectF().bottom - 2);
        assertEquals(true, testInfo.getBall().getRectF().top ==
                testInfo.getBall().getRectF().bottom - tempInfo.getBall().getBallHeight());

    }

    @Test
    public void draw() {
    }

    @Test
    public void setupAndRestart() {
    }

    @Test
    public void isOver() {
    }
}