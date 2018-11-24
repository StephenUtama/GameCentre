package fall2018.csc2017.coldwar;

import java.util.ArrayList;
import java.util.List;

import generalclasses.GameInfo;

public class ColdWarGameInfo extends GameInfo {

    /**
     * A list of tiles to represent the current state of the game board.
     */
    List<Tile> board;

    /**
     * The "International Reputation" of the signed in user. Used by the lose condition.
     */
    int userReputation;

    /**
     * The "International Reputation" of the guest user. Used by the lose condition.
     */
    int guestReputation;

    public ColdWarGameInfo() {
        board = new ArrayList<>();
        setUpTestBoard();
    }

    /**
     * Set up a blank board with no pieces.
     */
    private void setUpBlankBoard() {
        for (int i = 0; i < 36; i++) {
            Tile newTile = new Tile(null);
            board.add(newTile);
        }
    }

    /**
     * Set up a test board with some pieces.
     */
    private void setUpTestBoard() {
        setUpBlankBoard();
        Spy spy1 = new Spy();
        board.get(3).agent = spy1;
    }

    /**
     * Get a list of the image IDs based on the occupant of each tile on the board.
     * @return a list of integers corresponding to the correct image IDs.
     */
    public List<Integer> getImageIDs() {
        List<Integer> IDs = new ArrayList<>();

        for (int i = 0; i < board.size(); i++) {
            IDs.add(board.get(i).getPicture());
        }

        return IDs;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void updateScore() {

    }

    @Override
    public String getGame() {
        return null;
    }
}
