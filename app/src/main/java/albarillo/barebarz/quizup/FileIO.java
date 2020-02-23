package albarillo.barebarz.quizup;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileIO {

    private ArrayList<String> titleListArray = new ArrayList<>();
    private ArrayList<String> titleListArray1 = new ArrayList<>();
    private ArrayList<String> questionListArray = new ArrayList<>();
    private ArrayList<String> answerListArray = new ArrayList<>();
    private ArrayList<String> QA = new ArrayList<>();

    private Context context;

    public FileIO(Context context) {
        this.context = context;
    }



    public void addQA(String finalQuizTitle, String question1, String answer1){

        final String filename = "quizList.txt";

        File file = new File(context.getFilesDir(), filename);

        String title1 = finalQuizTitle;
        //String question1 = "";
        //String answer1 = "";


        FileOutputStream fileOutputStream;

    //    question1 = questionEditText.getText().toString();
    //   answer1 = answerEditText.getText().toString();

        if((question1.contains("|"))||(question1.contains(">"))||answer1.contains("|")||answer1.contains(">")){
            Toast.makeText(context, "QA not added. > | characters are not allowed.", Toast.LENGTH_SHORT).show();
        }
        else if((question1.isEmpty())||answer1.isEmpty()){
            Toast.makeText(context, "QA not added. Field cannot be empty.", Toast.LENGTH_SHORT).show();
        }else {
            try {

                String qaList = "";

                qaList = question1 + "|" + answer1 + "|" + title1 + "\n";
                fileOutputStream = context.openFileOutput(filename, context.MODE_APPEND);
                fileOutputStream.write(qaList.getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();

                Toast.makeText(context, "QA Added", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public Boolean fileCheck(String s1){
        Scanner x;
        Boolean exist = false;
        int counter = 0;



        String path = context.getFilesDir().getAbsolutePath();

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

                if(s1.equals(titleListArray.get(i))){
                    counter++;
                }
            }
            if(counter > 0){
                exist = true;
            }else{exist = false;}

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchElementException ne){
            ne.printStackTrace();
        } catch (NullPointerException ne){
            ne.printStackTrace();
        }

        return exist;
    }

    /**
     *
     * @param s1 - Takes a String parameter. Check if it exist in the filetext
     * @return - returns if exists or !exists
     */
    public Boolean fileQuestionExist(String s1){
        Scanner x;
        Boolean exist = false;
        int counter = 0;



        String path = context.getFilesDir().getAbsolutePath();

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

                questionListArray.add(question);
                //answerListArray.add(answer);
            }

            for(int i = 0; i<questionListArray.size(); i++){

                if(s1.equals(questionListArray.get(i))){
                    counter++;
                }
            }
            if(counter > 0){
                exist = true;
                Toast.makeText(context, "Question already exists!", Toast.LENGTH_SHORT).show();
            }else{exist = false;}

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchElementException ne){
            ne.printStackTrace();
        } catch (NullPointerException ne){
            ne.printStackTrace();
        }

        return exist;
    }

    public Boolean fileQuestionExist(String s1, String s2, String s3){
        Scanner x;
        Boolean exist = false;
        int counter = 0;



        String path = context.getFilesDir().getAbsolutePath();

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

                questionListArray.add(question);
                answerListArray.add(answer);
                titleListArray1.add(title1);
            }

            for(int i = 0; i<questionListArray.size(); i++){

                if((s1.equals(questionListArray.get(i)) && (s2.equals(answerListArray.get(i))) && (s3.equals(titleListArray1.get(i))))){
                    counter++;
                }
            }
            if(counter > 0){
                exist = true;
                Toast.makeText(context, "Question and Answer already exist!", Toast.LENGTH_SHORT).show();
            }else{exist = false;}

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchElementException ne){
            ne.printStackTrace();
        } catch (NullPointerException ne){
            ne.printStackTrace();
        } catch (IndexOutOfBoundsException ne){
            ne.printStackTrace();
        }

        return exist;
    }

    public void DeleteItem(String finalTempQuestion, String finalQq, Scanner ss){

        Scanner x;

        String path = context.getFilesDir().getAbsolutePath();


        String filePath = path+"/quizList.txt";
        String tempFilePath = path + "/temp.txt";

        File oldFile = new File(filePath);
        File newFile = new File(tempFilePath);

        String ID = ""; String mQuestion = ""; String mAnswer = ""; String mTitle = "";

        try {
            FileWriter fw = new FileWriter(tempFilePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            x = new Scanner(new File(filePath));
            x.useDelimiter("[|\n]");

            while(x.hasNext()){

                mQuestion = x.next().trim();
                mAnswer = x.next().trim();
                mTitle = x.next().trim();

                //
                if(!mQuestion.equals(finalTempQuestion) && !mTitle.equals(finalQq)){
                    pw.println(mQuestion + "|" + mAnswer + "|" + mTitle);

                }
                if(!mQuestion.equals(finalTempQuestion) && mTitle.equals(finalQq)){
                    pw.println(mQuestion + "|" + mAnswer + "|" + mTitle);
                }
            }
            ss.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File(filePath);
            newFile.renameTo(dump);

            Toast.makeText(context, "Item removed",
                    Toast.LENGTH_SHORT).show();

        }catch (IOException io){

            io.printStackTrace();

        }

    }

    public void DeleteSection(String finalQq){

        Scanner x;

        String path = context.getFilesDir().getAbsolutePath();


        String filePath = path+"/quizList.txt";
        String tempFilePath = path + "/temp.txt";

        File oldFile = new File(filePath);
        File newFile = new File(tempFilePath);

        String ID = ""; String mQuestion = ""; String mAnswer = ""; String mTitle = "";

        try {
            FileWriter fw = new FileWriter(tempFilePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            x = new Scanner(new File(filePath));
            x.useDelimiter("[|\n]");

            while(x.hasNext()){

                mQuestion = x.next().trim();
                mAnswer = x.next().trim();
                mTitle = x.next().trim();

                //
                if(!mTitle.equals(finalQq)){
                    pw.println(mQuestion + "|" + mAnswer + "|" + mTitle);
                }
            }
            x.close();
            pw.flush();
            pw.close();
            oldFile.delete();
            File dump = new File(filePath);
            newFile.renameTo(dump);

            Toast.makeText(context, "Item removed",
                    Toast.LENGTH_SHORT).show();

        }catch (IOException io){

            io.printStackTrace();

        }

    }

     ArrayList<String> ListQA(String qq) {

        String question = "";
        String answer = "";
        String title1 = "";

        String filepath = context.getFilesDir().getAbsolutePath() + "/quizList.txt";

        try {
            Scanner x;

            x = new Scanner(new File(filepath));
            x.useDelimiter("[|\n]");

            while (x.hasNext()) {

                question = x.next();
                answer = x.next();
                title1 = x.next();

                if (title1.equals(qq)) {
                    QA.add(question + "\t" + answer);
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return QA;
    }

    public void CreateTempFile(){

        final File tempFile = new File(context.getFilesDir(), "temp.txt");

        try{
            if(tempFile.createNewFile()){
                //Toast.makeText(context, "Temp file created.",
                        //Toast.LENGTH_SHORT).show();
            }else System.out.println("Temp file already exists");


        }catch (IOException io){
            Toast.makeText(context, io.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void FileEdit(String finalTempQuestion, String finalQuizTitle, String rQuestion, String rAnswer){

        Scanner s;

        String mPath = context.getFilesDir().getPath();

        String filePath = mPath + "/quizList.txt";
        String tempFilePath = mPath + "/temp.txt";

        File oldFile = new File(filePath);
        File newFile = new File(tempFilePath);

        String ID = ""; String mQuestion = ""; String mAnswer = ""; String mTitle = "";

        if((rQuestion.contains("|"))||(rQuestion.contains(">"))||rAnswer.contains("|")||rAnswer.contains(">")){
            Toast.makeText(context, "QA not added. > | characters are not allowed.", Toast.LENGTH_SHORT).show();
        }else {

            try {
                /**
                 * To better the code, since there are similarities in all read text file, create a
                 * separate method where we can override super.method() then add what ever needed to
                 * be added or simply copy the whole code and modify.
                 * */
                //PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(tempFile,true)));
                FileWriter fw = new FileWriter(tempFilePath, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);

                s = new Scanner(new File(filePath));
                s.useDelimiter("[|\n]");

                while (s.hasNext()) {

                    mQuestion = s.next().trim();
                    mAnswer = s.next().trim();
                    mTitle = s.next().trim();

                    if (!mQuestion.equals(finalTempQuestion) && !mTitle.equals(finalQuizTitle)) {

                        pw.println(mQuestion + "|" + mAnswer + "|" + mTitle);
                    }

                    if (!mQuestion.equals(finalTempQuestion) && mTitle.equals(finalQuizTitle)) {

                        pw.println(mQuestion + "|" + mAnswer + "|" + mTitle);

                    }

                    if (mQuestion.equals(finalTempQuestion) && mTitle.equals(finalQuizTitle)) {
                        pw.println(rQuestion + "|" + rAnswer + "|" + finalQuizTitle);
                    }
                }
                s.close();
                pw.flush();
                pw.close();
                oldFile.delete();
                File dump = new File(filePath);
                newFile.renameTo(dump);

                Toast.makeText(context, "Question modified!", Toast.LENGTH_SHORT).show();

            } catch (IOException io) {
                //File exception.
                io.printStackTrace();
            }
        }

    }

}
