package com.example.eugene.secretcalculator.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.example.eugene.secretcalculator.Classes.ImageListAdapter;
import com.example.eugene.secretcalculator.Classes.Note;
import com.example.eugene.secretcalculator.Classes.SharedData;
import com.example.eugene.secretcalculator.R;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class NotesActivity extends AppCompatActivity{

    ArrayList<Note> notes = new ArrayList<>();
    ArrayList<String> fileList = new ArrayList<>();
    ListView listView;
    ImageListAdapter adapter;
    Integer numberOfNotes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        listView = (ListView) findViewById(R.id.listView);
        listView.setLongClickable(true);
        createConfigFile();
        createNoteListFile();
        fillFileListArray();
        numberOfNotes = getNumberOfNoteFiles();
        initializeListView();
        hideNavigationBar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Note note = data.getParcelableExtra(Note.class.getCanonicalName());
                notes.add(note);

                //Reinflate listview
                listView.setAdapter(null);
                Collections.reverse(notes);
                adapter = new ImageListAdapter(NotesActivity.this, notes, fileList);
                listView.setAdapter(adapter);

            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (SharedData.runningActivities == 0) {
            Intent i = new Intent(this,CalculatorActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        SharedData.runningActivities++;
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedData.runningActivities--;
        if (SharedData.runningActivities == 0) {
            // app goes to background
        }
    }

    private void hideNavigationBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void onButtonBackMenuClick(View view){
        Intent intent = new Intent(this, VaultActivity.class);
        startActivity(intent);
    }

    public void onButtonAddClick(View view){
        numberOfNotes++;
        Note test = new Note();
        test.setSourceFileName("NOTE_"+ numberOfNotes.toString());
        test.setDate(Calendar.getInstance().getTime().toString());
        notes.add(test);
        fileList.add(test.getSourceFileName());
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ImageListAdapter(NotesActivity.this, notes, fileList);
        listView.setAdapter(adapter);
        test.createOnLocalStorage("NOTE_"+ numberOfNotes.toString());
        updateConfigFile(numberOfNotes.toString());
        updateNoteListFile(fileList);
    }

    @Override
    public void onBackPressed(){
        /**Disable back button**/
    }


    public void updateConfigFile(String data){
        try{
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SecCalc/Notes/");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File gpxFile = new File(directory, "conf");

            FileWriter writer = new FileWriter(gpxFile);
            writer.append(data);
            writer.flush();
            writer.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void updateNoteListFile(ArrayList<String> filelist){
        try{
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SecCalc/Notes/");

            File gpxFile = new File(directory, "notelist");

            FileWriter writer = new FileWriter(gpxFile);
            for (String note : filelist) {
                writer.append(note + "\n");
                writer.flush();
            }
            writer.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void createConfigFile(){
        try{
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SecCalc/Notes/");
            if (!directory.exists()) {
                directory.mkdirs();
            File gpxFile = new File(directory, "conf");

            FileWriter writer = new FileWriter(gpxFile);
            writer.append("0");
            writer.flush();
            writer.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void createNoteListFile(){
        try{
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SecCalc/Notes/");
                File gpxFile = new File(directory, "notelist");
                gpxFile.createNewFile();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Note readFromFile(String fileName) {
        Note note = new Note();

            try {
                InputStream inputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/SecCalc/Notes/" + fileName));
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String receiveTitleString = "";
                    String receiveDateString = "";
                    String receiveContentString = "";
                    String receiveSourceFileName = "";

                    StringBuilder stringTitleBuilder = new StringBuilder();
                    StringBuilder stringDateBuilder = new StringBuilder();
                    StringBuilder stringContentBuilder = new StringBuilder();
                    StringBuilder stringFileNameBuilder = new StringBuilder();

                    //Read first line, which is title of the note
                    receiveTitleString = bufferedReader.readLine();
                    stringTitleBuilder.append(receiveTitleString);
                    note.setTitle(stringTitleBuilder.toString());

                    //Read second line, which is creation date of the note
                    receiveDateString = bufferedReader.readLine();
                    stringDateBuilder.append(receiveDateString);
                    note.setDate(stringDateBuilder.toString());

                    //Read third line, which is source filename of the note
                    receiveSourceFileName = bufferedReader.readLine();
                    stringFileNameBuilder.append(receiveSourceFileName);
                    note.setSourceFileName(stringFileNameBuilder.toString());

                    //Read the rest of the file, which is the content of the note
                    while ((receiveContentString = bufferedReader.readLine()) != null) {
                        stringContentBuilder.append(receiveContentString);
                    }
                    inputStream.close();
                    note.setContent(stringContentBuilder.toString());
                }
            } catch (FileNotFoundException e) {
                Log.e("login activity", "File not found: " + e.toString());
            } catch (IOException e) {
                Log.e("login activity", "Can not read file: " + e.toString());
            }
        return note;
    }

    public int getNumberOfNoteFiles(){
        int numberOfNotes= 0;

        try {
            InputStream inputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/SecCalc/Notes/conf"));
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                numberOfNotes = Integer.parseInt(stringBuilder.toString());
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return numberOfNotes;
    }

    private void fillFileListArray(){

        try {
            InputStream inputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/SecCalc/Notes/notelist"));
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String receiveContentString = "";

                StringBuilder stringContentBuilder = new StringBuilder();

                while ((receiveContentString = bufferedReader.readLine()) != null) {
                    stringContentBuilder.append(receiveContentString);
                    fileList.add(stringContentBuilder.toString());
                    stringContentBuilder.delete(0, stringContentBuilder.toString().length());
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    public void loadDataFromStorage(){

        for(String note : fileList){
            Note n;
            n=readFromFile(note+".txt");
            notes.add(n);
        }
    }

    private void initializeListView(){

        loadDataFromStorage();
        adapter = new ImageListAdapter(NotesActivity.this, notes, fileList);
        listView.setAdapter(adapter);
    }

}
