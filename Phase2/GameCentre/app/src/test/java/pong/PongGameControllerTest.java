package pong;

import android.graphics.RectF;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import fall2018.csc2017.pong.Ball;
import fall2018.csc2017.pong.PongGameController;
import fall2018.csc2017.pong.PongGameInfo;
import fall2018.csc2017.pong.Racket;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PongGameControllerTest {
    PongGameController testController;

    private PongGameInfo testInfo;


    @Before
    public void setUp(){
        testInfo = new PongGameInfo(1080,1920,"tester");
        //testInfo.setFps((long)0.25); // So that it will for sure collide with walls
        testController = new PongGameController(testInfo);
    }

    /**
     * Returns true iff the two specified rectangles intersect. In no event are
     * either of the rectangles modified. To record the intersection,
     * use intersect() or setIntersect().
     *
     * @param a The first rectangle being tested for intersection
     * @param b The second rectangle being tested for intersection
     * @return true iff the two specified rectangles intersect. In no event are
     *              either of the rectangles modified.
     */
    public static boolean intersects(RectF a, RectF b) {
        return a.left < b.right && b.left < a.right
                && a.top < b.bottom && b.top < a.bottom;
    }
    @Test
    public void updateRacketCollision() {
//        int tempScore = testInfo.getScore();
//        PongGameInfo tempInfo = testInfo;
//        float previousXVelocity = testInfo.getBall().getXVelocity();
//        float previousYVelocity = testInfo.getBall().getYVelocity();

        // testInfo.getBall().reset(1020, 1080);

        // Setting coordinates to force the collision
        testInfo.getBall().getRectF().left = 5;
        testInfo.getBall().getRectF().right = 30;
        testInfo.getBall().getRectF().top = 10;
        testInfo.getBall().getRectF().bottom = 90;
        testInfo.getRacket().getRectF().left = 20;
        testInfo.getRacket().getRectF().right = 100;
        testInfo.getRacket().getRectF().top = 30;
        testInfo.getRacket().getRectF().bottom = 20;

        assertTrue(intersects(testInfo.getBall().getRectF(), testInfo.getRacket().getRectF()));

//        testInfo.getRacket().getRectF().bottom = testInfo.getBall().getRectF().bottom;
//        testController.update();
//        // Check whether the ball's coordinate was updated properly
//        System.out.println("Post Score is: " + testInfo.getScore());
//
//        assertEquals(true, tempScore + 1 == testInfo.getScore());
//        //testing both increase and random
//        assertEquals(true, previousXVelocity + previousXVelocity/10 == testInfo.getBall().getXVelocity()
//        || -previousXVelocity + previousXVelocity/10 == -testInfo.getBall().getXVelocity());
//        //testing increase velocity, reverse direction
//        assertEquals(true, -(previousYVelocity + previousYVelocity/10) == -testInfo.getBall().getYVelocity());
//        //test the position of the ball (clearObstacleY)
//        assertEquals(true, testInfo.getBall().getRectF().bottom == tempInfo.getBall().getRectF().bottom - 2);
//        assertEquals(true, testInfo.getBall().getRectF().top ==
//                testInfo.getBall().getRectF().bottom - tempInfo.getBall().getBallHeight());

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