package albarillo.barebarz.quizup;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Button submitB = findViewById(R.id.submitButton);
     /**   final TextView questionV = findViewById(R.id.questionView);
        final EditText answerET = findViewById(R.id.answerEditText);

        final TextView testingView = findViewById(R.id.testView);
*/
        String line = "";
        String quizList = "";

        String question = "";
        String answer = "";
        String title1 = "";

        final ArrayList questionArray = new ArrayList();
        final ArrayList answerArray = new ArrayList();
        ArrayList answerList = new ArrayList();



        Scanner x;

        String path = getApplicationContext().getFilesDir().getAbsolutePath();

        String filepath = path+"/quizList.txt";

        String selectedTitle = "";

        //Intent intent = new Intent();
        TextView quizTitleTV = findViewById(R.id.quiztitleTV);
        final TextView scoreTV = findViewById(R.id.scoreTV);

        try{
            selectedTitle = getIntent().getExtras().getString("tt");
            quizTitleTV.setText(selectedTitle);
            FileInputStream fis = openFileInput("quizList.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(fis)));

            quizList = "";

            /***
             * Store all quizzes into the array. question[i] answer[i]
             */
/**
            while((line =  bufferedReader.readLine()) != null){
                quizList += line + "\n";
            }
 */

            x = new Scanner(new File(filepath));
            x.useDelimiter("[|\n]");

            while(x.hasNext()){
                question = x.next();
                answer = x.next();
                title1 = x.next();

                if(title1.equals(selectedTitle)) {

                    questionArray.add(question);
                    answerArray.add(answer);

                }

            }
            fis.close();

        }catch (FileNotFoundException ex){

            //File not found

        }catch (IOException io){

            //File not found
        }catch (NoSuchElementException ne){

            //No such element
        }catch (IndexOutOfBoundsException io){
            //out of bounds
        }catch (NullPointerException ne){

            //null pointer
        }

        final EditText editText[] = new EditText[questionArray.size()]; //Adjust to 10-20?
        try {

            for (int i = 0; i < questionArray.size(); i++) {

                TextView testTV = new TextView(this);

                LinearLayout linearLayout = findViewById(R.id.linear1);


               /* testTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                testTV.setGravity(View.TEXT_ALIGNMENT_CENTER);
                testTV.setText(questionArray.get(i).toString());
                linearLayout.addView(testTV); */



                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                layoutParams.setMargins(10,10,10,5);

                LayoutParams layoutParams2 = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams2.gravity = Gravity.CENTER;
                layoutParams2.width = 600;
                layoutParams2.setMargins(10,10,10,80);


                testTV.setLayoutParams(layoutParams);
                testTV.setText(questionArray.get(i).toString());
                editText[i] = new EditText(this);
                editText[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                editText[i].setLayoutParams(layoutParams2);



                linearLayout.addView(testTV);
                linearLayout.addView(editText[i]);



            }
        }catch (IndexOutOfBoundsException ex){
            //throw error
        }catch (NullPointerException ex){
            //throw error
        }
        //Add for loop
        //questionV.setText(questionArray.get(1).toString());


        //final String finalAnswer = answerArray.get(1).toString();

        //Store all answers in array list then create another for loop in OnClick button


        /**
         * Stores all answers to List using for Loop
         */
        try {
            for (int i = 0; i < answerArray.size(); i++) {

                answerList.add(answerArray.get(i).toString());

            }
        }catch (IndexOutOfBoundsException ex){
            //Throw exception
        }

        scoreTV.setText(0 + "/" + answerArray.size());

//        final String a1 = answerArray.get(0).toString();
//        final String a2 = answerArray.get(1).toString();

        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean allCorrect = false;

                //String answer1 = editText[1].getText().toString();
               /** if(answer1.equals(finalAnswer)){
                    Toast.makeText(getApplicationContext(),"Correcto!",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getApplicationContext(),"Nah Bro. Wrong!",Toast.LENGTH_SHORT).show();
                }
                */

                /**
                 * Grab Titles and store in another file called Title list.
                 * From Title list. Compare TitleList[i]
                 *
                 * 1. Create new listview for each Title.
                 * 2. Scan file for title[i].equals(TitleList[i])
                 * 3. Assign to different listview
                 */

                // while (allCorrect == false) { //Removed while loop???
                    //Add a counter?
                    int counter = 0;


                    for (int i = 0; i < answerArray.size(); i++) {

                        if ((answerArray.get(i).toString().equals(editText[i].getText().toString())) || (answerArray.get(i).toString().toLowerCase().equals(editText[i].getText().toString()))) {

                            allCorrect = true;
                            counter++;
                            editText[i].setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.correcticon), null);

                        } else {

                            allCorrect = false;
                            editText[i].setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.invalidicon), null);
                            //editText[i].setTextColor(Color.RED);
                        }

                        scoreTV.setText(counter + "/" + answerArray.size());


                    }


                    if(counter == answerArray.size()){

                        Toast.makeText(getApplicationContext(),"Nice!"  + "  ",Toast.LENGTH_SHORT).show();

                    }else{
                        double totalScore;
                        totalScore = ((double)counter/answerArray.size()) * 100;
                        totalScore = (int)totalScore;
                        Toast.makeText(getApplicationContext(),"Failed. Try Again! " + (int)totalScore + "%",Toast.LENGTH_SHORT).show();
                    }

                }

                //String testing = a1 + " " + a2;

                //testingView.setText(testing);

            //}
        });





    }
}
