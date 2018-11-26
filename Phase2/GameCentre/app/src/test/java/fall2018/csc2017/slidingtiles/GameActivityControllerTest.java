package fall2018.csc2017.slidingtiles;
////import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import org.mockito.ArgumentMatchers;
import static org.junit.Assert.*;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class GameActivityControllerTest {

    private GameActivityController mController;
    private GameActivity mActivity;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        // Add the tiles from 1 to the NUM_ROWS * NOM_COLS-1
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS - 1;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        tiles.add(new Tile(24)); //add blank tile

        return tiles;
    }

    @Before
    public void setUpMock() {
//        mActivity = mock(GameActivity.class);
        mController = new GameActivityController(mActivity);
        Board.NUM_COLS = 4;
        Board.NUM_ROWS = 4;
    }

    @Test
    public void testCreateTileButtons() {
        boolean image_game = false;
        Board board = new Board(makeTiles());
        ArrayList<Button> tilebuttons = mController.createTileButtons(image_game, board);
        assertEquals(16, tilebuttons.size());
        Board.NUM_COLS = 5;
        Board.NUM_ROWS = 5;
        Board board1 = new Board(makeTiles());
        ArrayList<Button> tilebuttons1 = mController.createTileButtons(image_game, board1);
        assertEquals(25, tilebuttons1.size());
    }

//    @Test
//    public void testUpdateTileButtons() {
//        Board board = new Board(makeTiles());
//        boolean image_game = false;
//        ArrayList<Button> tilebuttons = mController.createTileButtons(image_game, board);
//        assertEquals(R.drawable.tile_25, tilebuttons);
//    }
}