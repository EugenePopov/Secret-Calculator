package com.example.eugene.secretcalculator.Activities;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.eugene.secretcalculator.Classes.Note;
import com.example.eugene.secretcalculator.Classes.SharedData;
import com.example.eugene.secretcalculator.R;

public class ListItemNoteActivity extends AppCompatActivity {

        String fileName;
        Note note;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list_note);
            hideNavigationBar();
            EditText noteContent = (EditText) findViewById(R.id.editText);
            TextView noteDate = (TextView) findViewById(R.id.textView6);
            note = (Note) getIntent().getParcelableExtra(
                    Note.class.getCanonicalName());
            fileName = note.getSourceFileName();
            noteDate.setText(note.getDate());
            noteContent.setText(note.getContent());
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

    public void onButtonBackClick(View view){
        Intent i = new Intent(this,NotesActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    public void onButtonDoneClick(View view){
        EditText noteContent = (EditText) findViewById(R.id.editText);
        note.setContent(noteContent.getText().toString());
        note.setTitle(noteContent.getText().toString().substring(0, Math.min(noteContent.getText().toString().length(), 4))+"...");
        note.createOnLocalStorage(fileName);

        Intent returnIntent = new Intent(ListItemNoteActivity.this, NotesActivity.class);
        returnIntent.putExtra(Note.class.getCanonicalName(), note);
        setResult(NotesActivity.RESULT_OK,returnIntent);
        finish();
    }

    private void hideNavigationBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

}
