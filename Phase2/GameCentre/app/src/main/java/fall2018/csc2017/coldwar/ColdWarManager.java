package fall2018.csc2017.coldwar;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.R;
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

    static List<Integer> getImageIDs(ColdWarGameInfo coldWarGameInfo){
        List<Integer> IDs = new ArrayList<>();

        for (int i = 0; i < coldWarGameInfo.getBoard().size(); i++) {
            if (coldWarGameInfo.getBoard().get(i).getAgent().getOwner().equals(coldWarGameInfo.getCurrentPlayer())) {
                IDs.add(coldWarGameInfo.getBoard().get(i).getPicture());
            }
            else {
                IDs.add(R.drawable.unknown);
            }
        }

        return IDs;
    }
}
