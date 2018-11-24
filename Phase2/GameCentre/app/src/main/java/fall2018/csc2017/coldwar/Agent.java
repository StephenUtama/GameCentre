package fall2018.csc2017.coldwar;

public abstract class Agent {
    private String owner;

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public abstract int getPicture();
}
