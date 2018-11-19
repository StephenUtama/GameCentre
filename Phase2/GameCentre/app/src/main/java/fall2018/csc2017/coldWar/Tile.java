package fall2018.csc2017.coldWar;

import fall2018.csc2017.slidingtiles.R;

public class Tile {
    Agent agent;

    public Tile(Agent agent){
        this.agent = agent;
    }

    public int getPicture() {
        if (this.agent == null) {
            return R.drawable.cold_war_blank_tile;
        }
        return agent.getPicture();
    }
}
