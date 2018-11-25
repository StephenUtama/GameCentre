package fall2018.csc2017.coldwar;
import java.util.ArrayList;
import java.util.List;
import fall2018.csc2017.slidingtiles.R;

public class ColdWarManager {

    /**
     * Determines whether the game in its current state is over.
     * @return wheher the game is over.
     */
    public boolean isOver(ColdWarGameInfo info) {
        return false;
    }

    /**
     * Takes in an Integer from and Integer to and decide if they are neighbouring each other on the
     * board.
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

//    /**
//     * Takes in an Integer move and relevant information about the state of the current game and
//     * decide whether move is valid.
//     * @param info The information about the current state of the game
//     * @param from position to move from
//     * @param to position to move to
//     * @return whether the move is valid
//     */
//    public boolean isValidMove(ColdWarGameInfo info, Integer from, Integer to) {
//        List<Tile> board = info.getBoard();
//        if (isNeighbouring(from, to)){
//            if (board.get(to).getAgent().getOwner() != info.getCurrentPlayer()){
//                if ()
//            }
//        }
//    }


    /** Move the agent at selectedPosition to positionToMove
     * @param selectedPosition The position of the agent to move
     * @param positionToMove The position of where we want the given agent to move to
     */
    public void makeMove(int selectedPosition, int positionToMove) {
    }

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
