package com.example.eugene.secretcalculator.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Eugene on 31-May-16.
 */
public class DbBinder {

    SQLiteDatabase db;
            Context context;

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

    public ArrayList<Bitmap> getImage(){
        ArrayList<Bitmap> imageList = new ArrayList<>();
        Cursor c = db.rawQuery("select * from tb", null);
        //Bitmap bmp = null;
        while(c.moveToNext()){

            byte [] image = c.getBlob(0);
            /*bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            imageList.add(bmp);*/
            ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            imageList.add(bmp);

        }
        c.close();
        return imageList;
    }

    private void createDataBase(){
        db = context.openOrCreateDatabase("imgs.db", context.MODE_PRIVATE, null);
        db.execSQL("create table if not exists tb (a blob)");
    }


}
