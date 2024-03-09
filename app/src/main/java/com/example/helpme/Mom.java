package com.example.helpme;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Mom extends AppCompatActivity {

    List<PicsAndAppNames> mainliste = new ArrayList<>();

    List<ApplicationInfo> launchableApps;
    List<Drawable> icons = new ArrayList<>();

    List<String> isimler = new ArrayList<>();

    String[] uyglist;

    int mode;

    TextView yenitext;
    String kullan;

    String[] istediklerim;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mom);

        checkperm();



        getzaman();

        pop();



        launchableApps = getLaunchableApps();

        sil("com.google.android.googlequicksearchbox");

        uyglist = new String[launchableApps.size()];

        RecyclerView recyclerView = findViewById(R.id.recviewmom);

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

        AdultAdapter adapt = new AdultAdapter(this, mainliste);
        recyclerView.setAdapter(adapt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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

    

    private void pop ()
    {
        
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popoutview = layoutInflater.inflate(R.layout.activity_popout_window, null);

//        PopoutWindow popoutWindow = new PopoutWindow();
//        popoutWindow.popupyaz();




        int genis = ViewGroup.LayoutParams.MATCH_PARENT;
        int uzun = ViewGroup.LayoutParams.MATCH_PARENT;
        boolean disaritikla = true;
        final PopupWindow popsayfa = new PopupWindow(popoutview,genis,uzun,disaritikla);

        TextView layoutduzenle =(TextView) popsayfa.getContentView().findViewById(R.id.popoutTextpopout);
        Button butonucalistir = (Button) popsayfa.getContentView().findViewById(R.id.popupkapa);
        Button bunuda = (Button) popsayfa.getContentView().findViewById(R.id.annesecilebiliruyglisteac);

        layoutduzenle.setText(kullan);

        butonucalistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popsayfa.dismiss();
            }
        });

        bunuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            anneuygsecac();
            }
        });

        findViewById(R.id.momlayout).post(new Runnable() {
            public void run() {
                popsayfa.showAtLocation(findViewById(R.id.momlayout), Gravity.CENTER, 0, 0);
            }
        });

        popoutview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popsayfa.dismiss();
                return true;
            }
        });
    }

    public void anneuygsecac()
    {
        Intent intent = new Intent(this, MomChooseAppsLayout.class);
        startActivity(intent);
    }


    public void checkperm()
    {
        AppOpsManager appOps = (AppOpsManager)
                getSystemService(Context.APP_OPS_SERVICE);
         mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());

        if(mode != AppOpsManager.MODE_ALLOWED)
        {
            startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),0);
        }
    }

    public void getzaman()
    {

        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar tarih = Calendar.getInstance();
        tarih.add(Calendar.DAY_OF_MONTH,-1);
        List<UsageStats> zamanliste = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                tarih.getTimeInMillis(), System.currentTimeMillis());
        istediklerim = new String[] {"com.android.settings", "com.android.deskclock", "com.google.android.calculator"};
        kullan = "";
        PackageManager packageManager = getPackageManager();
//        for (int i = 0; i < zamanliste.size(); i++)
//        {
//            try {
//                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(zamanliste.get(i).getPackageName(), 0);
//
//
//                String uygulamaAdi = (String) packageManager.getApplicationLabel(applicationInfo);
//                kullan = kullan + "Uygulama ismi: " + uygulamaAdi + "\n" +
//                        "Son kullanılan tarih: " + zamanal(zamanliste.get(i).getLastTimeUsed()) + "\n" +
//                        "Toplam geçirilen vakit:" + zamanal2(zamanliste.get(i).getTotalTimeInForeground()) + "\n";
//            }
//            catch (PackageManager.NameNotFoundException e)
//            {
//                continue;
//            }
//
//        }

        for(int i = 0; i < zamanliste.size();i++)
        {
            UsageStats usageStats = zamanliste.get(i);
            String paket = usageStats.getPackageName();
            if(Arrays.asList(istediklerim).contains(paket))
            {
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(paket,0);
                    String uygulamalar = (String) packageManager.getApplicationLabel(applicationInfo);
                    kullan = kullan + "Uygulama ismi: " + uygulamalar + "\nŞu tarihten beri: " +
                            zamanal(usageStats.getLastTimeUsed()) + "\nŞu kadar saat: " + zamanal2(usageStats.getTotalTimeInForeground());
                } catch (PackageManager.NameNotFoundException e) {
                    continue;
                }
            }

        }


        //uyglist.setText(kullan);
    }

    private String zamanal(Long lastTimeUsed) {
        Date date = new Date(lastTimeUsed);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM,yyyy hh:mm a", Locale.ENGLISH);
        return  simpleDateFormat.format(date);
    }

    private String zamanal2(Long lastTimeUsed) {
        Date date = new Date(lastTimeUsed);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
        return  simpleDateFormat.format(date);
    }







}