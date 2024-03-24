package com.example.helpme;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class BoyAppsList {
    private static final String SHARED_PREFF = "BoyAppsListPrefs";
    private static final String KEY_STRING_REAL_BOY_APP_LIST = "KeyRealBoyAppList";
    private ArrayList<String> StringRealBoyAppList = new ArrayList<String>();
    private SharedPreferences prefs;

    public BoyAppsList(Context context) {
        prefs = context.getSharedPreferences(SHARED_PREFF,Context.MODE_PRIVATE);
    }

    public void AddStringRealBoyAppList(String stringRealGirlAppList)
    {
        StringRealBoyAppList.add(stringRealGirlAppList);
    }
    public void RemoveStringRealBoyAppList(String çıkarılcak)
    {
        StringRealBoyAppList.remove(çıkarılcak);
    }

    public void GetChosenApps(ArrayList<String> arrayList)
    {

        StringRealBoyAppList = arrayList;
    }

    public ArrayList<String> GetBoyApplist()
    {
        return StringRealBoyAppList;
    }

}
