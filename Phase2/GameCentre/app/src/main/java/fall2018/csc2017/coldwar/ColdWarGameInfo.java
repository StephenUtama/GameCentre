package fall2018.csc2017.coldwar;

import android.view.View;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import generalclasses.GameInfo;
import generalclasses.ScoreBoard;
import generalclasses.User;

public class ColdWarGameInfo extends GameInfo {
    private Integer START_REPUTATION = 4;


    /**
     * Used to determine whether the game is over.
     */
    private boolean isBaseInfiltrated = false;

    /**
     * Used to determine whether the game is over.
     */
    private boolean isPlayer1BaseInfiltrated = false;

    /**
     * Used to determine whether the game is over.
     */
    private boolean isPlayer2BaseInfiltrated = false;

    public boolean isPlayer1BaseInfiltrated() {
        return isPlayer1BaseInfiltrated;
    }

    public void setPlayer1BaseInfiltrated(boolean player1BaseInfiltrated) {
        isPlayer1BaseInfiltrated = player1BaseInfiltrated;
    }

    public boolean isPlayer2BaseInfiltrated() {
        return isPlayer2BaseInfiltrated;
    }

    public void setPlayer2BaseInfiltrated(boolean player2BaseInfiltrated) {
        isPlayer2BaseInfiltrated = player2BaseInfiltrated;
    }

    public static String PLAYER1 = "p1";
    public static String PLAYER2 = "p2";

    /**
     * A list of tiles to represent the current state of the game board.
     */
    private List<Tile> board;

    /**
     * The "International Reputation" of the signed in user. Used by the win/lose condition.
     */
    private Integer Player1Reputation = START_REPUTATION;

    /**
     * The "International Reputation" of the guest user. Used by the win/lose condition.
     */
    private Integer Player2Reputation = START_REPUTATION;

    /**
     * The number of spies of the signed in user. Used by the win/lose condition.
     */
    private Integer Player1NumSpies = 4;

    /**
     * The number of spies of the guest user. Used by the win/los condition.
     */
    private Integer Player2NumSpies = 4;

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
     * The username of the User that owns this ColdWarGameInfo
     */
    private String userName;

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

    public ColdWarGameInfo(String userName) {
        this.userName = userName;
        board = new ArrayList<>();
        setUpDefaultBoard();
//        setUpTestBoard();
    }

    public String getUserName() {
        return userName;
    }

    /**
     * Set up a blank board with no pieces.
     */
    private void setUpDefaultBoard() {
        for (int i = 0; i < 36; i++) {
            Tile newTile = new Tile(null);
            board.add(newTile);
        }
        // set up the bases
        board.get(0).setAgent(new SUBase(PLAYER2));
        board.get(5).setAgent(new SUBase(PLAYER2));
        board.get(30).setAgent(new USBase(PLAYER1));
        board.get(35).setAgent(new USBase(PLAYER1));
    }

    public void setTile(Agent agent, int position) {
        Tile tileToSet = this.board.get(position);
        tileToSet.setAgent(agent);
    }

    public int getScore(ScoreBoard scoreBoard) {
        // we get to assume game is over
        int currentScore = 0;
        LinkedHashMap<String, ArrayList<Integer>> scoreMap = scoreBoard.getScoreMap();
        if (scoreMap.containsKey(userName)) {
            currentScore = scoreMap.get(userName).get(0);
        }

        if (ColdWarManager.getWinner(this).equals(PLAYER1)) {
            currentScore += 1;
        } else {
            currentScore -= 1;
        }

        return currentScore;
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
        return "Cold War";
    }


}


