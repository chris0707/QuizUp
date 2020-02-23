package albarillo.barebarz.quizup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TitlesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titles);


        final ListView titleLV = findViewById(R.id.titleListView);

        Scanner x;

        String path = getApplicationContext().getFilesDir().getAbsolutePath();

        String filepath = path+"/quizList.txt";


        final ArrayList questionArray = new ArrayList();
        final ArrayList answerArray = new ArrayList();
        final ArrayList titleListArray = new ArrayList();
        final ArrayList titleArray = new ArrayList();
        final ArrayList atitleListArray = new ArrayList();
        ArrayList answerList = new ArrayList();

        String question = "";
        String answer = "";
        String title1 = "";

        Boolean isGrouped = false;


        //Add a condition to check if title already exists. If it does. Do not add to the list.
        try {
            x  = new Scanner(new File(filepath));
            x.useDelimiter("[|\n]");

            while(x.hasNext()){
                question = x.next();
                answer = x.next();
                title1 = x.next();


                titleListArray.add(title1);

            }

            for(int i = 0; i<titleListArray.size(); i++){

                if(!atitleListArray.contains(titleListArray.get(i))){

                    atitleListArray.add(titleListArray.get(i));

                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        titleLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String tempString = "";

                tempString = (titleLV.getItemAtPosition(position)).toString();

                Intent intent = new Intent(TitlesActivity.this, QuizActivity.class);
                intent.putExtra("tt",tempString);
                startActivity(intent);

            }
        });

        titleLV.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_list_black_text, atitleListArray));


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }

}
