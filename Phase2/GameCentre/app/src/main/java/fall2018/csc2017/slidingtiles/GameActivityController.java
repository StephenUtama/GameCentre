package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Controller for GameActivity
 */
public class GameActivityController {
    Context context;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;


    public GameActivityController(Context context) {
        this.context = context;
    }

    public ArrayList<Button> createTileButtons(boolean image_game, Board board) {
        tileButtons = new ArrayList<>();

        if (image_game){ // If the complexity is image, creates buttons differently
            int num;
            for (int row = 0; row != Board.NUM_ROWS; row++) {
                for (int col = 0; col != Board.NUM_COLS; col++) {
                    num = board.getTile(row,col).getId()-1;
                    helperCreatingButton(num, row, col, board);
                }
            }
            return tileButtons;
        } else {
            for (int row = 0; row != Board.NUM_ROWS; row++) {
                for (int col = 0; col != Board.NUM_COLS; col++) {
                    Button tmp = new Button(context);
                    tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                    this.tileButtons.add(tmp);
                }
            }
            return tileButtons;
        }
    }

    /**
     * Helper function for creating a button when complexity is image
     * @param num id of the tile
     * @param row row of the creating tile
     * @param col col of the creating tile
     */
    public void helperCreatingButton(int num, int row, int col, Board board) {
        Button tmp = new Button(context);
        if (num != 24) {
            BitmapDrawable bmp = new BitmapDrawable(GameActivity.backgrounds[num]);
            tmp.setBackground(bmp);
            this.tileButtons.add(tmp);
        } else {
            tmp.setBackgroundResource(board.getTile(row, col).getBackground());
            this.tileButtons.add(tmp);
        }
    }

    /**
     * Update the button's background
     * @param tilesbtn List of buttons that we assign the background
     * @param image_game If game is Image game or not
     * @param board The board of this game that we pug the buttons
     */
    public void updateTileButtons(ArrayList<Button> tilesbtn, boolean image_game, Board board) {

        if (image_game) { // If the complexity is image, updates buttons differently
            int next = 0;
            for (Button b : tilesbtn) {
                helperUpdate(b, next, board);
                next++;
            }
        } else {
            int nextPos = 0;
            for (Button b : tilesbtn) {
                int row = nextPos / Board.NUM_ROWS;
                int col = nextPos % Board.NUM_COLS;
                b.setBackgroundResource(board.getTile(row, col).getBackground());
                nextPos++;
            }
        }
    }

    /**
     * Helper function for updating the background of the tile when complexity is image
     * @param b current button that is being updated
     * @param next the position of the tile that is being updated
     */
    public void helperUpdate(Button b, int next, Board board){
        int row = next / Board.NUM_ROWS;
        int col = next % Board.NUM_COLS;
        int num = board.getTile(row, col).getId() - 1;
        if(num != 24){
            BitmapDrawable bmp = new BitmapDrawable(GameActivity.backgrounds[num]);
            b.setBackground(bmp);
        }else{
            b.setBackgroundResource(board.getTile(row, col).getBackground());
        }
    }

    public void undoBtn(int numPreviousMoves, SlidingTilesManager slidingTilesManager) {
        if ((numPreviousMoves) != 0) { // check whether any moves were made
            int previousMove = slidingTilesManager.returnPreviousMove();
            slidingTilesManager.makeMove(previousMove);
        }
    }
}
