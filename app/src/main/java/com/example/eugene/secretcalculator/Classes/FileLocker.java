package com.example.eugene.secretcalculator.Classes;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileLocker {


    public void writeToFile(String sFileName) {


        try {
            File root = new File(Environment.getExternalStorageDirectory(), ".TestFolder");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxFile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxFile);

            writer.flush();
            writer.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveFile( ) {

        String inputPath=Environment.getExternalStorageDirectory()+"/Pictures/";
        String inputFile = "1990.jpg";
        String outputPath = Environment.getExternalStorageDirectory() + "TestFolder/";

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath, "TestFolder");
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;

            // delete the original file
            new File(inputPath + inputFile).delete();


        }

        catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }
}
