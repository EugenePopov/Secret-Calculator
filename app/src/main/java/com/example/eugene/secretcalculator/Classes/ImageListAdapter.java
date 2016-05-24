package com.example.eugene.secretcalculator.Classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eugene.secretcalculator.Activities.ListItemNoteActivity;
import com.example.eugene.secretcalculator.R;

import java.util.ArrayList;


public class ImageListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<Note> notes;
    Note note;

    public ImageListAdapter(Context context, ArrayList<Note> notes) {
        this.context = context;
        this.notes = notes;
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

        final int pos = position;
        itemView.setOnClickListener(new View.OnClickListener() {

           @Override
            public void onClick(View arg0) {
               note = notes.get(pos);
               notes.remove(note);
               Intent intent = new Intent(context, ListItemNoteActivity.class);
               intent.putExtra(Note.class.getCanonicalName(), note);
               context.startActivity(intent);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View arg0) {
                Toast.makeText(context, "long clicked pos: " , Toast.LENGTH_LONG).show();
                return true;
            }
        });


        return itemView;
    }


}
