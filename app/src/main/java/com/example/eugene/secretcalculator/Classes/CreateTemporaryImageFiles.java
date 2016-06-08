package com.example.eugene.secretcalculator.Classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.eugene.secretcalculator.MultipleImagePicker.CustomGalleryActivity;
import com.example.eugene.secretcalculator.MultipleImagePicker.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Eugene on 08-Jun-16.
 */
public class CreateTemporaryImageFiles extends AsyncTask<Void, Void, ArrayList<String> > {

    public static ProgressDialog mProgressDialog;
    Context context;
    SQLiteDatabase db;

    public CreateTemporaryImageFiles(Context context, SQLiteDatabase db){
        this.context = context;
        this.db = db;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle("Importing from gallery");
        mProgressDialog.setMessage("Please, don't close the app...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    @Override
    protected ArrayList<String> doInBackground(Void... params) {
        File directory = getDirectoryPath();
        ArrayList<String> imageList = new ArrayList<>();

        int imageNumber = 0;
        Cursor c = db.rawQuery("select * from tb", null);


        if (c.moveToFirst()) {
            do {
                byte[] blob = c.getBlob(c.getColumnIndex("a"));
                File imagePath = new File(directory, String.format("thumb%s.jpg", imageNumber++));
                imageList.add(imagePath.getPath());
                // Bitmap bmp = Bitmap.createScaledBitmap(Utility.getPhoto(blob), thumbnailWidth, thumbnailHeight, false);
                saveToFile(imagePath.getPath(), Utility.getPhoto(blob));
            } while (c.moveToNext());
        }
        c.close();

        return imageList;
    }

    private void saveToFile(String filename, Bitmap bmp) {
        try {
            FileOutputStream out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public File getDirectoryPath() {
        File path;

        path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SecCalc/Media/thumbnails/");
        if(!path.exists()) {
            path.mkdirs();
        }
        return path;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        //imageList = result;
        mProgressDialog.dismiss();
        String[] importedImages = new String[result.size()];
        for(int i =0; i<importedImages.length;i++){
            importedImages[i] = result.get(i);
        }

        CustomGalleryActivity.data = new Intent().putExtra("selectedImages", importedImages);
        ((Activity)context).setResult(((Activity)context).RESULT_OK, CustomGalleryActivity.data);
        ((Activity)context).finish();
    }
}
