package fall2018.csc2017.coldwar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fall2018.csc2017.slidingtiles.R;
import generalclasses.GameInfo;

public class UserPiecesSelectionActivity extends AppCompatActivity {

    ColdWarGameInfo gameInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pieces_selection);
        assignments();
    }

    private void assignments() {
        gameInfo = new ColdWarGameInfo();
    }

    /** Generate a board based on the submitted information and start guest piece choosing
     * activity.
     * @param view the Button
     */
    public void submitUserPieces(View view) {
        Intent intent = new Intent(this, GuestPiecesSelectionActivity.class);

        // get all data from the edit texts >> these data are coodinates of the board
        HashMap<String, ArrayList<String>> agentToPositionList = getAgentPositions();

        // add the spies to the board
        addAgents("spy", agentToPositionList);
        addAgents("diplomat", agentToPositionList);

        intent.putExtra("gameInfo", gameInfo);
        startActivity(intent);
    }

    /** Add agents of type agent to the positions dictated by agentToPositionList.
     * @param agent the type of agent
     * @param agentToPositionList a hash map that maps an agent type to the positions
     * that will contain this agent
     */
    private void addAgents(String agent, HashMap<String, ArrayList<String>> agentToPositionList) {
        // get position data >> then translate to array coordinates
        List<String> agentPositions = agentToPositionList.get(agent);
        for (String agentPosition : agentPositions) {
            // make a new spy object >> then insert it to gameinfo, passing in the spy and the coodinates
            int[] arrayCoordinates = getArrayCoordinatesFromPosition(agentPosition);
            Spy spy = new Spy(gameInfo.PLAYER1);
//            gameInfo.setTile(spy, arrayCoordinates);
        }
    }

    /**
     * @param agentPosition the position that the user inputs, e.g A4
     * @return the corresponding in "array terms"
     */
    private int[] getArrayCoordinatesFromPosition(String agentPosition) {
        int coord1 = (int) agentPosition.charAt(0);
        int coord2 = Integer.parseInt(String.valueOf(agentPosition.charAt(1))) - 1;
        int[] result = {coord1, coord2};
        return result;
    }

    private HashMap<String, ArrayList<String>> getAgentPositions() {
        // need to make sure input is valid
        EditText spy1Input = findViewById(R.id.spy1Input);
        EditText spy2Input = findViewById(R.id.spy2Input);
        EditText spy3Input = findViewById(R.id.spy3Input);
        EditText spy4Input = findViewById(R.id.spy4Input);

        EditText dip1Input = findViewById(R.id.dip1Input);
        EditText dip2Input = findViewById(R.id.dip2Input);
        EditText dip3Input = findViewById(R.id.dip3Input);
        EditText dip4Input = findViewById(R.id.dip4Input);

        return null;
    }
}

