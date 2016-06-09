package com.example.eugene.secretcalculator.Classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.eugene.secretcalculator.MultipleImagePicker.CustomGalleryActivity;
import com.example.eugene.secretcalculator.MultipleImagePicker.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Eugene on 31-May-16.
 */
public class DbBinder {

    SQLiteDatabase db;
    Context context;
    public static ProgressDialog mProgressDialog;


    public DbBinder(Context context){
        this.context = context;
    }

    public void saveImageToDatabase(String path){
        db = context.openOrCreateDatabase("imgs.db", context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists temp (a blob, b text)");



        try{
            FileInputStream fis = new FileInputStream(path);
            byte [] image = new byte[fis.available()];
            fis.read(image);

            ContentValues values = new ContentValues();
            values.put("a", image);
            values.put("b",getFilename(path));
            db.insert("temp", null, values);

            fis.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void getImagesFromDatabase() {
       // Cursor c = db.rawQuery("select * from tb", null);

        CreateTemporaryImageFiles tdts = new CreateTemporaryImageFiles(context);
        ArrayList<String> result = null;

             tdts.execute();


    }


 /*   private class CreateTemporaryImageFiles extends AsyncTask<Void, Void, ArrayList<String> > {

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
        protected ArrayList<String>  doInBackground(Void... params) {
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
    }*/

    private String getFilename(String path){
        return new File(path).getName();
    }


}
