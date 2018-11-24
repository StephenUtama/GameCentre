package fall2018.csc2017.slidingtiles;

import java.io.Serializable;

import generalclasses.GameScoreboards;

public class SlidingTileScoreboards extends GameScoreboards implements Serializable{
    public String game;
    SlidingTileScoreboards() {
        this.game = "Sliding Tiles";
    }
}
