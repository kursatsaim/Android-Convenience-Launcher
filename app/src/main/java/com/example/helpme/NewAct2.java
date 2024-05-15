package com.example.helpme;

import static com.example.helpme.ChooseNewAct2Layout.KEY_STRING_REAL_NEWACT2_APP_LIST;
import static com.example.helpme.ChooseNewAct2Layout.SHARED_PREF_ACT2;
import static com.example.helpme.SetBackgroundsAct.KEY_SHARED_PREF_NEWBACK_ACT2;
import static com.example.helpme.SetBackgroundsAct.SHARED_PREF_NEWBACK_ACT2;


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

public class NewAct2 extends AppCompatActivity {


    @Inject
    Context context;
    List<PicsAndAppNames> mainliste = new ArrayList<>();

    List<ApplicationInfo> launchableApps;
    List<Drawable> icons = new ArrayList<>();

    List<String> isimler = new ArrayList<>();

    String[] uyglist;
    public ArrayList<String> act2uyg;
    PackageManager packageManager;
    ImageView backgroundpic;
    private Uri imageUri;
    public NewAct2()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_act2);

        act2uyg = new ArrayList<String>();
        launchableApps = new ArrayList<ApplicationInfo>();
        backgroundpic = findViewById(R.id.imageView);
        packageManager = getPackageManager();
        LoadSharedPrefs();
        LoadSharePrefsForBackground();
        if(imageUri != null)
        {
            backgroundpic.setImageURI(imageUri);
        }

        for (String packageName : act2uyg) {
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                launchableApps.add(applicationInfo);
            } catch (PackageManager.NameNotFoundException e) {

            }
        }

        RecyclerView recyclerView = findViewById(R.id.act2recview);

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

    public void getact2apps(ArrayList<String> arrayList)
    {
        act2uyg = arrayList;
    }

    public void LoadSharedPrefs()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_ACT2, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_STRING_REAL_NEWACT2_APP_LIST,null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        act2uyg = gson.fromJson(json,type);
        if(act2uyg == null)
        {
            act2uyg = new ArrayList<String>();
        }
    }

    private void LoadSharePrefsForBackground()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NEWBACK_ACT2, Context.MODE_PRIVATE);
        String imagePath = sharedPreferences.getString(KEY_SHARED_PREF_NEWBACK_ACT2, "");
        if (!imagePath.isEmpty()) {
            imageUri = Uri.parse(imagePath);
        }
    }
    public Context getContextAct2() {
        return this;
    }

}