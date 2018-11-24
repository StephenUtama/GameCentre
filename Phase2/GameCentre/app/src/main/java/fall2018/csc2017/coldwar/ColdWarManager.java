package fall2018.csc2017.coldwar;
import generalclasses.Manager;

public class ColdWarManager extends Manager {
    @Override
    public boolean isOver() {
        return false;
    }

    @Override
    public boolean isValidMove(Object move) {
        return false;
    }

    @Override
    public void makeMove(Object move) {
        // just to satisfy parent

    }

    /** Move the agent at selectedPosition to positionToMove
     * @param selectedPosition The position of the agent to move
     * @param positionToMove The position of where we want the given agent to move to
     */
    public void makeMove(int selectedPosition, int positionToMove) {
    }
}
