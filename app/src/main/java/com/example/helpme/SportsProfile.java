package com.example.helpme;


import static com.example.helpme.ChooseSportsLayout.KEY_STRING_REAL_SPORTS_APP_LIST;
import static com.example.helpme.ChooseSportsLayout.SHARED_PREF_SPORTS;
import static com.example.helpme.SetBackgroundsAct.KEY_SHARED_PREF_NEWBACK_SPORTS;
import static com.example.helpme.SetBackgroundsAct.SHARED_PREF_NEWBACK_SPORTS;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.Context;
import android.content.SharedPreferences;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import android.Manifest;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.content.Intent;
import java.util.Locale;

public class SportsProfile extends AppCompatActivity {

    @Inject
    Context context;
    List<PicsAndAppNames> mainliste = new ArrayList<>();

    List<ApplicationInfo> launchableApps;
    List<Drawable> icons = new ArrayList<>();

    List<String> isimler = new ArrayList<>();

    String[] uyglist;
    public ArrayList<String> sportsuyg;
    PackageManager packageManager;
    ImageView backgroundpic;
    private Uri imageUri;
    private ImageView GetVoiceButton;

    private SpeechRecognizer speechRecognizer;

    public SportsProfile()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_profile);

        sportsuyg = new ArrayList<String>();
        launchableApps = new ArrayList<ApplicationInfo>();
        packageManager = getPackageManager();
        backgroundpic = findViewById(R.id.imageView);
        GetVoiceButton = findViewById(R.id.mic_speak_iv);

        LoadSharedPrefs();
        LoadSharePrefsForBackground();
        if(imageUri != null)
        {
            backgroundpic.setImageURI(imageUri);
        }

        for (String packageName : sportsuyg) {
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                launchableApps.add(applicationInfo);
            } catch (PackageManager.NameNotFoundException e) {

            }
        }

        RecyclerView recyclerView = findViewById(R.id.sportsrecview);

        uyglist = new String[launchableApps.size()];


        for(int i = 0; i < launchableApps.size(); i++)
        {
            ApplicationInfo applicationInfo = launchableApps.get(i);
            uyglist[i] = applicationInfo.packageName;
        }


        for(int i = 0; i < launchableApps.size(); i++)
        {
            ApplicationInfo applicationInfo = launchableApps.get(i);
            Drawable drawable = applicationInfo.loadIcon(getPackageManager());
            //Drawable drawable = applicationInfo.loadUnbadgedIcon(getPackageManager());
            icons.add(drawable);
        }

        for(int i = 0; i < launchableApps.size(); i++)
        {
            ApplicationInfo applicationInfo = launchableApps.get(i);
            String s = getPackageManager().getApplicationLabel(applicationInfo).toString();
            isimler.add(s);
        }

        for(int i = 0; i< launchableApps.size();i++)
        {
            mainliste.add(new PicsAndAppNames(icons.get(i), isimler.get(i)));
        }


        context = this;
        RAdapter adapt = new RAdapter(this,mainliste);
        recyclerView.setAdapter(adapt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GetVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAudioPermission();
                startSpeechToText();
            }
        });

    }

    public void getsportsapps(ArrayList<String> arrayList)
    {
        sportsuyg = arrayList;
    }

    public void LoadSharedPrefs()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_SPORTS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_STRING_REAL_SPORTS_APP_LIST,null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        sportsuyg = gson.fromJson(json,type);
        if(sportsuyg == null)
        {
            sportsuyg = new ArrayList<String>();
        }
    }

    private void LoadSharePrefsForBackground()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NEWBACK_SPORTS, Context.MODE_PRIVATE);
        String imagePath = sharedPreferences.getString(KEY_SHARED_PREF_NEWBACK_SPORTS, "");
        if (!imagePath.isEmpty()) {
            imageUri = Uri.parse(imagePath);
        }
    }

    public Context getContextSports() {
        return this;
    }

    private void startSpeechToText() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
            }

            @Override
            public void onBeginningOfSpeech() {
            }

            @Override
            public void onRmsChanged(float rmsdB) {
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int error) {
            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (result != null) {

                    if (isimler.contains(result.get(0))) {
                        Intent intent = getPackageManager().getLaunchIntentForPackage(sportsuyg.get(isimler.indexOf(result.get(0))));
                        startActivity(intent);

                    } else
                        Toast.makeText(context, "Söylediğiniz uygulama bu profilde bulunmamaktadır!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
            }
        });

        speechRecognizer.startListening(speechRecognizerIntent);
    }

    private void checkAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                Toast.makeText(this, "Lütfen mikrofon kullanımına izin verin.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Mikrofon kullanımı reddedildi.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}