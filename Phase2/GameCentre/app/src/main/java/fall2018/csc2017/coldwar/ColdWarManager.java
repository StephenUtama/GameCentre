package fall2018.csc2017.coldwar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.R;

public class ColdWarManager {

    /**
     * Determines whether the game in its current state is over.
     *
     * @return whether the game is over.
     */
    public static boolean isOver(ColdWarGameInfo info) {
        return (info.getPlayer1Reputation().equals(0) | info.getPlayer2Reputation().equals(0) |
                info.isPlayer1BaseInfiltrated() | info.isPlayer2BaseInfiltrated() |
                info.getPlayer1NumSpies().equals(0) | info.getPlayer2NumSpies().equals(0));
    }

    /**
     * Determine and return the win text when game is ended. Win text is determined by the how the
     * game was won.
     * @param info Information about the current game
     * @return A String of text that describes the end game scenario.
     */
    public static String getWinText(ColdWarGameInfo info) {
        if (info.getPlayer1Reputation().equals(0)){
            return "Due to the murder of multiple legitimate diplomats by User's country, " +
                    "public opinion and UN sanctions have led the toppling of User's government, " +
                    "ending a long and bitter cold war between the world's two superpowers. Guest " +
                    "wins!";
        }
        else if (info.getPlayer2Reputation().equals(0)) {
            return "Due to the murder of multiple legitimate diplomats by Guest's country, " +
                    "public opinion and UN sanctions have led the toppling of Guest's government, " +
                    "ending a long and bitter cold war between the world's two superpowers. User " +
                    "wins!";
        }
        else if (info.getPlayer1NumSpies().equals(0)) {
            return "All spies in User's country have been eradicated, causing Guest's country's " +
                    "technological prowess to quickly dwarf that of User's. There now remains" +
                    " one sole superpower. Guest wins!";
        }
        else if (info.getPlayer2NumSpies().equals(0)) {
            return "All spies in Guest's country have been eradicated, causing User's country's " +
                    "technological prowess to quickly dwarf that of Guest's. There now remains" +
                    " one sole superpower. User wins!";
        }

        else if (info.isPlayer1BaseInfiltrated()){
            return "User's capital has been infiltrated by Guest's spies. All secrets have been " +
                    "stolen, rendering User defenseless against Guest. Guest wins!";
        }
        else {
            return "Guest's capital has been infiltrated by User's spies. All secrets have been " +
                    "stolen, rendering Guest defenseless against User. User wins!";
        }
    }

    /**
     * Return the winner of the game.
     *
     * Precondition: The game is ended.
     *
     * @param info Information about the current game
     * @return the winner of the current game.
     */
    public static String getWinner(ColdWarGameInfo info) {
        if (info.getPlayer1Reputation().equals(0)){
            return ColdWarGameInfo.PLAYER2;
        }
        else if (info.getPlayer2Reputation().equals(0)) {
            return ColdWarGameInfo.PLAYER1;
        }
        else if (info.getPlayer1NumSpies().equals(0)) {
            return ColdWarGameInfo.PLAYER2;
        }
        else if (info.getPlayer2NumSpies().equals(0)) {
            return ColdWarGameInfo.PLAYER1;
        }

        else if (info.isPlayer1BaseInfiltrated()){
            return ColdWarGameInfo.PLAYER2;
        }
        else {
            return ColdWarGameInfo.PLAYER1;
        }
    }

    /**
     * Takes in an Integer from and Integer to and decide if they are neighbouring each other on the
     * board.
     *
     * @param from one of the positions to be compared
     * @param to   the other position to be compared
     * @return whether they are neighbouring orthogonally
     */
    public static boolean isNeighbouring(Integer from, Integer to) {
        List<Integer> neighbours = new ArrayList<>();

        // Add the left neighbour to neighbours, if there is one.
        if (from % 6 != 0) {
            neighbours.add(from - 1);
        }

        // Add right neighbour to neighbours, if there is one.
        if (from % 6 != 5) {
            neighbours.add(from + 1);
        }

        // Add top neighbour to neighbours, if there is one.
        if (from / 6 != 0) {
            neighbours.add(from - 6);
        }

        // Add bottom neighbour to neighbours, if there is one.
        if (from / 6 != 5) {
            neighbours.add(from + 6);
        }

        // Check if to is a neighbour
        return neighbours.contains(to);
    }

