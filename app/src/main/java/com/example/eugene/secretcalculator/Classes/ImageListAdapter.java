package com.example.eugene.secretcalculator.Classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.eugene.secretcalculator.Activities.ListItemNoteActivity;
import com.example.eugene.secretcalculator.Activities.NotesActivity;
import com.example.eugene.secretcalculator.R;

import java.util.ArrayList;


public class ImageListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<Note> notes;
    ArrayList<String> fileList;
    Note note;
    int positionToDelete;
    boolean isItemSelected = false;

    public ImageListAdapter(Context context, ArrayList<Note> notes, ArrayList<String> fileList) {
        this.context = context;
        this.notes = notes;
        this.fileList = fileList;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView title;
        TextView date;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_item_row, parent, false);
        note = notes.get(position);

        title = (TextView) itemView.findViewById(R.id.textView4);
        date = (TextView) itemView.findViewById(R.id.textView5);

        title.setText(note.getTitle());
        date.setText(note.getDate());
        final ImageButton myButton = new ImageButton(context);
        myButton.setBackgroundResource(R.drawable.delete_idle);
        myButton.setOnClickListener(oclBtnOk);

        final LinearLayout buttonLayout = (LinearLayout) itemView.findViewById(R.id.buttonLayout);
        final LinearLayout rowLayout = (LinearLayout) itemView.findViewById(R.id.rowLayout);



        ViewGroup.LayoutParams params = itemView.getLayoutParams();
        if (params != null) {
            params.height = 100;
        }

        final int pos = position;
        itemView.setOnClickListener(new View.OnClickListener() {

           @Override
            public void onClick(View arg0) {
               note = notes.get(pos);
               notes.remove(note);
               Intent intent = new Intent(context, ListItemNoteActivity.class);
               intent.putExtra(Note.class.getCanonicalName(), note);
               ((Activity)context).startActivityForResult(intent, 1);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View arg0) {
                if(!isItemSelected) {
                    isItemSelected = true;
                    buttonLayout.addView(myButton);
                    positionToDelete = pos;
                    rowLayout.setBackgroundResource(R.drawable.line_delete);

                }
                return true;
            }
        });

            return itemView;
    }

    View.OnClickListener oclBtnOk = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                note = notes.get(positionToDelete);
                notes.remove(note);
                fileList.remove(note.getSourceFileName());
                note.deleteFromLocalStorage();
                notifyDataSetChanged();
                NotesActivity.updateNoteListFile(fileList);
                isItemSelected = false;

        }
    };


}
