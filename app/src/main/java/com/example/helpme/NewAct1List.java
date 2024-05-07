package com.example.helpme;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;

public class NewAct1List {

    private static final String SHARED_PREFF = "NewAct1AppsListPrefs";
    private ArrayList<String> StringNewAct1AppList = new ArrayList<String>();
    private SharedPreferences prefs;
    private Context context;


    public NewAct1List(Context context) {
        prefs = context.getSharedPreferences(SHARED_PREFF,Context.MODE_PRIVATE);
    }

    public void AddStringNewAct1AppList(String stringNewAct1AppList)
    {
        StringNewAct1AppList.add(stringNewAct1AppList);
    }
    public void RemoveStringNewAct1AppList(String çıkarılcak)
    {
        StringNewAct1AppList.remove(çıkarılcak);
    }

    public void GetChosenApps(ArrayList<String> arrayList)
    {

        StringNewAct1AppList = arrayList;
    }

    public ArrayList<String> GetNewAct1Applist()
    {
        return StringNewAct1AppList;
    }

}
