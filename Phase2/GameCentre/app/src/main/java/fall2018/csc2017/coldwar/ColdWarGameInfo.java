package fall2018.csc2017.coldwar;

import java.util.ArrayList;
import java.util.List;

import generalclasses.GameInfo;

public class ColdWarGameInfo extends GameInfo {

    /**
     * Used to determine whether the game is over.
     */
    boolean isBaseInfiltrated = false;

    public static String PLAYER1 = "p1";
    public static String PLAYER2 = "p2";

    /**
     * A list of tiles to represent the current state of the game board.
     */
    private List<Tile> board;

    /**
     * The "International Reputation" of the signed in user. Used by the win/lose condition.
     */
    private Integer Player1Reputation;

    /**
     * The "International Reputation" of the guest user. Used by the win/lose condition.
     */
    private Integer Player2Reputation;

    /**
     * The number of spies of the signed in user. Used by the win/lose condition.
     */
    private Integer Player1NumSpies;

    /**
     * The number of spies of the guest user. Used by the win/los condition.
     */
    private Integer Player2NumSpies;

    public Integer getPlayer1NumSpies() {
        return Player1NumSpies;
    }

    public void setPlayer1NumSpies(Integer player1NumSpies) {
        Player1NumSpies = player1NumSpies;
    }

    public Integer getPlayer2NumSpies() {
        return Player2NumSpies;
    }

    public void setPlayer2NumSpies(Integer player2NumSpies) {
        Player2NumSpies = player2NumSpies;
    }


    /**
     * The current player.
     */
    private String currentPlayer = PLAYER1;

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<Tile> getBoard(){
        return this.board;
    }

    public Integer getPlayer1Reputation(){
        return this.Player1Reputation;
    }

    public Integer getPlayer2Reputation(){
        return this.Player2Reputation;
    }

    public void setPlayer1Reputation(int player1Reputation) {
        this.Player1Reputation = player1Reputation;
    }

    public void setPlayer2Reputation(int player2Reputation){
        this.Player2Reputation = player2Reputation;
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
        Spy spy = new Spy(PLAYER2);
        board.get(3).setAgent(spy);

        board.get(0).setAgent(new SUBase(PLAYER2));
        board.get(5).setAgent(new SUBase(PLAYER2));
        board.get(30).setAgent(new USBase(PLAYER1));
        board.get(35).setAgent(new USBase(PLAYER1));
        board.get(2).setAgent(new Diplomat(PLAYER1));
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
