package com.example.eugene.secretcalculator.Kairos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import com.kairos.Kairos;
import com.kairos.KairosListener;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import java.io.UnsupportedEncodingException;

import static com.example.eugene.secretcalculator.Kairos.KairosData.KAIROS_API_KEY;
import static com.example.eugene.secretcalculator.Kairos.KairosData.KAIROS_APP_ID;

public class KairosService {

    private Kairos kairos;

    public KairosService(Context context) {
        kairos = new Kairos();
        kairos.setAuthentication(context, KAIROS_APP_ID, KAIROS_API_KEY);
    }

    public void registerUserPhoto() throws UnsupportedEncodingException, JSONException {

        KairosListener listener = new KairosListener() {

            @Override
            public void onSuccess(String response) {
                // your code here!
                Log.d("KAIROS DEMO", response);
            }

            @Override
            public void onFail(String response) {
                // your code here!
                Log.d("KAIROS DEMO", response);
            }
        };

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        String photoPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/userPhotos/user.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        kairos.enroll(bitmap, generateId(), "myGallery", null, null, null, listener);


    }

    private String generateId(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomStringUtils.random( 100, characters );
    }
}
