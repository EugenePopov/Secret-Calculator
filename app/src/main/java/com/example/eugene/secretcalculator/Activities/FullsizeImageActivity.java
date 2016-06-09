package com.example.eugene.secretcalculator.Activities;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.eugene.secretcalculator.MultipleImagePicker.Presenter;
import com.example.eugene.secretcalculator.R;


public class FullsizeImageActivity extends AppCompatActivity implements Presenter.IView {

    Presenter presenter;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullsize_image);
        hideNavigationBar();

        presenter = new Presenter(this);
        imageView = (ImageView) findViewById(R.id.imageView2);
        presenter.recieveModelData(this);
    }

    private void hideNavigationBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void updateImageView(Bitmap bmp){
        imageView.setImageBitmap(bmp);
    }

}
