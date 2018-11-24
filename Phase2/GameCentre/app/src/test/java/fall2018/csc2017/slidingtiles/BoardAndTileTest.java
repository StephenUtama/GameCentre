package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BoardAndTileTest {

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

    // TODO: gotta fix this
    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        List<Tile> tiles = makeTiles();
        SlidingTilesGameInfo gameInfo = new SlidingTilesGameInfo(tiles);
        slidingTilesManager = new SlidingTilesManager();
        slidingTilesManager.setInfo(gameInfo);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        slidingTilesManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    // TODO: gotta fix this too
    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertEquals(true, slidingTilesManager.getBoard().getTile(3,2).getId() < slidingTilesManager.getBoard().getTile(3,3).getId());
        assertEquals(true, slidingTilesManager.isOver());
        swapFirstTwoTiles();
        assertEquals(false, slidingTilesManager.isOver());
    }

    //TODO: you'll see the red squigglies when you uncomment the following code
    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, slidingTilesManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, slidingTilesManager.getBoard().getTile(0, 1).getId());
        slidingTilesManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, slidingTilesManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, slidingTilesManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, slidingTilesManager.getBoard().getTile(3, 2).getId());
        assertEquals(25, slidingTilesManager.getBoard().getTile(3, 3).getId());
        slidingTilesManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(25, slidingTilesManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, slidingTilesManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertEquals(true, slidingTilesManager.isValidMove(11));
        assertEquals(true, slidingTilesManager.isValidMove(14));
        assertEquals(false, slidingTilesManager.isValidMove(10));
    }
}

