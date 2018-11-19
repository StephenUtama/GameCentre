package fall2018.csc2017.coldWar;

public abstract class Agent {
    String owner;

    public abstract void attack(Agent agent);
    public abstract int getPicture();
}
