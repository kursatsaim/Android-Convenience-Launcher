package com.example.helpme;

import static com.example.helpme.ChooseNewAct3Layout.KEY_STRING_REAL_NEWACT3_APP_LIST;
import static com.example.helpme.ChooseNewAct3Layout.SHARED_PREF_ACT3;
import static com.example.helpme.SetBackgroundsAct.KEY_SHARED_PREF_NEWBACK_ACT3;
import static com.example.helpme.SetBackgroundsAct.SHARED_PREF_NEWBACK_ACT3;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.Context;
import android.content.SharedPreferences;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
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

public class NewAct3 extends AppCompatActivity {


    @Inject
    Context context;
    List<PicsAndAppNames> mainliste = new ArrayList<>();

    List<ApplicationInfo> launchableApps;
    List<Drawable> icons = new ArrayList<>();

    List<String> isimler = new ArrayList<>();

    String[] uyglist;
    public ArrayList<String> act3uyg;
    PackageManager packageManager;
    ImageView backgroundpic;
    private Uri imageUri;

    public NewAct3()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_act3);

        act3uyg = new ArrayList<String>();
        launchableApps = new ArrayList<ApplicationInfo>();
        packageManager = getPackageManager();
        LoadSharedPrefs();
        LoadSharePrefsForBackground();
        if(imageUri != null)
        {
            backgroundpic.setImageURI(imageUri);
        }

        for (String packageName : act3uyg) {
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                launchableApps.add(applicationInfo);
            } catch (PackageManager.NameNotFoundException e) {

            }
        }

        RecyclerView recyclerView = findViewById(R.id.act3recview);

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

    }

    public void getact3apps(ArrayList<String> arrayList)
    {
        act3uyg = arrayList;
    }

    public void LoadSharedPrefs()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_ACT3, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_STRING_REAL_NEWACT3_APP_LIST,null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        act3uyg = gson.fromJson(json,type);
        if(act3uyg == null)
        {
            act3uyg = new ArrayList<String>();
        }
    }

    private void LoadSharePrefsForBackground()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NEWBACK_ACT3, Context.MODE_PRIVATE);
        String imagePath = sharedPreferences.getString(KEY_SHARED_PREF_NEWBACK_ACT3, "");
        if (!imagePath.isEmpty()) {
            imageUri = Uri.parse(imagePath);
        }
    }

    public Context getContextAct3() {
        return this;
    }

}