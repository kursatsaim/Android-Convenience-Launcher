package com.example.helpme;

import static com.example.helpme.MomChooseAppsLayout.KEY_STRING_REAL_GIRL_APP_LIST;
import static com.example.helpme.MomChooseAppsLayout.SHARED_PREF;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;

public class ChildGirl extends AppCompatActivity {

    @Inject
    Context context;
    List<PicsAndAppNames> mainliste = new ArrayList<>();

    List<ApplicationInfo> launchableApps;
    List<Drawable> icons = new ArrayList<>();

    List<String> isimler = new ArrayList<>();

    String[] uyglist;
    public ArrayList<String> kızuyg;
    PackageManager packageManager;
    private ImageView GetVoiceButton;
    private SpeechRecognizer speechRecognizer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl);

        //kızınuyglarr = new GirlAppsList(this);
        kızuyg = new ArrayList<String>();
        launchableApps = new ArrayList<ApplicationInfo>();
        packageManager = getPackageManager();
        LoadSharedPrefs();
        GetVoiceButton = findViewById(R.id.mic_speak_iv);


        for (String packageName : kızuyg) {
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                launchableApps.add(applicationInfo);
            } catch (PackageManager.NameNotFoundException e) {

            }
        }

        //launchableApps = kızınuyglarr.GetGirlApps();

        RecyclerView recyclerView = findViewById(R.id.recviewkiz);

        uyglist = new String[launchableApps.size()];


        for(int i = 0; i < kızuyg.size(); i++)
        {
            ApplicationInfo applicationInfo = launchableApps.get(i);
            uyglist[i] = applicationInfo.packageName;
        }


        for(int i = 0; i < launchableApps.size(); i++)
        {
            ApplicationInfo applicationInfo = launchableApps.get(i);
            Drawable drawable = applicationInfo.loadIcon(getPackageManager());
            icons.add(drawable);
        }

        for(int i = 0; i < launchableApps.size(); i++)
        {
            ApplicationInfo applicationInfo = launchableApps.get(i);
            String s = getPackageManager().getApplicationLabel(applicationInfo).toString();
            isimler.add(s);
        }

        for(int i = 0; i< launchableApps.size();i++) {

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
                GetVoiceButton.setColorFilter(ContextCompat.getColor(ChildGirl.this, R.color.mic_enabled_color));
                startSpeechToText();
            }
        });
    }


    public void getchildgirlapps(ArrayList<String> arrayList)
    {
        kızuyg = arrayList;
    }

     public void LoadSharedPrefs()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_STRING_REAL_GIRL_APP_LIST,null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        kızuyg = gson.fromJson(json,type);
        if(kızuyg == null)
        {
            kızuyg = new ArrayList<String>();
        }
    }

    private void startSpeechToText() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {}

            @Override
            public void onBeginningOfSpeech() {}

            @Override
            public void onRmsChanged(float rmsdB) {}

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() {
                GetVoiceButton.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.mic_disabled_color));
            }

            @Override
            public void onError(int error) {}

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (result != null) {

                    if(isimler.contains(result.get(0)))
                    {
                        Intent intent = getPackageManager().getLaunchIntentForPackage(kızuyg.get(isimler.indexOf(result.get(0))));
                        startActivity(intent);

                    }
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {}

            @Override
            public void onEvent(int eventType, Bundle params) {}
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

    private void pop ()
    {
        Boolean bool = true;
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popoutview = layoutInflater.inflate(R.layout.activity_dupe_app_popout, null);


        int genis = ViewGroup.LayoutParams.MATCH_PARENT;
        int uzun = ViewGroup.LayoutParams.MATCH_PARENT;
        boolean disaritikla = true;
        final PopupWindow popsayfa = new PopupWindow(popoutview,genis,uzun,disaritikla);

        TextView layoutduzenle =(TextView) popsayfa.getContentView().findViewById(R.id.DupeAppPopoutText);
        Button iptalButton = (Button) popsayfa.getContentView().findViewById(R.id.DupeAppIptalButon);
        Button RemoveButton = (Button) popsayfa.getContentView().findViewById(R.id.DupeAppKaldırButon);


        /*iptalButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });*/

        iptalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popsayfa.dismiss();
            }
        });

        RemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popsayfa.dismiss();
            }
        });

        findViewById(R.id.momlayout).post(new Runnable() {
            public void run() {
                popsayfa.showAtLocation(findViewById(R.id.GirlLayout), Gravity.CENTER, 0, 0);
            }
        });

        popoutview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popsayfa.dismiss();
                return true;
            }
        });
    }
    public Context getContextGirl() {
        return this;
    }

}