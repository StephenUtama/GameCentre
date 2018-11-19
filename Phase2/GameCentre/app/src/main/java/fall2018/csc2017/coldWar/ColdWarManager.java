package fall2018.csc2017.coldWar;
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
}
