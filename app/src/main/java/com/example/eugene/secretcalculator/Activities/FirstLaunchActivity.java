package com.example.eugene.secretcalculator.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.eugene.secretcalculator.Classes.PasswordHashGenerator;
import com.example.eugene.secretcalculator.Kairos.KairosService;
import com.example.eugene.secretcalculator.MailClient.Client;
import com.example.eugene.secretcalculator.R;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ExecutionException;


public class FirstLaunchActivity extends AppCompatActivity {

    String sPassword;
    String sRepeatedPassword;
    String sEmail;
    String sSecretQuestion;
    String sSecretAnswer;
    String error;
    private static final String PASSWORD_ERROR = "Passwords are not identical!";
    private static final String FIELDS_ERROR = "Fill all the fields!";
    private static final String NETWORK_ERROR = "You need a network connection for the first launch of the application. Please turn on mobile network or Wi-Fi in Settings.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideNavigationBar();

        if(!isNetworkAvailable()){
            createNetErrorDialog();
        }
            setContentView(R.layout.activity_first_launch);

    }

    private void hideNavigationBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
            actionBar.hide();
        }
    }

    public void onButtonDoneClick(View view) throws IOException, ExecutionException, InterruptedException {
        //Client c= new Client();
        EditText password = (EditText)findViewById(R.id.editText3);
        EditText repeatedPassword = (EditText)findViewById(R.id.editText4);
        //EditText email = (EditText)findViewById(R.id.editText5);
        //EditText secretQuestion = (EditText)findViewById(R.id.editText6);
        //EditText secretAnswer = (EditText)findViewById(R.id.editText7);


        if(password!=null) {
            sPassword = password.getText().toString();
        }
        if(repeatedPassword!=null) {
            sRepeatedPassword = repeatedPassword.getText().toString();
        }
/*        if(email!=null) {
            sEmail = email.getText().toString();
        }
        if(secretQuestion!=null) {
            sSecretQuestion = secretQuestion.getText().toString();
        }
        if(secretAnswer!=null) {
            sSecretAnswer = secretAnswer.getText().toString();
        }*/

        if(isFormOk()) {

            String toServer = sEmail + "@@@" + sPassword;
            PasswordHashGenerator gen = new PasswordHashGenerator(this);
            try {
                gen.generateStrongPasswordHash(sPassword);
            } catch (NoSuchAlgorithmException|InvalidKeySpecException e) {
                e.printStackTrace();
            }


            gen.writeToFile();
            takePhoto();
/*            Intent intent = new Intent(this, CalculatorActivity.class);
            startActivity(intent);
            finish();*/
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
            error = PASSWORD_ERROR;
            return false;
        }
    }

    private boolean isAllDataTyped(){
        if(!sPassword.isEmpty()&&!sRepeatedPassword.isEmpty())return true;
        else{
            error = FIELDS_ERROR;
            return false;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    protected void createNetErrorDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(NETWORK_ERROR)
                .setTitle("Unable to connect")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(i);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                FirstLaunchActivity.this.finish();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void takePhoto(){
        int TAKE_PHOTO_CODE = 0;

        //final String dir = Environment.getDataDirectory() + "/userFolder/";
        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/userPhotos/";
        File newdir = new File(dir);
        newdir.mkdirs();
        String file = dir+"user.jpg";
        File newfile = new File(file);
        try {
            newfile.createNewFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Uri outputFileUri = Uri.fromFile(newfile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        KairosService ks = new KairosService(this);
        try {
            ks.registerUserPhoto();
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivity(intent);

    }



}
