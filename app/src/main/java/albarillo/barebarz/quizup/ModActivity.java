package albarillo.barebarz.quizup;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import java.util.ArrayList;
import java.util.Scanner;

public class ModActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod);

        TextView titleTV = findViewById(R.id.titleTextView);

        final ArrayList questionArray = new ArrayList();
        final ArrayList answerArray = new ArrayList();
        ArrayList<String> QA1 = new ArrayList<String>();
        ArrayList<String> QA = new ArrayList<String>();
        ArrayList answerList = new ArrayList();

        final ListView modLV = findViewById(R.id.modListView);

        final FileIO fileIO = new FileIO(getApplicationContext());


        Scanner x;

        String filepath = getFilesDir().getAbsolutePath() + "/quizList.txt";

        String selectedTitle = "";



        String qq = "";
        String tt = "";
        qq = getIntent().getExtras().getString("qq");
        tt = getIntent().getExtras().getString("tt");

        final Button addB = findViewById(R.id.AddButton);

        titleTV.setText(qq);
        try {
            if (tt.equals("CreateActivity")) {
                addB.setVisibility(View.VISIBLE);
            }else {
                modLV.setLongClickable(false);
                addB.setVisibility(View.INVISIBLE);
            }

            QA1 = fileIO.ListQA(qq); //Lists the QA

            QA = QA1;

        }catch (NullPointerException nu){
            //if tt is null
        } catch (IndexOutOfBoundsException ie){
        }
       //Creates a temp file
        fileIO.CreateTempFile();

        /**
         * Created new layout from a copy of simple_list_item_1 + Color: black
         */
        final ArrayList<String> finalQA = QA;
        final ArrayList<String> finalQA1 = QA1;
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_2_black_text, android.R.id.text1, finalQA){
          @Override
          public View getView(int position, View convertView, ViewGroup parent){
              View view = super.getView(position, convertView, parent);
              TextView text1 = (TextView) view.findViewById(android.R.id.text1);
              TextView text2 = (TextView) view.findViewById(android.R.id.text2);

              String secondText = finalQA.get(position);
              String lvQuestion = "";
              String lvAnswer = "";
              Scanner s = new Scanner(secondText).useDelimiter("[\t]");
              lvQuestion = s.next();
              lvAnswer = s.next();

              //text1.setText(finalQA.get(position));
              //text2.setText(finalQA1.get(position));

              text1.setText(lvQuestion);
              text2.setText(lvAnswer);

              return view;
          }

        };


        //modLV.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2_black_text, android.R.id.text1, QA)); //simple_list_item_1
        modLV.setAdapter(adapter);

        final String finalQq = qq;
        final String previousClass = "ModActivity";
        final String finalQq1 = qq;
        final String finalTt = tt;

        modLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {

                String temp = "";
                String question1 = "";
                String answer1 = "";
                String title1 = "";

                String tempQuestion1 = "";
                String tempQuestion2 = "";
                String tempQuestion3 = "";
                String tempAnswer1 = "";
                String tempAnswer2 = "";
                String tempAnswer3 = "";

                temp = modLV.getItemAtPosition(position).toString();

                Scanner s = new Scanner(temp).useDelimiter("[\t]");
                tempQuestion2 = s.next();
                tempAnswer2 = s.next();

                //Scanner s1 = new Scanner(question1).useDelimiter("[>]");
                //tempQuestion1 = s1.next();
                //tempQuestion2 = s1.next().trim();
                //tempQuestion3 = s1.next();
                final String finalTempQuestion = tempQuestion2;

                //Scanner s2 = new Scanner(answer1).useDelimiter("[>]");
                //tempAnswer1 = s2.next();
                //tempAnswer2 = s2.next().trim();

                final View edit_layout =  LayoutInflater.from(ModActivity.this)
                        .inflate(R.layout.activity_pop_edit, null);
                    TextView textView = edit_layout.findViewById(R.id.titleTV);
                    final EditText questionEditText = edit_layout.findViewById(R.id.questionET);
                    final EditText answerEditText = edit_layout.findViewById(R.id.answerET);
                    textView.setText("hello");

                    questionEditText.setText(tempQuestion2);
                    answerEditText.setText(tempAnswer2);


                new MaterialStyledDialog.Builder(ModActivity.this)
                        .setIcon(R.drawable.editlogoicon)
                        .setTitle(finalQq)
                        .setDescription("Please fill all fields")
                        .setCustomView(edit_layout)
                        .setNegativeText("CANCEL")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("APPLY CHANGES")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                /**
                                 * Remove same question validation
                                 *
                                 */
                                String tempQuestion2 = questionEditText.getText().toString();
                                String tempAnswer2 = answerEditText.getText().toString();

                                if(fileIO.fileQuestionExist(tempQuestion2, tempAnswer2, finalQq)){
                                    onItemClick(parent,view,position,id);
                                }else {
                                    fileIO.FileEdit(finalTempQuestion, finalQq, tempQuestion2, tempAnswer2);

                                    finish();
                                    startActivity(getIntent());
                                }
                            }
                        }).show();

            }
        });



        modLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String temp = "";
                String question2 = "";
                String answer2 = "";
                String title2 = "";

                temp = modLV.getItemAtPosition(position).toString();
                final Scanner ss = new Scanner(temp).useDelimiter("[\t]");
                question2 = ss.next();
                answer2 = ss.next();

                String tempQuestion1 = "";
                String tempQuestion2 = "";
                String tempQuestion3 = "";
                String tempAnswer1 = "";
                String tempAnswer2 = "";
                String tempAnswer3 = "";

                //Scanner s1 = new Scanner(question2).useDelimiter("[>]");
                //tempQuestion1 = s1.next();
                //tempQuestion2 = s1.next().trim();

                //Scanner s2 = new Scanner(answer2).useDelimiter("[>]");
                //tempAnswer1 = s2.next();
                //tempAnswer2 = s2.next().trim();


                final String finalTempQuestion = question2;
                final String finalTempAnswer = tempAnswer2;
                new AlertDialog.Builder(ModActivity.this)
                        .setTitle("Delete item")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                /**
                                 * To better the code, since there are similarities in all read text file, create a
                                 * separate method where we can override super.method() then add what ever needed to
                                 * be added or simply copy the whole code and modify.
                                 * */

                                fileIO.DeleteItem(finalTempQuestion, finalQq, ss); //Put back. Testing

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


        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View add_layout = LayoutInflater.from(ModActivity.this)
                        .inflate(R.layout.activity_pop_add, null);

                new MaterialStyledDialog.Builder(ModActivity.this)
                        .setIcon(R.drawable.addlogoicon)
                        .setTitle(finalQq)
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

                                if(fileIO.fileQuestionExist(question1)){
                                    addB.callOnClick();
                                }else {
                                    fileIO.addQA(finalQq, question1, answer1);
                                    //Toast.makeText(ModActivity.this,"Added", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(getIntent());
                                }
                            }
                        }).show();
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
