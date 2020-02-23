package albarillo.barebarz.quizup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.Scanner;

public class PopEdit extends AppCompatActivity {

    String rQuestion = "";
    String rAnswer = "";

    FileIO fileIO = new FileIO(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_edit);

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

        final TextView titleText = findViewById(R.id.titleTV);
        final TextView questionText = findViewById(R.id.questionTV);
        final TextView answerText = findViewById(R.id.answerTV);
        final EditText questionEditText = findViewById(R.id.questionET);
        final EditText answerEditText = findViewById(R.id.answerET);

        String question1 = "";
        String answer1 = "";

        String tempQuestion1 = "";
        String tempQuestion2 = "";
        String tempQuestion3 = "";
        String tempAnswer1 = "";
        String tempAnswer2 = "";
        String tempAnswer3 = "";




        String quizTitle = "";
        question1 = getIntent().getStringExtra("question1");
        answer1 = getIntent().getStringExtra("answer1");
        quizTitle = getIntent().getStringExtra("title1");



            Scanner s1 = new Scanner(question1).useDelimiter("[:]");
            tempQuestion1 = s1.next();
            tempQuestion2 = s1.next().trim();
            //tempQuestion3 = s1.next();

            Scanner s2 = new Scanner(answer1).useDelimiter("[:]");
            tempAnswer1 = s2.next();
            tempAnswer2 = s2.next().trim();
            //tempAnswer3 = s2.next();

        questionEditText.setText(tempQuestion2);
        answerEditText.setText(tempAnswer2);

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
/**
        replaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tempQuestion2 = questionEditText.getText().toString();
                String tempAnswer2 = answerEditText.getText().toString();

                rQuestion = tempQuestion2;
                rAnswer = tempAnswer2;

                originalText.setText("Q: " + rQuestion + ", " + "A: " + rAnswer);

            }
        });

 */       //Create a temp file on the go.

        //fileIO.CreateTempFile();

        final String finalTempQuestion = tempQuestion2;
        final String finalQuizTitle = quizTitle;

    }
}
