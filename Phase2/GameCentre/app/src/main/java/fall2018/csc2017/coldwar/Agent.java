package fall2018.csc2017.coldwar;

import java.io.Serializable;

public abstract class Agent implements Serializable {

    private String owner;

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public abstract int getPicture();
}