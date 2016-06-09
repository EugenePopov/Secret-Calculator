package com.example.eugene.secretcalculator.MultipleImagePicker;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.eugene.secretcalculator.Activities.FullsizeImageActivity;
import com.example.eugene.secretcalculator.Activities.ListItemNoteActivity;
import com.example.eugene.secretcalculator.Activities.NotesActivity;
import com.example.eugene.secretcalculator.Classes.Note;
import com.example.eugene.secretcalculator.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivityAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<String> images;
    String imagePath;
    // Gets the context so it can be used later
    public AlbumActivityAdapter(Context c, ArrayList<String> images) {
        mContext = c;
        this.images = images;

    }

    // Total number of things contained within the adapter
    public int getCount() {
        return images.size();
    }

    // Require for structure, not really used in my code.
    public Object getItem(int position) {
        return null;
    }

    // Require for structure, not really used in my code. Can
    // be used to get the id of an item in the adapter for
    // manual control.
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageURI(Uri.parse(images.get(position)));

        return imageView;
    }






}