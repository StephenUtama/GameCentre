package fall2018.csc2017.pong;

import java.io.Serializable;

import generalclasses.GameScoreboards;

public class PongGameScoreBoards extends GameScoreboards implements Serializable {
    public String game;
    PongGameScoreBoards() {
        this.game = "Pong";
    }
}
