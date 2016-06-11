package com.example.eugene.secretcalculator.Classes;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileInputStream;
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

        TemporaryImageFilesCreator tdts = new TemporaryImageFilesCreator(context);
        ArrayList<String> result = null;

             tdts.execute();


    }



    private String getFilename(String path){
        return new File(path).getName();
    }


}
