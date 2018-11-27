package fall2018.csc2017.pong;

import android.graphics.RectF;
import android.os.PowerManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    private Racket racket;
    private Ball ball;
    private RectF rectr;
    private RectF rectb;

    @Before
    public void setUp(){
//        testInfo = new PongGameInfo(1080,1920,"tester");
//        //testInfo.setFps((long)0.25); // So that it will for sure collide with walls
//        testController = new PongGameController(testInfo);
        testInfo = mock(PongGameInfo.class);
        testInfo.lives = 2;
        racket = mock(Racket.class);
        ball = mock(Ball.class);
        rectr = mock(RectF.class);
        rectb = mock(RectF.class);

        testController = new PongGameController(testInfo);
        when(testInfo.getFps()).thenReturn(1L);
        when(testInfo.getBall()).thenReturn(ball);
        when(testInfo.getRacket()).thenReturn(racket);
        when(racket.getRectF()).thenReturn(rectr);
        when(ball.getRectF()).thenReturn(rectb);
        doNothing().when(ball).update(isA(Long.class));
        doNothing().when(racket).update(isA(Long.class));
        when(testInfo.getScreenHeight()).thenReturn(30);
        when(testInfo.getScreenWidth()).thenReturn(110);
    }

    @Test
    public void updateRacketCollision() {
        rectr.left = 5;
        rectr.right = 30;
        rectr.bottom = 90;
        rectr.top = 10;
        rectb.left = 20;
        rectb.right = 100;
        rectb.top = 30;
        rectb.bottom = 20;
        doNothing().when(ball).setRandomXVelocity();
        doNothing().when(ball).reverseYVelocity();
        doNothing().when(ball).clearObstacleY(isA(Float.class));
        doNothing().when(testInfo).updateScore();
        doNothing().when(ball).increaseVelocity();

        testController.update();

        verify(ball).setRandomXVelocity();
        verify(ball).reverseYVelocity();
        verify(ball).clearObstacleY(8);
        verify(testInfo).updateScore();
        verify(ball).increaseVelocity();
        //        // Setting coordinates to force the collision
//        testInfo.getRacket().getRectF().left = 5;
//        testInfo.getRacket().getRectF().right = 30;
//        testInfo.getRacket().getRectF().top = 10;
//        testInfo.getRacket().getRectF().bottom = 90;
//        testInfo.getBall().getRectF().left = 20;
//        testInfo.getBall().getRectF().right = 100;
//        testInfo.getBall().getRectF().top = 30;
//        testInfo.getBall().getRectF().bottom = 20;
    }

    @Test
    public void testBottomScreenCollision() {
        rectr.left = 110;
        rectr.right = 30;
        rectr.bottom = 90;
        rectr.top = 10;
        rectb.left = 20;
        rectb.right = 100;
        rectb.top = 30;
        rectb.bottom = 40;
        doNothing().when(ball).reverseYVelocity();
        doNothing().when(ball).clearObstacleY(isA(Float.class));
        doNothing().when(testInfo).updateLife();

        testController.update();
        verify(ball).reverseYVelocity();
        verify(ball).clearObstacleY(30 - 2);
        verify(ball, times(0)).reverseXVelocity();
        verify(ball, times(0)).clearObstacleX(isA(Float.class));

        verify(testInfo).updateLife();
        verify(testInfo, times(0)).updateScore();
    }

    @Test
    public void testTopScreenCollision() {
        rectr.left = 110;
        rectr.right = 30;
        rectr.bottom = 90;
        rectr.top = 10;
        rectb.left = 20;
        rectb.right = 100;
        rectb.top = -1;
        rectb.bottom = 20;
        doNothing().when(ball).reverseYVelocity();
        doNothing().when(ball).clearObstacleY(isA(Float.class));

        testController.update();
        verify(ball).reverseYVelocity();
        verify(ball).clearObstacleY(12);
        verify(ball, times(0)).reverseXVelocity();
        verify(ball, times(0)).clearObstacleX(isA(Float.class));
        assertFalse(testController.isOver());
    }

    @Test
    public void testLeftScreenCollision() {
        rectr.left = 110;
        rectr.right = 30;
        rectr.bottom = 90;
        rectr.top = 10;
        rectb.left = -1;
        rectb.right = 100;
        rectb.top = 15;
        rectb.bottom = 20;
        doNothing().when(ball).reverseXVelocity();
        doNothing().when(ball).clearObstacleX(isA(Float.class));

        testController.update();
        verify(ball).reverseXVelocity();
        verify(ball).clearObstacleX(2);
        verify(ball,times(0)).reverseYVelocity();
        verify(ball, times(0)).clearObstacleY(isA(Float.class));
        assertFalse(testController.isOver());
    }

    @Test
    public void testRightScreenCollision() {
        rectr.left = 150;
        rectr.right = 30;
        rectr.bottom = 90;
        rectr.top = 10;
        rectb.left = 20;
        rectb.right = 120;
        rectb.top = 15;
        rectb.bottom = 20;
        doNothing().when(ball).reverseXVelocity();
        doNothing().when(ball).clearObstacleX(isA(Float.class));

        testController.update();
        verify(ball).reverseXVelocity();
        verify(ball).clearObstacleX(110-22);
        verify(ball,times(0)).reverseYVelocity();
        verify(ball, times(0)).clearObstacleY(isA(Float.class));
        assertFalse(testController.isOver());
    }

    @Test
    public void testSetUpAndRestartAndZeroLives () {
        doNothing().when(ball).reset(110, 30);
        testInfo.lives = 0;
        when(testInfo.getLives()).thenReturn(testInfo.lives);
        doNothing().when(testInfo).setScore(isA(Integer.class));
        doNothing().when(testInfo).setLives(isA(Integer.class));

        testController.setupAndRestart();

        verify(testInfo).setScore(0);
        verify(testInfo).setLives(3);
    }

    @Test
    public void testSetUpAndRestartAndLivesRemain () {
        doNothing().when(ball).reset(110, 30);
        when(testInfo.getLives()).thenReturn(testInfo.lives);

        testController.setupAndRestart();

        verify(testInfo, times(0)).setScore(0);
        verify(testInfo, times(0)).setLives(3);
    }


