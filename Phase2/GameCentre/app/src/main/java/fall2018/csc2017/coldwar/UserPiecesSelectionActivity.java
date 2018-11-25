package fall2018.csc2017.coldwar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

        if (agentToPositionList != null) {
            // add the spies to the board
            addAgents("spy", agentToPositionList);
            addAgents("diplomat", agentToPositionList);

            intent.putExtra("gameInfo", gameInfo);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please enter valid inputs", Toast.LENGTH_SHORT).show();
        }

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
            int position = getPosition(agentPosition);
            Spy spy = new Spy(gameInfo.PLAYER1);
            gameInfo.setTile(spy, position);
        }
    }

    /**
     * @param agentPosition the position that the user inputs, e.g A4
     * @return the corresponding in "array terms"
     */
    private int getPosition(String agentPosition) {
        int position = 0;
        int coord1 = (int) agentPosition.charAt(0);
        int coord2 = Integer.parseInt(String.valueOf(agentPosition.charAt(1))) - 1;
        position = coord1 * 6 + coord2;

        return position;
    }

    private HashMap<String, ArrayList<String>> getAgentPositions() {
        ArrayList<String> spyPositions = getSpyPositions();
        ArrayList<String> diplomatPositions = getDiplomatPositions();

        boolean spyValid = checkValid(spyPositions);
        boolean diplomatValid = checkValid(diplomatPositions);

        if (spyValid && diplomatValid) { // valid
            HashMap<String, ArrayList<String>> result = new HashMap<>();
            result.put("spy", new ArrayList<String>());
            result.put("agent", new ArrayList<String>());

            return result;
        }
        return null;
    }

    private boolean checkValid(ArrayList<String> positions) {
        Character[] validLetters = {'A', 'B', 'C', 'D', 'E', 'F'};
        Integer[] validNumbers = {1, 2, 3, 4, 5, 6};

        if (positions.size() != 2) {
            return false;
        }

        // thus, length of string is 2
        for (String position : positions) {
            boolean firstCharValid = Arrays.asList(validLetters).contains(position.charAt(0));
            boolean secondCharValid = Arrays.asList(validNumbers).contains(position.charAt(1));
            boolean notValid = !firstCharValid || !secondCharValid;
            if (notValid) {
                return false;
            }
        }

        return true;
    }

    private ArrayList<String> getDiplomatPositions() {
        ArrayList<String> diplomatPositions = new ArrayList<>();

        EditText dip1Input = findViewById(R.id.dip1Input);
        EditText dip2Input = findViewById(R.id.dip2Input);
        EditText dip3Input = findViewById(R.id.dip3Input);
        EditText dip4Input = findViewById(R.id.dip4Input);

        diplomatPositions.add(dip1Input.getText().toString());
        diplomatPositions.add(dip2Input.getText().toString());
        diplomatPositions.add(dip3Input.getText().toString());
        diplomatPositions.add(dip4Input.getText().toString());

        return diplomatPositions;
    }

    private ArrayList<String> getSpyPositions() {
        ArrayList<String> spyPositions = new ArrayList<>();

        EditText spy1Input = findViewById(R.id.spy1Input);
        EditText spy2Input = findViewById(R.id.spy2Input);
        EditText spy3Input = findViewById(R.id.spy3Input);
        EditText spy4Input = findViewById(R.id.spy4Input);

        spyPositions.add(spy1Input.getText().toString());
        spyPositions.add(spy2Input.getText().toString());
        spyPositions.add(spy3Input.getText().toString());
        spyPositions.add(spy4Input.getText().toString());

        return spyPositions;
    }
}

