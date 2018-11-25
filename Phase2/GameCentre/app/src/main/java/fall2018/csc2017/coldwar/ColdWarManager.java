package fall2018.csc2017.coldwar;

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
                info.isBaseInfiltrated | info.getPlayer1NumSpies().equals(0) |
                info.getPlayer2NumSpies().equals(0));
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
        else if (receiver.getClass().getName().equals("USBase") | receiver.getClass().getName().equals("SUBase")) {
            info.isBaseInfiltrated = true;
        }
    }

    /**
     * Move the agent at selectedPosition to positionToMove
     *
     * @param info             Information about the current state of the game
     * @param selectedPosition The position of the agent to move
     * @param positionToMove   The position of where we want the given agent to move to
     */
    public static void makeMove(ColdWarGameInfo info, int selectedPosition, int positionToMove) {
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
        }
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

        toggleMovability(info);
        toggleVisibility(info);

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
        toggleVisibility(info);
    }

    /**
     * Makes all pieces in info that are owned by the current player visible if invisible and vice
     * versa.
     * @param info The game info of the current game
     */
    static void toggleVisibility(ColdWarGameInfo info) {
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
     * Makes all playable Agent pieces in info unmovable if movable and vice versa.
     * @param info The game info of the current game
     */
    static void toggleMovability(ColdWarGameInfo info) {
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
}
