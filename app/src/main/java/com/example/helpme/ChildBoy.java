package com.example.helpme;

import static com.example.helpme.MomChooseAppsForBoy.KEY_STRING_REAL_BOY_APP_LIST;
import static com.example.helpme.MomChooseAppsForBoy.SHARED_PREF_BOY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChildBoy extends AppCompatActivity {

    List<PicsAndAppNames> mainliste = new ArrayList<>();

    List<ApplicationInfo> launchableApps;
    List<Drawable> icons = new ArrayList<>();

    List<String> isimler = new ArrayList<>();

    String[] uyglist;
    public ArrayList<String> boyuyg;
    PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boy);

        boyuyg = new ArrayList<String>();
        launchableApps = new ArrayList<ApplicationInfo>();
        packageManager = getPackageManager();
        LoadSharedPrefs();

        for (String packageName : boyuyg) {
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                launchableApps.add(applicationInfo);
            } catch (PackageManager.NameNotFoundException e) {

            }
        }


        RecyclerView recyclerView = findViewById(R.id.RView);

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



        RAdapter adapt = new RAdapter(this,mainliste);
        recyclerView.setAdapter(adapt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    public void sil (String silincekuyg)
    {
        for (ApplicationInfo appInfo : launchableApps) {
            if (appInfo.packageName.equals(silincekuyg)) {
                launchableApps.remove(appInfo);
                break;
            }
        }
    }
    public void getchildboyapps(ArrayList<String> arrayList)
    {
        boyuyg = arrayList;

    }

    public void LoadSharedPrefs()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_BOY, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_STRING_REAL_BOY_APP_LIST,null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        boyuyg = gson.fromJson(json,type);
        if(boyuyg == null)
        {
            boyuyg = new ArrayList<String>();
        }
    }

}