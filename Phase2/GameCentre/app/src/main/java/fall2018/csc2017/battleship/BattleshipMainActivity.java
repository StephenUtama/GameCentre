package fall2018.csc2017.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import fall2018.csc2017.slidingtiles.R;

public class BattleshipMainActivity extends AppCompatActivity {

    GridView gridView;

    Integer list[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battleship_main);

        gridView = findViewById(R.id.battleshipGridView);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(BattleshipMainActivity.this,
                android.R.layout.simple_list_item_1, list);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(BattleshipMainActivity.this, "Position is " +
                        position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
