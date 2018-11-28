package fall2018.csc2017.coldwar;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MovementTest {
    private ColdWarGameInfo info; // create a new ColdWarGameInfo variable
    private PiecesSelectionManager selectionManager = new PiecesSelectionManager();

    /**
     * Set up a sample board for testing purposes.
     */
    private void setUpTestBoard(){
        info = new ColdWarGameInfo("testUser"); // create a new ColdWarGameInfo object

        // create a new agent to positions hash maps for each player
        HashMap<String, ArrayList<String>> player1AgentToPositionList = new HashMap<>();
        HashMap<String, ArrayList<String>> player2AgentToPositionList = new HashMap<>();

        ArrayList<String> player1SpyPositions = new ArrayList<>(); // String list of player 1's spy positions
        ArrayList<String> player1DiplomatPositions = new ArrayList<>(); // String list of player 1's diplomat positions
        ArrayList<String> player2SpyPositions = new ArrayList<>(); // String list of player 2's spy positions
        ArrayList<String> player2DiplomatPositions = new ArrayList<>(); // String list of player 2's diplomat positions

        // choose the positions
        Collections.addAll(player1SpyPositions, "E3", "A3", "A4", "A5");
        Collections.addAll(player1DiplomatPositions, "B2", "B3", "C5", "B6");
        Collections.addAll(player2SpyPositions, "F2", "F3", "F4", "F5");
        Collections.addAll(player2DiplomatPositions, "E2", "E4", "D5");

        // put the positions in hash map
        player1AgentToPositionList.put("spy", player1SpyPositions);
        player1AgentToPositionList.put("diplomat", player1DiplomatPositions);
        player2AgentToPositionList.put("spy", player2SpyPositions);
        player2AgentToPositionList.put("diplomat", player2DiplomatPositions);

        // add the positions to the board
        selectionManager.addAgents("spy", player1AgentToPositionList, info, ColdWarGameInfo.PLAYER1);
        selectionManager.addAgents("diplomat", player1AgentToPositionList, info, ColdWarGameInfo.PLAYER1);
        selectionManager.addAgents("spy", player2AgentToPositionList, info, ColdWarGameInfo.PLAYER2);
        selectionManager.addAgents("diplomat", player2AgentToPositionList, info, ColdWarGameInfo.PLAYER2);
    }

    @Test
    public void testMoveToNeighbour() {
        setUpTestBoard(); // set up a sample board
        MovementUtility.toggleMovability(info);

        Integer from1 = 7, to1 = 6; // neighbouring on the same row
        Integer from2 = 8, to2 = 10; // not neighbouring on the same row
        Integer from3 = 1, to3 = 7; // neighbouring on different row
        Integer from4 = 6, to4 = 18; // not neighbouring on different row

        Assert.assertTrue(MovementUtility.makeMove(info, from1, to1));
        MovementUtility.toggleMovability(info); // need to make piece movable again, since true

        Assert.assertFalse(MovementUtility.makeMove(info, from2, to2));

        Assert.assertTrue(MovementUtility.makeMove(info, from3, to3));
        MovementUtility.toggleMovability(info); // need to make piece movable again, since true

        Assert.assertFalse(MovementUtility.makeMove(info, from4, to4));
    }

    @Test
    public void testMovable() {
        setUpTestBoard();
        MovementUtility.toggleMovability(info); // make pieces movable (they are not by default)

        Integer from1 = 7, to1 = 6; // move a piece owned by player 1
        Integer from2 = 8, to2 = 10; // move a piece owned by player 1

        // since it is player 1's turn, this should be legal
        Assert.assertTrue(MovementUtility.makeMove(info, from1, to1));

        // since a move was made, it is now player 2's turn, this should not be legal
        Assert.assertFalse(MovementUtility.makeMove(info, from2, to2 ));
    }

    @Test
    public void testPerformAction() {
        setUpTestBoard();
        MovementUtility.toggleMovability(info); // make pieces movable (they are not by default)

        Integer from1 = 16, to1 = 22; // piece legally perform action on diplomat
        Integer from2 = 29, to2 = 35; // diplomat illegally perform action on base
        Integer from3 = 7, to3 = 8; // piece illegally perform action on friendly
        Integer from4 = 26, to4 = 32; // piece legally perform action on spy

        Assert.assertTrue(MovementUtility.makeMove(info, from1, to1));
        MovementUtility.toggleMovability(info);

        Assert.assertFalse(MovementUtility.makeMove(info, from2, to2));

        Assert.assertFalse(MovementUtility.makeMove(info, from3, to3));

        Assert.assertTrue(MovementUtility.makeMove(info, from4, to4));
        MovementUtility.toggleMovability(info);
    }
}
