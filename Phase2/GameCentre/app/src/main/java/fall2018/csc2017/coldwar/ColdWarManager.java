package fall2018.csc2017.coldwar;

import java.util.ArrayList;
import java.util.List;
import fall2018.csc2017.slidingtiles.R;

public class ColdWarManager {

    /**
     * Determines whether the game in its current state is over.
     * @return whether the game is over.
     */
    public boolean isOver(ColdWarGameInfo info) {
        return (info.getPlayer1Reputation().equals(0) | info.getPlayer2Reputation().equals(0) |
        info.isBaseInfiltrated | info.getPlayer1NumSpies().equals(0) |
        info.getPlayer2NumSpies().equals(0));
    }

    /**
     * Takes in an Integer from and Integer to and decide if they are neighbouring each other on the
     * board.
     *
     * @param from one of the positions to be compared
     * @param to the other position to be compared
     * @return whether they are neighbouring orthogonally
     */
    public boolean isNeighbouring(Integer from, Integer to) {
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
     * @param info The information about the current state of the game
     * @param from position to move from
     * @param to position to move to
     * @return whether the move is valid
     */
    public boolean isValidMove(ColdWarGameInfo info, Integer from, Integer to) {

        List<Tile> board = info.getBoard();
        Agent fromOccupant = board.get(from).getAgent();
        Agent toOccupant = board.get(to).getAgent();

        // check if from and to are neighbouring
        if (!isNeighbouring(from, to)) {
            return false;
        }

        // check if from is a valid piece and owned by current player
        if (fromOccupant == null | !fromOccupant.getOwner().equals(info.getCurrentPlayer())) {
            return false;
        }

        // check if diplomat is trying to steal information from base (this is not allowed)
        if (fromOccupant.getClass().getName().equals("Diplomat") &&
                (toOccupant.getClass().getName().equals("SUBase") |
                        toOccupant.getClass().getName().equals("USBase"))) {
            return false;
        }

        // check if to is owned by the other player
        return (! (toOccupant != null && toOccupant.getOwner().equals(info.getCurrentPlayer())));
    }

    /**
     * Perform action on receiver based on performer
     * @param info information about the current state of the game
     * @param receiver the Agent receiving the action
     *
     * Precondition: performer and receiver are not null and performance is legal.
     */
    private void performAction(ColdWarGameInfo info, Agent receiver){
        String currentPlayer = info.getCurrentPlayer();

        // performing player loses reputation if action is performed on an enemy diplomat
        if (receiver.getClass().getName().equals("Diplomat")) {
            if (currentPlayer.equals(ColdWarGameInfo.PLAYER2)){
                info.setPlayer2Reputation(info.getPlayer2Reputation() - 1);
            }
            else {
                info.setPlayer1Reputation(info.getPlayer1Reputation() - 1);
            }
        }

        // update the number of spies if spies have been performed on
        else if (receiver.getClass().getName().equals("Spy")) {
            if (currentPlayer.equals(ColdWarGameInfo.PLAYER2)) {
                info.setPlayer1NumSpies(info.getPlayer1NumSpies() - 1);
            }
            else {
                info.setPlayer2NumSpies(info.getPlayer2NumSpies() - 1);
            }
        }

        // update variable isBaseInfiltrated in info if a base has been infiltrated (by a spy)
        else if (receiver.getClass().getName().equals("USBase") | receiver.getClass().getName().equals("SUBase")){
            info.isBaseInfiltrated = true;
        }
    }

    /** Move the agent at selectedPosition to positionToMove
     * @param info Information about the current state of the game
     * @param selectedPosition The position of the agent to move
     * @param positionToMove The position of where we want the given agent to move to
     */
    public void makeMove(ColdWarGameInfo info, int selectedPosition, int positionToMove) {
        List<Tile> board = info.getBoard();
        Agent fromOccupant = board.get(selectedPosition).getAgent();
        Agent toOccupant = board.get(positionToMove).getAgent();

        if (isValidMove(info, selectedPosition, positionToMove)){

            // move to positionToMove
            board.get(positionToMove).setAgent(fromOccupant);
            board.get(selectedPosition).setAgent(null);

            // perform action on occupant at positionToMove if possible
            if (toOccupant != null){
                performAction(info, toOccupant);
            }
        }
    }

    /**
     * Return a list of imageIDs essential for displaying a visual represtation of board.
     * @param coldWarGameInfo Information used to generate imageIDs list.
     * @return A list of integers corresponding to imageIDs in the drawables folder based on the
     *         information in coldWarGameInfo's board.
     */
    static List<Integer> getImageIDs(ColdWarGameInfo coldWarGameInfo){
        List<Integer> IDs = new ArrayList<>();
        List<Tile> board = coldWarGameInfo.getBoard();

        for (int i = 0; i < board.size(); i++) {
            if (board.get(i).getAgent() == null) {
                IDs.add(R.drawable.cold_war_blank_tile);
            }
            else if (board.get(i).getAgent().getOwner() == null){
                IDs.add(board.get(i).getAgent().getPicture());
            }
            else if (board.get(i).getAgent().getOwner().equals(coldWarGameInfo.getCurrentPlayer())) {
                IDs.add(board.get(i).getAgent().getPicture());
            }
            else {
                IDs.add(R.drawable.unknown);
            }
        }

        return IDs;
    }
}
