package fall2018.csc2017.coldwar;

import java.util.ArrayList;
import java.util.List;

import generalclasses.GameInfo;

public class ColdWarGameInfo extends GameInfo {

    /**
     * A list of tiles to represent the current state of the game board.
     */
    private List<Tile> board;

    /**
     * The "International Reputation" of the signed in user. Used by the lose condition.
     */
    private int userReputation;

    /**
     * The "International Reputation" of the guest user. Used by the lose condition.
     */
    private int guestReputation;

    /**
     * The current player.
     */
    private String currentPlayer = "player1";

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<Tile> getBoard(){
        return this.board;
    }

    public int getUserReputation(){
        return this.userReputation;
    }

    public int getGuestReputation(){
        return this.guestReputation;
    }

    public void setUserReputation(int userReputation) {
        this.userReputation = userReputation;
    }

    public void setGuestReputation(int guestReputation){
        this.guestReputation = guestReputation;
    }

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
        Spy spy = new Spy("player2");
        board.get(3).setAgent(spy);

        board.get(0).setAgent(new SUBase());
        board.get(5).setAgent(new SUBase());
        board.get(30).setAgent(new USBase());
        board.get(35).setAgent(new USBase());
        board.get(2).setAgent(new Diplomat("player1"));
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
