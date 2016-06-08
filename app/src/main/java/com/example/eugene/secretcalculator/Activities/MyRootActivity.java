package com.example.eugene.secretcalculator.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;

public class MyRootActivity extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SecCalc/Config/");
        if(!directory.exists()){
            Intent intent = new Intent(this, FirstLaunchActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(this, CalculatorActivity.class);
            startActivity(intent);
            finish();
        }
    }

}