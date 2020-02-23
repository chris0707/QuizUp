package albarillo.barebarz.quizup;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CreateActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button addButton = findViewById(R.id.addB);

        final EditText titleEdit = findViewById(R.id.titleEditText);

        final ArrayList titleListArray = new ArrayList();
        final ArrayList atitleListArray = new ArrayList();

        final ListView titleLV2 = findViewById(R.id.titleListView2);

        final FileIO fileIO = new FileIO(getApplicationContext());

        Scanner x;

        String path = getApplicationContext().getFilesDir().getAbsolutePath();

        String filepath = path+"/quizList.txt";

        String question = "";
        String answer = "";
        String title1 = "";


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
        } catch (NoSuchElementException ne){
            ne.printStackTrace();
        }




        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title;
                title = titleEdit.getText().toString();


                //Intent intent = new Intent(getBaseContext(), PopAdd.class);
                Intent intent = new Intent(CreateActivity.this, PopAdd.class);

                if(title.length()<3 || title.isEmpty() || title.contains("|")) {

                    if(title.length()<3){
                        Toast.makeText(getApplicationContext(), "Length should be more than 3", Toast.LENGTH_SHORT).show();
                    }else if(title.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Field is empty!", Toast.LENGTH_SHORT).show();
                    }else if(title.contains("|")){
                        Toast.makeText(getApplicationContext(), " | character is not allowed.", Toast.LENGTH_SHORT).show();
                    }

                }else{

                    try {
                        if (!fileIO.fileCheck(title)) {

                            intent.putExtra("title1", title);
                            intent.putExtra("PrevClass", "NotNull");

                            final View add_layout = LayoutInflater.from(CreateActivity.this)
                                    .inflate(R.layout.activity_pop_add, null);

                            new MaterialStyledDialog.Builder(CreateActivity.this)
                                    .setIcon(R.drawable.addlogoicon)
                                    .setTitle(title)
                                    .setDescription("Please fill all fields")
                                    .setCustomView(add_layout)
                                    .setNegativeText("CANCEL")
                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setPositiveText("ADD")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            EditText questionEditText = add_layout.findViewById(R.id.questionET);
                                            EditText answerEditText = add_layout.findViewById(R.id.answerET);

                                            String question1 = questionEditText.getText().toString();
                                            String answer1 = answerEditText.getText().toString();

                                            //Adds a record with new title
                                            fileIO.addQA(title, question1, answer1);
                                            //Toast.makeText(CreateActivity.this,"Added", Toast.LENGTH_SHORT).show();
                                            finish();
                                            startActivity(getIntent());
                                        }
                                    }).show();

                            //startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Title already exist. Please retry", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NullPointerException ne) {
                        Toast.makeText(getApplicationContext(), "Null reference", Toast.LENGTH_SHORT).show();
                        ne.printStackTrace();
                    }
                }
            }
        });

        titleLV2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Redirect to adding to an existing Quiz(varies in title)
                String tempString = "";
                String prevPage = "CreateActivity";

                tempString = (titleLV2.getItemAtPosition(position)).toString();

                Intent intent = new Intent(CreateActivity.this, ModActivity.class);
                intent.putExtra("qq",tempString);
                intent.putExtra("tt",prevPage);
                startActivity(intent);


            }
        });

        titleLV2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String temp="";
                temp = titleLV2.getItemAtPosition(position).toString();

                final String finalTemp = temp;
                new AlertDialog.Builder(CreateActivity.this)
                            .setTitle("Delete item")
                            .setMessage("Are you sure you want to delete this item? This contains lists of quizzes!!")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    fileIO.DeleteSection(finalTemp);
                                    finish();
                                    startActivity(getIntent());
                                }
                            })

                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return true;
            }
        });

        /**
         * Created new layout from a copy of simple_list_item_1 + Color: black
         */
        titleLV2.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_list_black_text, atitleListArray));


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
