package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Toast;

import generalclasses.GameController;
import generalclasses.User;


class SlidingTilesGameController implements GameController {

    private SlidingTilesManager slidingTilesManager = null;

    SlidingTilesGameController() {
    }

    void setSlidingTilesManager(SlidingTilesManager slidingTilesManager) {
        this.slidingTilesManager = slidingTilesManager;
    }

    void processTapMovement(Context context, int position, boolean display) {
        if (slidingTilesManager.isValidMove(position)) {

            // save the previous move into previousMovesList
            slidingTilesManager.savePreviousMovePosition(this.getPositionToMoveTo());

            // Make move
            slidingTilesManager.makeMove(position);


            if (slidingTilesManager.isOver()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else { // if tap is not valid
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Determine the position the current tap moves to, if valid, and return it.
     *
     * @return the position to move to.
     */
    private int getPositionToMoveTo() {
        int[] newCoordinatePosition;
        int newPosition;

        // get coordinate position of blank tile
        newCoordinatePosition = slidingTilesManager.findBlankTile(25);

        // get standard position of blank tile
        newPosition = SlidingTilesManager.convertCoordinateToPosition(newCoordinatePosition);

        return newPosition;
    }
}