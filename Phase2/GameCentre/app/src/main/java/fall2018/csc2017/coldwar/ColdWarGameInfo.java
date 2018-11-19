package fall2018.csc2017.coldwar;

import java.util.List;

import generalclasses.GameInfo;

public class ColdWarGameInfo extends GameInfo {
    List<Tile> board;
    int userReputation;
    int guestReputation;

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
