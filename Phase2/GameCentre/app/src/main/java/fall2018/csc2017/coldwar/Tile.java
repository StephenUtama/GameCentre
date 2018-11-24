package fall2018.csc2017.coldwar;

import fall2018.csc2017.slidingtiles.R;

public class Tile {
    private Agent agent;

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

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
