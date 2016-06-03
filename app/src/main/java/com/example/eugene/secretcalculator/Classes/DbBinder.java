package com.example.eugene.secretcalculator.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.eugene.secretcalculator.MultipleImagePicker.Utility;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Eugene on 31-May-16.
 */
public class DbBinder {

    SQLiteDatabase db;
    Context context;
    int thumbnailWidth = Resources.getSystem().getDisplayMetrics().widthPixels/4;
    int thumbnailHeight = Resources.getSystem().getDisplayMetrics().widthPixels/4;

    public DbBinder(Context context){
        this.context = context;
    }

    public void saveImage(String path){
        createDataBase();
        try{
            FileInputStream fis = new FileInputStream(path);
            byte [] image = new byte[fis.available()];
            fis.read(image);

            ContentValues values = new ContentValues();
            values.put("a", image);
            db.insert("tb", null, values);

            fis.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getImage() {
        Cursor c = db.rawQuery("select * from tb", null);

        TumblrDataToStorage tdts = new TumblrDataToStorage();
        //tdts.execute();
        ArrayList<String> result = null;
        try {
            result = tdts.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void createDataBase(){
        db = context.openOrCreateDatabase("imgs.db", context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists tb (a blob)");
    }

    private class TumblrDataToStorage extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                path.mkdirs();
            return path;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {

        }
    }


}
