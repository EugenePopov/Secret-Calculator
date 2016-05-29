package com.example.eugene.secretcalculator.Classes;


import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Note extends VaultEntity implements Parcelable{

    private String content;
    private String date;
    private String title;
    private String sourceFileName;

    public Note(){
        content = " ";
        date = " ";
        title = " ";
        sourceFileName = " ";
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setTitle(String title){ this.title = title; }

    public void setSourceFileName(String sourceFileName){ this.sourceFileName = sourceFileName; }

    public String getTitle(){return title;}

    public String getContent(){return content;}

    public String getDate(){return date;}

    public String getSourceFileName(){return sourceFileName;}

    @Override
    public void createOnLocalStorage(String fileName) {
        try{
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SecCalc/Notes/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File gpxFile = new File(directory, fileName + ".txt");
        FileWriter writer = new FileWriter(gpxFile);
        writer.append(title + "\n" + date + "\n" + sourceFileName + "\n" + content);
        writer.flush();
        writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void deleteFromLocalStorage(){
        File note = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/SecCalc/Notes/" + sourceFileName + ".txt");
        note.delete();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(content);
        parcel.writeString(date);
        parcel.writeString(title);
        parcel.writeString(sourceFileName);
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        //Unpack object from Parcel
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    private Note(Parcel parcel) {
        content = parcel.readString();
        date = parcel.readString();
        title = parcel.readString();
        sourceFileName = parcel.readString();
    }

}
