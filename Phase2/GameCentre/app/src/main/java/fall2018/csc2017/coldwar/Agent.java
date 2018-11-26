package fall2018.csc2017.coldwar;

import java.io.Serializable;

public abstract class Agent implements Serializable {

    private String owner;

    /*
        Indicates whether this piece is visible.
         */
    public boolean isVisible = false;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /*
    Indicates whether this piece can move
     */
    public boolean canMove = true;

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public abstract int getPicture();
}