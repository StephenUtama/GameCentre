package fall2018.csc2017.slidingtiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import generalclasses.GameInfo;

public class SlidingTilesGameInfo extends GameInfo {

    /**
     * A list of previous moves made. This is used for undo purposes.
     */
    ArrayList<Integer> previousMovesList = new ArrayList<>();

    private Board board;

    /**
     * Current score based on the current state of the game.
     */
    private int score;

    /**
     * The complexity of the current game.
     */
    private String complexity;

    /**
     * Constructor for SlidingTilesGameInfo.
     */
    public SlidingTilesGameInfo() {

        List<Tile> tiles = new ArrayList<>();
        // Add the tiles from 1 to the NUM_ROWS * NOM_COLS-1
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS - 1;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        tiles.add(new Tile(24)); //add blank tile

        Collections.shuffle(tiles);
        boolean solvable = check_solvable(tiles);
        while (!solvable) {
            Collections.shuffle(tiles);
            solvable = check_solvable(tiles);
        }
        this.board = new Board(tiles);

    }

    boolean check_solvable(List<Tile> tiles) {
        int blankrow = 0;
        int blankindex = 0;
        for (int i = 0; i != (Board.NUM_COLS * Board.NUM_ROWS); i++) {
            if (tiles.get(i).getId() == 25) {
                blankindex = i;
                blankrow = i / Board.NUM_ROWS;
            }
        }
        int inversions = calculate_inversions(tiles, blankindex);
        boolean oddgrid = (Board.NUM_COLS % 2 == 1) && (inversions % 2 == 0);
        boolean evengrid = (Board.NUM_COLS % 2 == 0) && ((blankrow % 2 == 1) == (inversions % 2 == 0));
        return oddgrid || evengrid;
    }

    int calculate_inversions(List <Tile> tiles, int blankindex) {
        int sum = 0;
        for (int i = 0; i != Board.NUM_COLS * Board.NUM_ROWS -1; i++) {
            if (i != blankindex) {
                for (int j = i + 1; j != Board.NUM_COLS * Board.NUM_ROWS; j++){
                    if (j != blankindex) {
                        if (tiles.get(i).getId() > tiles.get(j).getId()) {
                            sum++;
                        }
                    }
                }
            }
        }
        return sum;
    }
    /**
     * @return the board for the corresponding sliding tiles game
     */
    Board getBoard() {
        return board;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void updateScore() {
        score += 1;
    }


    /**
     * @return the name of the current name.
     */
    public String getGame() {
        return "Sliding Tiles";
    }


    /**
     * Set the complexity of the sliding tiles game.
     *
     * @param complexity whether 3x3, 4x4, 5x5
     */
    void setComplexity(String complexity) {
        this.complexity = complexity;
    }


    /**
     * @return the complexity of the current game.
     */
    String getComplexity() {
        return this.complexity;
    }
}
