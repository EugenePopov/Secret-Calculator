package com.example.eugene.secretcalculator.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.eugene.secretcalculator.R;

public class CalculatorActivity extends AppCompatActivity {

    private static String displayedInfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        hideNavigationBar();
    }

    private void hideNavigationBar() {
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }

    public void onButton1Click(View view){
        if(displayedInfo.length()<8) displayedInfo += "1";
        showOnDisplay(displayedInfo);
    }

    public void onButton2Click(View view){
        if(displayedInfo.length()<8) displayedInfo += "2";
        showOnDisplay(displayedInfo);
    }

    public void onButton3Click(View view){
        if(displayedInfo.length()<8) displayedInfo += "3";
        showOnDisplay(displayedInfo);
    }

    public void onButton4Click(View view){
        if(displayedInfo.length()<8) displayedInfo += "4";
        showOnDisplay(displayedInfo);
    }

    public void onButton5Click(View view){
        if(displayedInfo.length()<8) displayedInfo += "5";
        showOnDisplay(displayedInfo);
    }

    public void onButton6Click(View view){
        if(displayedInfo.length()<8) displayedInfo += "6";
        showOnDisplay(displayedInfo);
    }

    public void onButton7Click(View view){
        if(displayedInfo.length()<8) displayedInfo += "7";
        showOnDisplay(displayedInfo);
    }

    public void onButton8Click(View view){
        if(displayedInfo.length()<8) displayedInfo += "8";
        showOnDisplay(displayedInfo);
    }

    public void onButton9Click(View view){
        if(displayedInfo.length()<8) displayedInfo += "9";
        showOnDisplay(displayedInfo);
    }



    public void onButton0Click(View view){
        if(displayedInfo.length()<8) displayedInfo += "0";
        showOnDisplay(displayedInfo);
    }

    public void onButtonClearClick(View view){
        if (displayedInfo != null && displayedInfo.length() > 0 ) {
            displayedInfo = displayedInfo.substring(0, displayedInfo.length()-1);
        }
        showOnDisplay(displayedInfo);
    }

    public void onButtonDotClick(View view){
        if(displayedInfo.length()<8) displayedInfo += ".";
        showOnDisplay(displayedInfo);

    }

    public void onButtonEqualClick(View view){

        Intent intent = new Intent(this, VaultActivity.class);
        startActivity(intent);

    }


    public void showOnDisplay(String str){
        setContentView(R.layout.activity_fullscreen);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(str);
    }

}
