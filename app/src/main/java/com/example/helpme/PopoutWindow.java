package com.example.helpme;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PopoutWindow extends AppCompatActivity {

    private   TextView uyglist;

    public String kullan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popout_window);

        uyglist = findViewById(R.id.popoutTextpopout);

        getzaman();
        uyglist.setText("bruh");

        //popupyaz();
    }

    public void popupyaz()
    {
        uyglist.setText(kullan);
    }





    public void getzaman()
    {

        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar tarih = Calendar.getInstance();
        tarih.add(Calendar.DAY_OF_MONTH,-1);
        List<UsageStats> zamanliste = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                tarih.getTimeInMillis(), System.currentTimeMillis());
        String[] istediklerim = new String[] {"com.android.settings", "com.android.deskclock", "com.google.android.calculator"};
        kullan = "";
        PackageManager packageManager = getPackageManager();


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