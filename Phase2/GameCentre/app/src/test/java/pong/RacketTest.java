package pong;

import android.graphics.RectF;

import org.junit.Before;
import org.junit.Test;

import fall2018.csc2017.pong.Racket;
import fall2018.csc2017.pong.SerializableRectF;

import static org.junit.Assert.*;

public class RacketTest {

    Racket testRacket;

    @Before
    public void setUp() throws Exception {
        testRacket = new Racket(1080, 1920);
    }
    /**
    @Test
    public void getRectF(){
        RectF newRectF = new RectF(1080/2, 1920 - 150, 1080/6 + 1080/2, 1920 - 150 + 1920/25);
        SerializableRectF Rect = new SerializableRectF(newRectF);
        assertTrue( Rect.);
    }

    @Test
    public void getRectMove(){
        assertEquals(true, testRacket.getRectMove() == 0);
    }

    @Test
    public void setMovementState() {
        testRacket.setMovementState(1);
        assertEquals(true, testRacket.getRectMove() == 1);
    }
     **/

    @Test
    public void updateLeftMove() {
        testRacket.setMovementState(1); // moving left
        testRacket.update(10);
        assertTrue(432 == testRacket.getRectF().left);
        assertTrue(612 == testRacket.getRectF().right);
        testRacket.update(1); // this should make the x coordinate negative
        assertTrue(-648 == testRacket.getRectF().left); // but updates prevents
        assertTrue(-468 == testRacket.getRectF().right);
        testRacket.update(1);
        assertTrue(0 == testRacket.getRectF().left);
    }

    @Test
    public void updateRightMove(){
        testRacket.setMovementState(2);
        testRacket.update(1);
        assertTrue(1620 == testRacket.getRectF().left);
        testRacket.update(1);
        assertTrue(1080 == testRacket.getRectF().right);
    }
}
