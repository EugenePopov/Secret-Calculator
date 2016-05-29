package com.example.eugene.secretcalculator.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.eugene.secretcalculator.Classes.FileLocker;
import com.example.eugene.secretcalculator.Classes.SharedData;
import com.example.eugene.secretcalculator.R;

public class VaultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault);

        hideNavigationBar();
    }

    private void hideNavigationBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

 /*   @Override
    protected void onRestart(){
        super.onRestart();
        Intent i = new Intent(this,CalculatorActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }*/

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

    public void onButtonCloseClick(View view){
/*        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivity(intent);*/
        Intent i = new Intent(this,CalculatorActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }

    public void onButtonInfoClick(View view){
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);

    }

    public void onButtonMediaClick(View view){
        Intent intent = new Intent(this, MediaActivity.class);
        startActivity(intent);
    }

    public void onButtonNotesClick(View view){
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed(){
        /**Disable back button**/
    }


}
