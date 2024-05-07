package com.example.helpme;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
public class NewAct3List {
    private static final String SHARED_PREFF = "NewAct3AppsListPrefs";
    private ArrayList<String> StringNewAct3AppList = new ArrayList<String>();
    private SharedPreferences prefs;
    private Context context;


    public NewAct3List(Context context) {
        prefs = context.getSharedPreferences(SHARED_PREFF,Context.MODE_PRIVATE);
    }

    public void AddStringNewAct3AppList(String stringNewAct3AppList)
    {
        StringNewAct3AppList.add(stringNewAct3AppList);
    }
    public void RemoveStringNewAct3AppList(String çıkarılcak)
    {
        StringNewAct3AppList.remove(çıkarılcak);
    }

    public void GetChosenApps(ArrayList<String> arrayList)
    {

        StringNewAct3AppList = arrayList;
    }

    public ArrayList<String> GetNewAct3Applist()
    {
        return StringNewAct3AppList;
    }
}
