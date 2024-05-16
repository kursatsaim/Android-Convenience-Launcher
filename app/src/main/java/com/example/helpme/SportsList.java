package com.example.helpme;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;

public class SportsList {

    private static final String SHARED_PREFF = "SportsAppsListPrefs";
    private ArrayList<String> StringSportsAppList = new ArrayList<String>();
    private SharedPreferences prefs;
    private Context context;

    public SportsList(Context context)
    {
        prefs = context.getSharedPreferences(SHARED_PREFF,Context.MODE_PRIVATE);
    }

    public void AddStringSportsAppList(String stringSportsAppList)
    {
        StringSportsAppList.add(stringSportsAppList);
    }
    public void RemoveStringSportsAppList(String çıkarılcak)
    {
        StringSportsAppList.remove(çıkarılcak);
    }

    public void GetChosenApps(ArrayList<String> arrayList)
    {

        StringSportsAppList = arrayList;
    }

    public ArrayList<String> GetSportsApplist()
    {
        return StringSportsAppList;
    }

}
