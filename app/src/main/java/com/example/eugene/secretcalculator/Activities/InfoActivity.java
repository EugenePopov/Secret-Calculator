package com.example.eugene.secretcalculator.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.eugene.secretcalculator.Classes.SharedData;
import com.example.eugene.secretcalculator.R;


public class InfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        hideNavigationBar();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (SharedData.runningActivities == 0) {
            Intent i = new Intent(this,CalculatorActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        SharedData.runningActivities++;
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedData.runningActivities--;
        if (SharedData.runningActivities == 0) {
            // app goes to background
        }
    }

/*    protected void onRestart(){
        super.onRestart();
        Intent i = new Intent(this,CalculatorActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }*/
    private void hideNavigationBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void onButtonBackMenuClick(View view){
        Intent intent = new Intent(this, VaultActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        /**Disable back button**/
    }



}