    /**
     * Takes in an Integer move and relevant information about the state of the current game and
     * decide whether move is valid.
     *
     * @param info The information about the current state of the game
     * @param from position to move from
     * @param to   position to move to
     * @return whether the move is valid
     */
    public static boolean isValidMove(ColdWarGameInfo info, Integer from, Integer to) {

        List<Tile> board = info.getBoard();
        Agent fromOccupant = board.get(from).getAgent();
        Agent toOccupant = board.get(to).getAgent();

        // check if from is a not null
        if (fromOccupant == null) {
            return false;
        }

        // check if from piece can move
        if (! fromOccupant.isCanMove()){
            return false;
        }

        // check if from and to are neighbouring
        if (!isNeighbouring(from, to)) {
            return false;
        }

        // check if from is owned by current player
        if (!fromOccupant.getOwner().equals(info.getCurrentPlayer())) {
            return false;
        }

        // check if diplomat is trying to steal information from base (this is not allowed)
        if (fromOccupant instanceof Diplomat &&
                ((toOccupant instanceof SUBase |
                        toOccupant instanceof USBase))) {
            return false;
        }

        // check if to is owned by the other player
        return (!(toOccupant != null && toOccupant.getOwner().equals(info.getCurrentPlayer())));
    }

    /**
     * Perform action on receiver based on performer
     *
     * @param info     information about the current state of the game
     * @param receiver the Agent receiving the action
     *
     * Precondition: performer and receiver are not null and performance is legal.
     */
    private static void performAction(ColdWarGameInfo info, Agent receiver) {
        String currentPlayer = info.getCurrentPlayer();

        // performing player loses reputation if action is performed on an enemy diplomat
        if (receiver instanceof Diplomat) {
            if (currentPlayer.equals(ColdWarGameInfo.PLAYER2)) {
                info.setPlayer2Reputation(info.getPlayer2Reputation() - 1);
            } else {
                info.setPlayer1Reputation(info.getPlayer1Reputation() - 1);
            }
        }

        // update the number of spies if a spy has been performed on
        else if (receiver instanceof Spy) {
            if (currentPlayer.equals(ColdWarGameInfo.PLAYER2)) {
                info.setPlayer1NumSpies(info.getPlayer1NumSpies() - 1);
            } else {
                info.setPlayer2NumSpies(info.getPlayer2NumSpies() - 1);
            }
        }

        // update variable isBaseInfiltrated in info if a base has been infiltrated (by a spy)
        else if (receiver instanceof USBase) {
            info.setPlayer1BaseInfiltrated(true);
        }
        else if (receiver instanceof SUBase) {
            info.setPlayer2BaseInfiltrated(true);
        }
    }

    /**
     * Move the agent at selectedPosition to positionToMove
     *
     * @param info             Information about the current state of the game
     * @param selectedPosition The position of the agent to move
     * @param positionToMove   The position of where we want the given agent to move to
     * @return Whether a move was successfully made.
     */
    public static boolean makeMove(ColdWarGameInfo info, int selectedPosition, int positionToMove) {
        List<Tile> board = info.getBoard();
        Agent fromOccupant = board.get(selectedPosition).getAgent();
        Agent toOccupant = board.get(positionToMove).getAgent();

        if (isValidMove(info, selectedPosition, positionToMove)) {

            // move to positionToMove
            board.get(positionToMove).setAgent(fromOccupant);
            board.get(selectedPosition).setAgent(null);

            // perform action on occupant at positionToMove if possible
            if (toOccupant != null) {
                performAction(info, toOccupant);
            }

            // set all pieces to be unmovable
            toggleMovability(info);

            return true;
        }

        // no valid move was made
        return false;
    }

