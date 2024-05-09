package com.example.helpme;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.lang.Object;
import java.util.ArrayList;

public class SetBackgroundsAct extends AppCompatActivity {
    private static final String SHARED_PREF_BACK = "imageURI";
    private static final String KEY_SHARED_PREF_BACK = "imageURI";
    private static final String SHARED_PREF_NEWBACK_BOY = "imageURI";
    private static final String KEY_SHARED_PREF_NEWBACK_BOY = "imageURI";
    private static final String SHARED_PREF_NEWBACK_GIRL = "imageURI";
    private static final String KEY_SHARED_PREF_NEWBACK_GIRL = "imageURI";
    private static final String SHARED_PREF_NEWBACK_DAD = "imageURI";
    private static final String KEY_SHARED_PREF_NEWBACK_DAD = "imageURI";
    private static final String SHARED_PREF_NEWBACK_MOM = "imageURI";
    private static final String KEY_SHARED_PREF_NEWBACK_MOM = "imageURI";
    private static final String SHARED_PREF_NEWBACK_ACT1 = "imageURI";
    private static final String KEY_SHARED_PREF_NEWBACK_ACT1 = "imageURI";
    private static final String SHARED_PREF_NEWBACK_ACT2 = "imageURI";
    private static final String KEY_SHARED_PREF_NEWBACK_ACT2 = "imageURI";
    private static final String SHARED_PREF_NEWBACK_ACT3 = "imageURI";
    private static final String KEY_SHARED_PREF_NEWBACK_ACT3 = "imageURI";

    private Button dadButon,momButon,girlButon,boyButon,prof1Buton,prof2Buton,prof3Buton,anamenuButon;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_READ_STORAGE_PERMISSION = 2;
    private ImageView imageView;
    private Uri imageUri;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_backgrounds);
        LoadSharePrefs();
        imageView = findViewById(R.id.backgroundImageview);
        imageView.setImageURI(imageUri);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Arkaplanları değiştirmek için lütfen izni verin.", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_READ_STORAGE_PERMISSION);
        }

        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, RESULT_LOAD_IMAGE);



        };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            ContentResolver contentResolver = getContentResolver();
            contentResolver.takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            getContentResolver().getPersistedUriPermissions();
            imageView.setImageURI(imageUri);

            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_BACK,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor =sharedPreferences.edit();
            path = imageUri.getEncodedPath();
            editor.putString(KEY_SHARED_PREF_BACK, imageUri.toString());
            editor.apply();

        }
    }



    private void LoadSharePrefs()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_BACK,Context.MODE_PRIVATE);
        String imagePath = sharedPreferences.getString(KEY_SHARED_PREF_BACK, "");
        if (!imagePath.isEmpty()) {
            imageUri = Uri.parse(imagePath);
        }
    }

}
