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


public class GirlAppsList {

    private static final String SHARED_PREFF = "GirlAppsListPrefs";
    private static final String KEY_STRING_REAL_GIRL_APP_LIST = "KeyRealGirlAppList";
    private ArrayList<ApplicationInfo> RealGirlAppList = new ArrayList<ApplicationInfo>();
    private ArrayList<String> StringRealGirlAppList = new ArrayList<String>();
    private Context context;
    private SharedPreferences prefs;
    private MomChooseAppsLayout momChooseAppsLayout;

    public GirlAppsList(Context context) {
        prefs = context.getSharedPreferences(SHARED_PREFF,Context.MODE_PRIVATE);
    }


    public ArrayList<String> getStringRealGirlAppList() {
        return StringRealGirlAppList;
    }

    public void setStringRealGirlAppList(ArrayList<String> stringRealGirlAppList) {
        StringRealGirlAppList = stringRealGirlAppList;
    }
    public void AddStringRealGirlAppList(String stringRealGirlAppList)
    {
        StringRealGirlAppList.add(stringRealGirlAppList);
    }
    public void RemoveStringRealGirlAppList(String çıkarılcak)
    {
        StringRealGirlAppList.remove(çıkarılcak);
    }

    public ArrayList<ApplicationInfo> GetGirlApps()
    {
        return RealGirlAppList;
    }

    public void SetGirlApps(ArrayList<ApplicationInfo> gercekkızuyglist)
    {
        this.RealGirlAppList = gercekkızuyglist;
    }

    public void AddGirlApps(ApplicationInfo applicationInfo)
    {
        if(RealGirlAppList != null)
        {
            RealGirlAppList.add(applicationInfo);
        }
    }

    public void KızUygÇıkar (ApplicationInfo applicationInfo)
    {
        if(RealGirlAppList != null)
        {
            RealGirlAppList.remove(applicationInfo);
        }
    }




   public void GetChosenApps(ArrayList<String> arrayList)
   {

       StringRealGirlAppList = arrayList;
   }

   public ArrayList<String> GetGirlApplist()
   {
      return StringRealGirlAppList;
   }

}
