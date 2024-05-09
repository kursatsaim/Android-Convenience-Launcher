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
    public static final String SHARED_PREF_BACK = "imageURI";
    public static final String KEY_SHARED_PREF_BACK = "imageURI";
    public static final String SHARED_PREF_NEWBACK_BOY = "imageURI";
    public static final String KEY_SHARED_PREF_NEWBACK_BOY = "imageURI";
    public static final String SHARED_PREF_NEWBACK_GIRL = "imageURI";
    public static final String KEY_SHARED_PREF_NEWBACK_GIRL = "imageURI";
    public static final String SHARED_PREF_NEWBACK_DAD = "imageURI";
    public static final String KEY_SHARED_PREF_NEWBACK_DAD = "imageURI";
    public static final String SHARED_PREF_NEWBACK_MOM = "imageURI";
    public static final String KEY_SHARED_PREF_NEWBACK_MOM = "imageURI";
    public static final String SHARED_PREF_NEWBACK_ACT1 = "imageURI";
    public static final String KEY_SHARED_PREF_NEWBACK_ACT1 = "imageURI";
    public static final String SHARED_PREF_NEWBACK_ACT2 = "imageURI";
    public static final String KEY_SHARED_PREF_NEWBACK_ACT2 = "imageURI";
    public static final String SHARED_PREF_NEWBACK_ACT3 = "imageURI";
    public static final String KEY_SHARED_PREF_NEWBACK_ACT3 = "imageURI";
    public static final String SHARED_PREF_NEWBACK_MAIN = "imageURI";
    public static final String KEY_SHARED_PREF_NEWBACK_MAIN = "imageURI";

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
        dadButon = findViewById(R.id.goDad);
        momButon = findViewById(R.id.goMother);
        girlButon = findViewById(R.id.goChildGirl);
        boyButon = findViewById(R.id.goChildBoy);
        prof1Buton = findViewById(R.id.YeniProfil1);
        prof2Buton = findViewById(R.id.YeniProfil2);
        prof3Buton = findViewById(R.id.YeniProfil3);
        anamenuButon = findViewById(R.id.goMainMenu);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,"Arkaplanları değiştirmek için lütfen izni verin.", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_READ_STORAGE_PERMISSION);
        }


        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, RESULT_LOAD_IMAGE);

        Toast.makeText(this,"Hangi profilin arkaplanını değiştirmek istediğinizi seçiniz",Toast.LENGTH_LONG).show();

        dadButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedForDad();
                OpenDadAct();
            }
        });

        momButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedForMom();
                OpenMomAct();
            }
        });

        girlButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedForGirl();
                OpenGirlAct();
            }
        });

        boyButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedForBoy();
                OpenBoyAct();
            }
        });

        prof1Buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedForProf1();
                OpenProf1Act();
            }
        });

        prof2Buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedForProf2();
                OpenProf2Act();
            }
        });

        prof3Buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedForProf3();
                OpenProf3Act();
            }
        });

        anamenuButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedForMain();
                OpenMainAct();
            }
        });

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

    private void saveSharedForDad()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NEWBACK_DAD,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        path = imageUri.getEncodedPath();
        editor.putString(KEY_SHARED_PREF_NEWBACK_DAD, imageUri.toString());
        editor.apply();
    }

    private void saveSharedForMom()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NEWBACK_MOM,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        path = imageUri.getEncodedPath();
        editor.putString(KEY_SHARED_PREF_NEWBACK_MOM, imageUri.toString());
        editor.apply();
    }

    private void saveSharedForGirl()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NEWBACK_GIRL,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        path = imageUri.getEncodedPath();
        editor.putString(KEY_SHARED_PREF_NEWBACK_GIRL, imageUri.toString());
        editor.apply();
    }

    private void saveSharedForBoy()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NEWBACK_BOY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        path = imageUri.getEncodedPath();
        editor.putString(KEY_SHARED_PREF_NEWBACK_BOY, imageUri.toString());
        editor.apply();
    }

    private void saveSharedForProf1()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NEWBACK_ACT1,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        path = imageUri.getEncodedPath();
        editor.putString(KEY_SHARED_PREF_NEWBACK_ACT1, imageUri.toString());
        editor.apply();
    }
    private void saveSharedForProf2()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NEWBACK_ACT2,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        path = imageUri.getEncodedPath();
        editor.putString(KEY_SHARED_PREF_NEWBACK_ACT2, imageUri.toString());
        editor.apply();
    }

    private void saveSharedForProf3()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NEWBACK_ACT3,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        path = imageUri.getEncodedPath();
        editor.putString(KEY_SHARED_PREF_NEWBACK_ACT3, imageUri.toString());
        editor.apply();
    }

    private void saveSharedForMain()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NEWBACK_MAIN,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        path = imageUri.getEncodedPath();
        editor.putString(KEY_SHARED_PREF_NEWBACK_MAIN, imageUri.toString());
        editor.apply();
    }

    public void OpenMomAct(){
        Toast.makeText(this,"Arkaplan başarıyla değiştirildi!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, Mom.class);
        startActivity(intent);
    }

    public void OpenDadAct(){
        Toast.makeText(this,"Arkaplan başarıyla değiştirildi!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,Dad.class);
        startActivity(intent);
    }
    public void OpenProf3Act() {
        Toast.makeText(this,"Arkaplan başarıyla değiştirildi!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, NewAct3.class);
        startActivity(intent);
    }
    public void OpenProf2Act() {
        Toast.makeText(this,"Arkaplan başarıyla değiştirildi!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, NewAct2.class);
        startActivity(intent);
    }
    public void OpenProf1Act() {
        Toast.makeText(this,"Arkaplan başarıyla değiştirildi!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, New_Added_Act1.class);
        startActivity(intent);
    }
    public void OpenBoyAct(){
        Toast.makeText(this,"Arkaplan başarıyla değiştirildi!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ChildBoy.class);
        startActivity(intent);
    }
    public void OpenGirlAct(){
        Toast.makeText(this,"Arkaplan başarıyla değiştirildi!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ChildGirl.class);
        startActivity(intent);
    }
    public void OpenMainAct(){
        Toast.makeText(this,"Arkaplan başarıyla değiştirildi!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
