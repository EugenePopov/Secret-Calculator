package com.example.eugene.secretcalculator.MultipleImagePicker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Eugene on 09-Jun-16.
 */
public class Presenter {

    Image image;
    IView view;

    public Presenter(IView view){
        this.view = view;
        image = new Image();
    }

    public void recieveModelData(Context context){
        image.setSdcardPath(((Activity)context).getIntent().getStringExtra("string"));
        view.updateImageView(image.getBitmap());
    }

    public interface IView{
        void updateImageView(Bitmap bmp);
    }
}
