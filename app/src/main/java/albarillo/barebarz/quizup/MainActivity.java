package albarillo.barebarz.quizup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button createB = findViewById(R.id.createButton);
        Button viewB = findViewById(R.id.viewButton);
        Button quizb = findViewById(R.id.quizButton);

        createB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), CreateActivity.class));

            }
        });


        viewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewActivity.class));
            }
        });


        quizb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TitlesActivity.class));
                //In quizB. ListView same as ViewB. Identifier?? Append in the first title.
            }
        });





    }
}
