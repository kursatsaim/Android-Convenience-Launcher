package com.example.helpme;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
public class NewAct2List {
    private static final String SHARED_PREFF = "NewAct2AppsListPrefs";
    private ArrayList<String> StringNewAct2AppList = new ArrayList<String>();
    private SharedPreferences prefs;
    private Context context;


    public NewAct2List(Context context) {
        prefs = context.getSharedPreferences(SHARED_PREFF,Context.MODE_PRIVATE);
    }

    public void AddStringNewAct2AppList(String stringNewAct2AppList)
    {
        StringNewAct2AppList.add(stringNewAct2AppList);
    }
    public void RemoveStringNewAct2AppList(String çıkarılcak)
    {
        StringNewAct2AppList.remove(çıkarılcak);
    }

    public void GetChosenApps(ArrayList<String> arrayList)
    {

        StringNewAct2AppList = arrayList;
    }

    public ArrayList<String> GetNewAct2Applist()
    {
        return StringNewAct2AppList;
    }
}
