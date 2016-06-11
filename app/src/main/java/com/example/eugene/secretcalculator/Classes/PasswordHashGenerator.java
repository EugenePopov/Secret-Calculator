package com.example.eugene.secretcalculator.Classes;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;



public class PasswordHashGenerator {

    private Context context;
    private String encryptedString = " ";

    public PasswordHashGenerator(Context context){
        this.context = context;
    }


    public void generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        encryptedString = toHex(hash);
    }

    /** Generate salt based on the device ID**/
    private  byte[] getSalt() throws NoSuchAlgorithmException {
        String androidId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        byte[] salt = androidId.getBytes();
        return salt;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    public void writeToFile() {
        try{
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SecCalc/Config/");

                directory.mkdirs();
                File gpxFile = new File(directory, "pass.txt");
                FileWriter writer = new FileWriter(gpxFile);
                writer.append(encryptedString);
                writer.flush();
                writer.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean compareStrings(String inputString) throws NoSuchAlgorithmException, InvalidKeySpecException{

        String introducedEncryptedString = "";
        try {
            int iterations = 1000;
            char[] chars = inputString.toCharArray();
            byte[] salt = getSalt();

            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            introducedEncryptedString = toHex(hash);
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

                while ((storedValue = bufferedReader.readLine()) != null) {
                    storedValueBuilder.append(storedValue);
                }
                inputStream.close();
                storedValue = storedValueBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storedValue;
    }

}
