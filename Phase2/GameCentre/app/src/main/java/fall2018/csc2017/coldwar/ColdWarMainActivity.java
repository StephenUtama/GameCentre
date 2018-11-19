package fall2018.csc2017.coldwar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import fall2018.csc2017.slidingtiles.R;

public class ColdWarMainActivity extends AppCompatActivity {

    GridView gridView;

    List<Integer> imageIDs;

    int selectedPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cold_war_main);
        ColdWarGameInfo coldWarGameInfo = new ColdWarGameInfo();
        imageIDs = coldWarGameInfo.getImageIDs();

        gridView = findViewById(R.id.coldWarGridView);
        gridView.setAdapter(new ImageAdapterGridView(this, imageIDs));


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (selectedPosition == -1){
                    int selectedPosition = position;
                }
                else {
                    int positionToMove = position;
                }

                Toast.makeText(ColdWarMainActivity.this, "Position is " +
                        position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
