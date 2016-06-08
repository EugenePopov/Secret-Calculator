package com.example.eugene.secretcalculator.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eugene.secretcalculator.Classes.PasswordHash;
import com.example.eugene.secretcalculator.R;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class FirstLaunchActivity extends AppCompatActivity {



    String sPassword;
    String sRepeatedPassword;
    String sEmail;
    String sSecretQuestion;
    String sSecretAnswer;

    String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);
        hideNavigationBar();
    }

    private void hideNavigationBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void onButtonDoneClick(View view){
        EditText password = (EditText)findViewById(R.id.editText3);
        EditText repeatedPassword = (EditText)findViewById(R.id.editText4);
        EditText email = (EditText)findViewById(R.id.editText5);
        EditText secretQuestion = (EditText)findViewById(R.id.editText6);
        EditText secretAnswer = (EditText)findViewById(R.id.editText7);

        sPassword = password.getText().toString();
        sRepeatedPassword = repeatedPassword.getText().toString();
        sEmail = email.getText().toString();
        sSecretQuestion = secretQuestion.getText().toString();
        sSecretAnswer = secretAnswer.getText().toString();

        if(isFormOk()) {
            PasswordHash e = new PasswordHash(this);
            try {
                e.generateStrongPasswordHash(sPassword);
            } catch (NoSuchAlgorithmException e1) {
                e1.printStackTrace();
            } catch (InvalidKeySpecException e1) {
                e1.printStackTrace();
            }
            e.writeToFile();

            Intent intent = new Intent(this, CalculatorActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, error,
                    Toast.LENGTH_LONG).show();
        }

    }

    private boolean isFormOk(){
        return arePasswordsIdentical() && isAllDataTyped();
    }

    private boolean arePasswordsIdentical(){
        if(sPassword.equals(sRepeatedPassword))return true;
        else{
            error = "Password are not identical !";
            return false;
        }
    }

    private boolean isAllDataTyped(){
        if(!sPassword.isEmpty()&&!sRepeatedPassword.isEmpty()&&!sEmail.isEmpty()&&!sSecretQuestion.isEmpty()&&!sSecretAnswer.isEmpty())return true;
        else{
            error = "Fill all the fields !";
            return false;
        }
    }




}
