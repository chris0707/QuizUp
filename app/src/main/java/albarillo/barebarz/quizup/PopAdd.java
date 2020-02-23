package albarillo.barebarz.quizup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PopAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_add);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        getWindow().setLayout((int)(width*.85),(int)(height*.9));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 0;

        getWindow().setAttributes(params);

        Button addButton = findViewById(R.id.addB);

        final TextView titleText = findViewById(R.id.titleTV);

        final EditText questionEditText = findViewById(R.id.questionET);
        final EditText answerEditText = findViewById(R.id.answerET);

        String quizTitle = "";
        quizTitle = getIntent().getStringExtra("title1");
        titleText.setText(quizTitle);

        /**Add title to the quizList and create section by section to determine
         * sets of quizzes.
         * Question is, is it possible to create multiple sets of quizzes in out text file??
         * Test it first.
         *
         *-- Logic for ListView Quizes with the sets of 5
         * For loop of 5
         *
         * String title = title[0].toString;
         *
         * if file.Title[0].equals(title)
         * arraylist.add[0]
         *
         *
         *
         */


        final String filename = "quizList.txt";

        File file = new File(getApplicationContext().getFilesDir(), filename);

        final String previousClass = getIntent().getStringExtra("PrevClass");
        final String title2 = getIntent().getStringExtra("title");


        final String finalQuizTitle = quizTitle;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1 = finalQuizTitle;
                String question1 = "";
                String answer1 = "";


                FileOutputStream fileOutputStream;

                question1 = questionEditText.getText().toString();
                answer1 = answerEditText.getText().toString();

                //titleText.setText(title1 + " Hello");


                    if (previousClass.equals("ModActivity")) {

                        try {

                            String qaList = "";

                            qaList = question1 + "|" + answer1 + "|" + title2 + "\n";
                            fileOutputStream = openFileOutput(filename, MODE_APPEND);
                            fileOutputStream.write(qaList.getBytes());
                            fileOutputStream.flush();
                            fileOutputStream.close();

                        } catch (IOException e) {

                            e.printStackTrace();

                        }

                    } else{

                        try {

                            String qaList = "";

                            qaList = question1 + "|" + answer1 + "|" + title1 + "\n";
                            fileOutputStream = openFileOutput(filename, MODE_APPEND);
                            fileOutputStream.write(qaList.getBytes());
                            fileOutputStream.flush();
                            fileOutputStream.close();

                            Toast.makeText(getApplicationContext(),"QA Added",Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {

                            e.printStackTrace();

                        }
                    }

                    finish();
                //Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();



            }
        });







    }
}
