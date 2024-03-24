package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MomChooseAppsLayout extends AppCompatActivity {

    List<PicsAndAppNames> mainliste = new ArrayList<>();
    List<ApplicationInfo> launchableApps;
    List<Drawable> icons = new ArrayList<>();
    List<String> isimler = new ArrayList<>();
    String[] uyglist;
    GirlAppsList girlAppsList;
    ArrayList<String> chosenapps;
    public static final String SHARED_PREF = "GirlAppsListPrefs";
    public static final String KEY_STRING_REAL_GIRL_APP_LIST = "KeyRealGirlAppList";
    SharedPreferences prefs;
    String json;
    ChildGirl childGirl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mom_choose_apps_layout);

        girlAppsList = new GirlAppsList(this);
        childGirl = new ChildGirl();

        launchableApps = getLaunchableApps();

        sil("com.google.android.googlequicksearchbox");

        uyglist = new String[launchableApps.size()];

        RecyclerView recyclerView = findViewById(R.id.momchooserecview);

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

        for (int i = 0; i<uyglist.length;i++){
            String[] strings = new String[]{uyglist[i]};

        }

        UygKutuAdapt adapt = new UygKutuAdapt(this,mainliste,girlAppsList);
        recyclerView.setAdapter(adapt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LoadSharedPrefs();
        girlAppsList.GetChosenApps(chosenapps);
        childGirl.getchildgirlapps(chosenapps);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveSharedPrefss();
    }

    private List<ApplicationInfo> getLaunchableApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(mainIntent, 0);

        List<ApplicationInfo> launchableApps = new ArrayList<>();

        for (ResolveInfo resolveInfo : activities) {
            launchableApps.add(resolveInfo.activityInfo.applicationInfo);
        }

        return launchableApps;
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

    public void SaveSharedPrefss()
    {
        chosenapps = new ArrayList<String>();
        chosenapps = girlAppsList.GetGirlApplist();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();

        if(chosenapps.size() > 0 ) {
            //chosenapps.clear();
            Gson gson = new Gson();
            json = gson.toJson(chosenapps);
            int x = 3;
            editor.putString(KEY_STRING_REAL_GIRL_APP_LIST, json);
            editor.apply();
        }
    }

    public void LoadSharedPrefs()
    {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_STRING_REAL_GIRL_APP_LIST,"");
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        chosenapps = gson.fromJson(json,type);
        if(chosenapps == null)
        {
            chosenapps = new ArrayList<String>();
        }

    }

}