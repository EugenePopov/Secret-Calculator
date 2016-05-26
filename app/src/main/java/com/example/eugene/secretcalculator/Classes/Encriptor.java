package com.example.eugene.secretcalculator.Classes;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Encriptor{

    String encryptedString;

    public  void encryptString() {
            String stringToEncrypt = "123456";
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(stringToEncrypt.getBytes());
                encryptedString = new String(messageDigest.digest());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

    public void writeToFile() {
        try{
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SecCalc/Config/");
            if (!directory.exists()) {
                directory.mkdirs();
                 File gpxFile = new File(directory, "pass.txt");
                 FileWriter writer = new FileWriter(gpxFile);
                 writer.append(encryptedString);
                 writer.flush();
                 writer.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean compareStrings(String inputString){

        String introducedEncryptedString = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(inputString.getBytes());
            introducedEncryptedString = new String(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return introducedEncryptedString.equals(readFromFile());
    }

    public String readFromFile() {
        String storedValue = "";

        try {
            InputStream inputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/SecCalc/Config/pass.txt"));
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder storedValueBuilder = new StringBuilder();

                //Read the rest of the file, which is the content of the note
                while ((storedValue = bufferedReader.readLine()) != null) {
                    storedValueBuilder.append(storedValue);
                }
                inputStream.close();
                storedValue = storedValueBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return storedValue;
    }


}
