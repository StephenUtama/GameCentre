package fall2018.csc2017.coldwar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import fall2018.csc2017.slidingtiles.R;

public class ColdWarMainActivity extends AppCompatActivity {

    GridView gridView;

    List<Integer> imageIDs;

    int selectedPosition = -1; // this is "unselected" by default


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cold_war_main);

        Intent intent = getIntent();
        final ColdWarGameInfo coldWarGameInfo = (ColdWarGameInfo) intent.getSerializableExtra("gameInfo");
        List<Tile> board = coldWarGameInfo.getBoard();
        imageIDs = ColdWarManager.getImageIDs(coldWarGameInfo);

        gridView = findViewById(R.id.coldWarGridView);
        gridView.setAdapter(new ImageAdapterGridView(this, imageIDs));


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedPosition == -1){
                    selectedPosition = position;
                }
                else {
                    ColdWarManager.makeMove(coldWarGameInfo, selectedPosition, position);
                    selectedPosition = -1; // this indicates that selectedPosition is reset to "unselected"
                }
                imageIDs = ColdWarManager.getImageIDs(coldWarGameInfo);

                gridView.setAdapter(new ImageAdapterGridView(getBaseContext(), imageIDs));

                Toast.makeText(ColdWarMainActivity.this, "Position is " +
                        position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
