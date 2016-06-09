package com.example.eugene.secretcalculator.MultipleImagePicker;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Image implements ImageModel {
    private String sdcardPath;
    public boolean isSeleted = false;

    public String getSdcardPath(){
        return sdcardPath;
    }

    public void setSdcardPath(String path){
        this.sdcardPath = path;
    }


    public Bitmap getBitmap(){
        BitmapFactory.Options options=new BitmapFactory.Options();// Create object of bitmapfactory's option method for further option use
        options.inPurgeable = true; // inPurgeable is used to free up memory while required
        return BitmapFactory.decodeFile(sdcardPath, options);

    }
}
