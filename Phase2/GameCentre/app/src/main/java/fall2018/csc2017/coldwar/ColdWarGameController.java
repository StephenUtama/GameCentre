package fall2018.csc2017.coldwar;

import android.content.Context;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ColdWarGameController {
    private int selectedPosition;
    Button endButton;
    TextView guestReputationText;
    TextView userReputationText;
    Context context;

    public ColdWarGameController(Context context) {
        this.context = context;
    }

    public void touchMove(ColdWarGameInfo coldWarGameInfo, int selectedPosition, int position) {
        if (selectedPosition == -1){
            this.selectedPosition = position;
        }
        else {
            if ( ColdWarManager.makeMove(coldWarGameInfo, selectedPosition, position)) {
                endButton.setEnabled(true);
            }
            else {
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

    public void updateGridView(GridView gridView, ColdWarGameInfo coldWarGameInfo) {
        List<Integer> imageIDs = ColdWarManager.getImageIDs(coldWarGameInfo);
        gridView.setAdapter(new ImageAdapterGridView(context, imageIDs));
    }

    public void setViews(Button endButton, TextView guestReputationText, TextView userReputationText) {
        this.endButton = endButton;
        this.guestReputationText = guestReputationText;
        this.userReputationText = userReputationText;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}
