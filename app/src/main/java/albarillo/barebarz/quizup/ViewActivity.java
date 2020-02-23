package albarillo.barebarz.quizup;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();

        final ListView quizLV = findViewById(R.id.quizListView);
        String path = getApplicationContext().getFilesDir().getAbsolutePath();

        String filename = path+"/quizList.txt";

        String question = "";
        String answer = "";
        String title = "";

        Scanner x;

        try{

            x = new Scanner(new File(filename));
            x.useDelimiter("[|\n]");

            while (x.hasNext()){

                question = x.next();
                answer = x.next();
                title = x.next();

                //arrayList.add("Q: " + question + ", " + "A: " + answer);
                arrayList.add(title);

            }
            for(int i = 0; i<arrayList.size(); i++){
                if(!arrayList2.contains(arrayList.get(i))){

                    arrayList2.add(arrayList.get(i));

                }

            }

        }catch(FileNotFoundException e){

            e.printStackTrace();

        }catch (NoSuchElementException ex){
            ex.printStackTrace();
        }

        quizLV.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_list_black_text, arrayList2));


        quizLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /**
                 * Read from the text file using the delimeter for list view then pass id/string
                 * throught Intent.
                 *
                 * Next page, on "Page Load" use the String id to pull up the data/row/section to be
                 * edited.
                 */
                String title1 = "";

                /**
                 * Create a delimeter to only extract questions for the string and then
                 * proceed to editing the objects
                 */
                title1 = (quizLV.getItemAtPosition(position)).toString();

                Intent intent = new Intent(ViewActivity.this, ModActivity.class);
                intent.putExtra("qq",title1);
                intent.putExtra("tt","ViewActivity");

                startActivity(intent);

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }
}
