package fall2018.csc2017.coldwar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import fall2018.csc2017.slidingtiles.R;

public class InstructionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        TextView instructions = findViewById(R.id.instructions);
        instructions.setMovementMethod(new ScrollingMovementMethod());
    }
}