    /**
     * Return a list of imageIDs essential for displaying a visual representation of board.
     *
     * @param coldWarGameInfo Information used to generate imageIDs list.
     * @return A list of integers corresponding to imageIDs in the drawables folder based on the
     * information in coldWarGameInfo's board.
     */
    static List<Integer> getImageIDs(ColdWarGameInfo coldWarGameInfo) {
        List<Integer> IDs = new ArrayList<>();
        List<Tile> board = coldWarGameInfo.getBoard();

        for (int i = 0; i < board.size(); i++) {
            Agent occupant = board.get(i).getAgent();
            if (occupant == null) {
                IDs.add(R.drawable.cold_war_blank_tile);
            }
            else if (occupant.getOwner().equals(coldWarGameInfo.getCurrentPlayer())
                    && occupant.isVisible()) {
                IDs.add(occupant.getPicture());
            } else {
                IDs.add(R.drawable.unknown);
            }
        }

        return IDs;
    }

    /**
     * Makes all pieces in info movable and makes visibility anonymous for all. Switch current player
     * to other player.
     * @param info The game info of the current game
     */
    static void endTurn(ColdWarGameInfo info) {
        String currentPlayer = info.getCurrentPlayer();

        makeInvisible(info);

        if (currentPlayer.equals(ColdWarGameInfo.PLAYER1)) {
            info.setCurrentPlayer(ColdWarGameInfo.PLAYER2);
        } else {
            info.setCurrentPlayer(ColdWarGameInfo.PLAYER1);
        }
    }

    /**
     * Makes visibility visible if piece is owned by current player.
     * @param info The game info of the current game
     */
    static void beginTurn(ColdWarGameInfo info) {

        // Allow pieces to be movable
        toggleMovability(info);
        makeVisible(info);
    }

    /**
     * Makes all pieces in info that are owned by the current player visible if invisible and vice
     * versa.
     * @param info The game info of the current game
     */
    static private void toggleVisibility(ColdWarGameInfo info) {
        List<Tile> board = info.getBoard();
        for (int i = 0; i < board.size(); i++){
            Agent occupant = board.get(i).getAgent();
            if (! (occupant == null)){
                if (occupant.isVisible()){
                    occupant.setVisible(false);
                }
                else {
                    occupant.setVisible(true);
                }
            }
        }
    }

    /**
     * Makes all pieces visible.
     * @param info The game info of the current game
     */
    static private void makeVisible(ColdWarGameInfo info) {
        List<Tile> board = info.getBoard();
        for (int i = 0; i < board.size(); i++){
            Agent occupant = board.get(i).getAgent();
            if (! (occupant == null)){
                occupant.setVisible(true);
            }
        }
    }

    /**
     * Makes all pieces invisible.
     * @param info The game info of the current game
     */
    static private void makeInvisible(ColdWarGameInfo info) {
        List<Tile> board = info.getBoard();
        for (int i = 0; i < board.size(); i++){
            Agent occupant = board.get(i).getAgent();
            if (! (occupant == null)){
                occupant.setVisible(false);
            }
        }
    }

    /**
     * Makes all playable Agent pieces in info unmovable if movable and vice versa.
     * @param info The game info of the current game
     */
    static private void toggleMovability(ColdWarGameInfo info) {
        List<Tile> board = info.getBoard();
        for (int i = 0; i < board.size(); i++) {
            Agent occupant = board.get(i).getAgent();
            if (occupant instanceof Spy | occupant instanceof Diplomat){
                if (occupant.isCanMove()) {
                    occupant.setCanMove(false);
                }
                else {
                    occupant.setCanMove(true);
                }
            }
        }
    }

    /**
     * Show a popup with message.
     * @param message Message to show
     * @param con The context
     */
    public static void showAlert(String message, Context con) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(con);
        TextView myMessage = new TextView(con);
        myMessage.setText(message);
        myMessage.setTextSize(20);
        myMessage.setGravity(Gravity.CENTER_HORIZONTAL);
        dialog.setView(myMessage);
        dialog.show();


//        Dialog d = dialog.setView(new View(con)).create();
//
//        dialog.setTitle(message);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        dialog.show();
//        d.getWindow().setAttributes(lp);
    }
}
