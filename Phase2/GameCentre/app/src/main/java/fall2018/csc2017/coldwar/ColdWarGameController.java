package fall2018.csc2017.coldwar;

import android.content.Context;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.R;

class ColdWarGameController {
    private int selectedPosition;
    private Button endButton;
    private TextView guestReputationText;
    private TextView userReputationText;
    private Context context;

    ColdWarGameController(Context context) {
        this.context = context;
    }

    void touchMove(ColdWarGameInfo coldWarGameInfo, int selectedPosition, int position) {
        if (selectedPosition == -1) {
            this.selectedPosition = position;
        } else {
            if (MovementUtility.makeMove(coldWarGameInfo, selectedPosition, position)) {
                endButton.setEnabled(true);
            } else {
                Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show();
            }
            this.selectedPosition = -1; // this indicates that selectedPosition is reset to "unselected"
            updateUserDisplay(coldWarGameInfo);
        }
    }

    private void updateUserDisplay(ColdWarGameInfo coldWarGameInfo) {
        String guestReputationString = "Guest Global Reputation: " +
                coldWarGameInfo.getPlayer2Reputation().toString();
        String userReputationString = "User Global Reputation: " +
                coldWarGameInfo.getPlayer1Reputation().toString();
        guestReputationText.setText(guestReputationString);
        userReputationText.setText(userReputationString);
    }

    void updateGridView(GridView gridView, ColdWarGameInfo coldWarGameInfo) {
        List<Integer> imageIDs = getImageIDs(coldWarGameInfo);
        gridView.setAdapter(new ImageAdapterGridView(context, imageIDs));
    }

    void setViews(Button endButton, TextView guestReputationText, TextView userReputationText) {
        this.endButton = endButton;
        this.guestReputationText = guestReputationText;
        this.userReputationText = userReputationText;
    }

    int getSelectedPosition() {
        return selectedPosition;
    }

    /**
     * Return a list of imageIDs essential for displaying a visual representation of board.
     *
     * @param coldWarGameInfo Information used to generate imageIDs list.
     * @return A list of integers corresponding to imageIDs in the drawables folder based on the
     * information in coldWarGameInfo's board.
     */
    static List<Integer> getImageIDs(ColdWarGameInfo coldWarGameInfo) {
        List<Integer> IDs = new ArrayList<>();
        List<Tile> board = coldWarGameInfo.getBoard();

        for (int i = 0; i < board.size(); i++) {
            Agent occupant = board.get(i).getAgent();
            if (occupant == null) {
                IDs.add(R.drawable.cold_war_blank_tile);
            } else if (occupant instanceof USBase | occupant instanceof  SUBase) {
                // Bases are shown regardless of visibility
                IDs.add(occupant.getPicture());
            }
            else if (occupant.getOwner().equals(coldWarGameInfo.getCurrentPlayer())
                    && occupant.isVisible()) {
                IDs.add(occupant.getPicture());
            } else {
                IDs.add(R.drawable.unknown);
            }
        }

        return IDs;
    }
}
