package fall2018.csc2017.coldwar;
import java.util.ArrayList;
import java.util.List;

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

    static List<Integer> getImageIDs(List<Tile> board){
        List<Integer> IDs = new ArrayList<>();

        for (int i = 0; i < board.size(); i++) {
            IDs.add(board.get(i).getPicture());
        }

        return IDs;
    }
}
