package pong;

import fall2018.csc2017.pong.Racket;

import static org.junit.Assert.*;

/**
 * Unit test of the Racket class
 */
public class RacketTest {

    /**
     * Create new Racket
     */
    private Racket testRacket = new Racket(1080, 1920);

    //  Most of the methods in Racket is rather simple, so we omit the tests

    /**
     * unit test for update function
     */
    public void testUpdate(){
        testRacket.setMovementState(1); // moving left
        testRacket.update(10);
        assertEquals(true,432 == testRacket.getRectF().left);
        assertEquals(true, 567 == testRacket.getRectF().right);
        testRacket.update(1); // this should make the x coordinate negative
        assertEquals(true, 0 == testRacket.getRectF().left); // but updates prevents
        assertEquals(true, 135 == testRacket.getRectF().right);
    }


}
