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


public class TemporaryImageFilesCreator extends AsyncTask<Void, Void, ArrayList<String> > {

    public static ProgressDialog mProgressDialog;
    Context context;
    SQLiteDatabase db;

    public TemporaryImageFilesCreator(Context context){
        this.context = context;
        db = context.openOrCreateDatabase("imgs.db", context.MODE_PRIVATE, null);
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
        ArrayList<String> imageList = new ArrayList<String>();
        Cursor c = db.rawQuery("select * from temp", null);


        if (c.moveToFirst()) {
            do {
                byte[] blob = c.getBlob(c.getColumnIndex("a"));
                String filename = c.getString(c.getColumnIndex("b"));
                File imagePath = new File(directory,filename);
                imageList.add(imagePath.getPath());
                saveToFile(imagePath.getPath(), Utility.getPhoto(blob));
            } while (c.moveToNext());
        }
        c.close();
        db.execSQL("insert into tb select * from temp");
        db.execSQL("DROP TABLE IF EXISTS temp");
        db.close();
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
