package com.example.eugene.secretcalculator.Activities;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eugene.secretcalculator.Classes.Note;
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
    public void onBackPressed(){
        EditText noteContent = (EditText) findViewById(R.id.editText);
        note.setContent(noteContent.getText().toString());
        note.setTitle(noteContent.getText().toString().substring(0, Math.min(noteContent.getText().toString().length(), 4))+"...");
        note.createOnLocalStorage(fileName);
        Intent intent = new Intent(ListItemNoteActivity.this, NotesActivity.class);
        intent.putExtra(Note.class.getCanonicalName(), note);
        NotesActivity.isNoteContentChanged = true;
        ListItemNoteActivity.this.startActivity(intent);
    }

    private void hideNavigationBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

}
