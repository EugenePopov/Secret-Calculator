package com.example.eugene.secretcalculator.Activities;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ViewSwitcher;
import com.example.eugene.secretcalculator.MultipleImagePicker.Action;
import com.example.eugene.secretcalculator.MultipleImagePicker.Image;
import com.example.eugene.secretcalculator.MultipleImagePicker.GalleryAdapter;
import com.example.eugene.secretcalculator.MultipleImagePicker.Utility;
import com.example.eugene.secretcalculator.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class AlbumActivity extends AppCompatActivity {

    GridView gridGallery;
    Handler handler;
    GalleryAdapter adapter;
    SQLiteDatabase db ;

    ImageButton btnGalleryPickMul;
    ArrayList<Image> imageList = new ArrayList<>();
    ArrayList<String> imagePathList = new ArrayList<>();

    ViewSwitcher viewSwitcher;
    ImageLoader imageLoader;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_album);
        hideNavigationBar();
        initImageLoader();
        init();
        initializeContext();
        //updateGridView();
       // this.deleteDatabase("imgs.db");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        deleteTemporaryFolder();
    }





    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                this).defaultDisplayImageOptions(defaultOptions).memoryCache(
                new WeakMemoryCache());

        ImageLoaderConfiguration config = builder.build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        imageLoader.clearDiscCache();
        imageLoader.clearMemoryCache();
    }

    private void init() {

        handler = new Handler();
        gridGallery = (GridView) findViewById(R.id.gridGallery);
        gridGallery.setFastScrollEnabled(true);
        adapter = new GalleryAdapter(getApplicationContext(), imageLoader);
        adapter.setMultiplePick(false);
        gridGallery.setAdapter(adapter);

        viewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
        viewSwitcher.setDisplayedChild(1);

//        imgSinglePick = (ImageView) findViewById(R.id.imgSinglePick);



        btnGalleryPickMul = (ImageButton) findViewById(R.id.imageButton20);
        btnGalleryPickMul.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Action.ACTION_MULTIPLE_PICK);
                startActivityForResult(i, 200);
            }
        });

        gridGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                String imagePath = imagePathList.get(position);
                Intent intent = new Intent(AlbumActivity.this, FullsizeImageActivity.class);
                intent.putExtra("string", imagePath);
                startActivity(intent);

            }
        });

    }

    public void onButtonBackClick(View view){
        Intent intent = new Intent(this, VaultActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            String[] selectedImages = data.getStringArrayExtra("selectedImages");

            for (String string : selectedImages) {
                Image item = new Image();
                item.setSdcardPath(string);

                imageList.add(item);
                imagePathList.add(item.getSdcardPath());
            }

            viewSwitcher.setDisplayedChild(0);
            adapter.addAll(imageList);
        }
    }

    private void hideNavigationBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void deleteTemporaryFolder(){
        File dir = new File(Environment.getExternalStorageDirectory()+"/SecCalc/Media/thumbnails/");

            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
    }

    private void initializeContext(){

            db = this.openOrCreateDatabase("imgs.db", this.MODE_PRIVATE, null);
            db.execSQL("create table if not exists tb (a blob, b text)");
            populateImageList t = new populateImageList();
            t.execute();
    }


    public class populateImageList extends AsyncTask<Void, Void, ArrayList<String> > {

        public ProgressDialog mProgressDialog;
        //Context context;
        //SQLiteDatabase db;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(AlbumActivity.this);
            mProgressDialog.setTitle("Loading images");
            mProgressDialog.setMessage("Please, don't close the app...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            File directory = getDirectoryPath();

            Cursor c = db.rawQuery("select * from tb", null);

            if (c.moveToFirst()) {
                do {
                    byte[] blob = c.getBlob(c.getColumnIndex("a"));
                    String filename = c.getString(c.getColumnIndex("b"));
                    File imagePath = new File(directory, filename);
                    imagePathList.add(imagePath.getPath());
                    saveToFile(imagePath.getPath(), Utility.getPhoto(blob));
                } while (c.moveToNext());
            }
            c.close();

            return imagePathList;
        }

        private void saveToFile(String filename, Bitmap bmp) {
            try {
                FileOutputStream out = new FileOutputStream(filename);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        public File getDirectoryPath() {
            File path;

            path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/SecCalc/Media/thumbnails/");
            if(!path.exists()) {
                path.mkdirs();
            }
            return path;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            imagePathList = result;
            mProgressDialog.dismiss();
            db.close();
            updateGridView();


        }
    }

    public void updateGridView(){

        for (String string : imagePathList) {
            Image item = new Image();
            item.setSdcardPath(string);

            imageList.add(item);
        }

        viewSwitcher.setDisplayedChild(0);
        adapter.addAll(imageList);

    }

}