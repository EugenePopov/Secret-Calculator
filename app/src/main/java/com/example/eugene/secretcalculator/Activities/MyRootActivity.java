package com.example.eugene.secretcalculator.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MyRootActivity extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivity(intent);
    }

}