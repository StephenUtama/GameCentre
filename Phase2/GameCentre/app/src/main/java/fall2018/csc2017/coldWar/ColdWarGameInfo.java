package fall2018.csc2017.coldwar;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import generalclasses.GameInfo;

public class ColdWarGameInfo extends GameInfo {
    List<Tile> board;
    int userReputation;
    int guestReputation;

    public ColdWarGameInfo() {
        board = new ArrayList<>();
        setUpBlankBoard();
    }

    private void setUpBlankBoard() {
        for (int i = 0; i < 36; i++) {
            Tile newTile = new Tile(null);
            board.add(newTile);
        }
    }

    public List<Integer> getImageIDs() {
        List<Integer> IDs = new ArrayList<>();

        for (int i = 0; i < board.size(); i++) {
            IDs.add(board.get(i).getPicture());
        }

        return IDs;
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
        return null;
    }
}
