package com.example.eugene.secretcalculator.Activities;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eugene.secretcalculator.Classes.ImageListAdapter;
import com.example.eugene.secretcalculator.Classes.Note;
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

public class NotesActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener{

    ArrayList<Note> notes = new ArrayList<>();
    ListView listView;
    ImageListAdapter adapter;
    Integer numberOfNotes = 0 ;
    public static boolean isNoteContentChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        listView = (ListView) findViewById(R.id.listView);
        listView.setLongClickable(true);
        createConfigFile();
        numberOfNotes = getNumberOfNoteFiles();
        initializeListView();
        hideNavigationBar();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (!isNoteContentChanged){
            Note note = (Note) getIntent().getParcelableExtra(
                    Note.class.getCanonicalName());
            notes.add(note);
            listView.setAdapter(null);
            initializeListView();
            isNoteContentChanged = false;
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
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ImageListAdapter(NotesActivity.this, notes);
        listView.setAdapter(adapter);
        test.createOnLocalStorage("NOTE_"+ numberOfNotes.toString());
        updateConfigFile(numberOfNotes.toString());
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

    public void loadDataFromStorage(){
        int numberOfFiles = getNumberOfNoteFiles();

        for (int i = 0; i< numberOfFiles ; i++)
        {
            Note n;
            int index = i+1;
            String name = String.format("NOTE_%s.txt",index);
            n=readFromFile(name);
            notes.add(n);
        }
    }

    private void initializeListView(){
        loadDataFromStorage();
        //listView = (ListView) findViewById(R.id.listView);
        adapter = new ImageListAdapter(NotesActivity.this, notes);
        listView.setAdapter(adapter);
    }

    //ListView listView = (ListView) findViewById(R.id.listView);

    @Override
    public boolean onItemLongClick(AdapterView<?> l, View v,
                                   final int position, long id) {

        Toast.makeText(this, "long clicked pos: " + position, Toast.LENGTH_LONG).show();

        return true;
    }


}