//    @Test
//    public void updateRacketCollision() {
//        int tempScore = testInfo.getScore();
//        PongGameInfo tempInfo = testInfo;
//        float previousXVelocity = testInfo.getBall().getXVelocity();
//        float previousYVelocity = testInfo.getBall().getYVelocity();
//        // testInfo.getBall().reset(1020, 1080);
//
//        // Setting coordinates to force the collision
//        testInfo.getRacket().getRectF().left = 5;
//        testInfo.getRacket().getRectF().right = 30;
//        testInfo.getRacket().getRectF().top = 10;
//        testInfo.getRacket().getRectF().bottom = 90;
//        testInfo.getBall().getRectF().left = 20;
//        testInfo.getBall().getRectF().right = 100;
//        testInfo.getBall().getRectF().top = 30;
//        testInfo.getBall().getRectF().bottom = 20;
//
//        assertTrue(intersects(testInfo.getRacket().getRectF(), testInfo.getBall().getRectF()));
//
////        testInfo.getRacket().getRectF().bottom = testInfo.getBall().getRectF().bottom;
//        testController.update();
//        // Check whether the ball's coordinate was updated properly
//        System.out.println("Post Score is: " + testInfo.getScore());
//
//        assertEquals(true, tempScore + 1 == testInfo.getScore());
//        //testing both increase and random
//        assertEquals(true, previousXVelocity + previousXVelocity/10 == testInfo.getBall().getXVelocity()
//                || -previousXVelocity + previousXVelocity/10 == -testInfo.getBall().getXVelocity());
//        //testing increase velocity, reverse direction
//        assertEquals(true, -(previousYVelocity + previousYVelocity/10) == -testInfo.getBall().getYVelocity());
//        //test the position of the ball (clearObstacleY)
//        assertEquals(true, testInfo.getBall().getRectF().bottom == tempInfo.getBall().getRectF().bottom - 2);
//        assertEquals(true, testInfo.getBall().getRectF().top ==
//                testInfo.getBall().getRectF().bottom - tempInfo.getBall().getBallHeight());
//
//    }


    @Test
    public void setupAndRestart() {
    }


}