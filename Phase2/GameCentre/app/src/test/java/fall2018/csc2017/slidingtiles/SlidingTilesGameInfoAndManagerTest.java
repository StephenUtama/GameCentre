package fall2018.csc2017.slidingtiles;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SlidingTilesGameInfoAndManagerTest {


    /** The board manager for testing. */
    SlidingTilesManager slidingTilesManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        Board.NUM_COLS = 4;
        Board.NUM_ROWS = 4;
        List<Tile> tiles = new ArrayList<>();
        // Add the tiles from 1 to the NUM_ROWS * NOM_COLS-1
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS - 1;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        tiles.add(new Tile(24)); //add blank tile

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        List<Tile> tiles = makeTiles();
        SlidingTilesGameInfo gameInfo = new SlidingTilesGameInfo(tiles);
        slidingTilesManager = new SlidingTilesManager();
        slidingTilesManager.setInfo(gameInfo);
    }

    @Test
    public void testInversionSolvedGame() {
        setUpCorrect();
        assertEquals(true, slidingTilesManager.getInfo().calculate_inversions(makeTiles(), 15) == 0);
    }

    @Test
    public void testGameName() {
        setUpCorrect();
        assertEquals("Sliding Tiles", slidingTilesManager.getInfo().getGame());
    }

    @Test
    public void testCalculateScore() {
        setUpCorrect();
        slidingTilesManager.makeMove(14);
        assertEquals(1, slidingTilesManager.getInfo().getScore());
        slidingTilesManager.makeMove(15);
        assertEquals(false, slidingTilesManager.getInfo().getScore() == 1);
    }

    @Test
    public void testTouchMove() {
        setUpCorrect();
        slidingTilesManager.makeMove(14);
        assertEquals(true, slidingTilesManager.getBoard().getTile(3,3).getId() == 15);
        slidingTilesManager.makeMove(12);
        assertEquals(false, slidingTilesManager.getBoard().getTile(3,2).getId() == 13);
    }

//    @Test
//    public void testCoordinatetoIndex() {
//        setUpCorrect();
//        int [] ret = new int[2];
//        ret[0] = 3;
//        ret[1] = 3;
//        assertEquals(15, SlidingTilesManager.convertCoordinateToPosition(ret));
//    }
}
