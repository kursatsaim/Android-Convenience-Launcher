package com.example.helpme;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterChooseAppsForNewAct1 extends RecyclerView.Adapter<AdapterChooseAppsForNewAct1.Holderz> {

    ArrayList<ApplicationInfo> applist;
    ArrayList<String> Stringapplist;
    Context context;
    List<PicsAndAppNames> picsAndAppNames;
    NewAct1List newAct1List;
    public ConstraintLayout kutu;

// 1-12 2-13 3-14
    public AdapterChooseAppsForNewAct1(Context context, List<PicsAndAppNames> picsAndAppNames, NewAct1List newAct1List) {
        this.context = context;
        this.picsAndAppNames = picsAndAppNames;
        this.newAct1List = newAct1List;

        PackageManager packageManager = context.getPackageManager();
        applist = new ArrayList<ApplicationInfo>();
        Stringapplist = new ArrayList<String>();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> allApps = packageManager.queryIntentActivities(intent, 0);
        for(ResolveInfo ri:allApps)
        {
            applist.add(ri.activityInfo.applicationInfo);
            Stringapplist.add(ri.activityInfo.packageName);
        }

        burdadasil("com.google.android.googlequicksearchbox");

    }

    public AdapterChooseAppsForNewAct1() {
    }

    @NonNull
    @Override
    public Holderz onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.uygkutu,parent,false);
        Holderz holderz = new Holderz(view);

        return holderz;
    }

    @Override
    public void onBindViewHolder(@NonNull Holderz holder, int position) {

        holder.uygismi.setText(picsAndAppNames.get(position).yazi);
        holder.uygresim.setImageDrawable(picsAndAppNames.get(position).resim);

    }

    @Override
    public int getItemCount() {
        return applist.size();
    }

    public class Holderz extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public TextView uygismi;
        public ImageView uygresim;

        public Holderz(@NonNull View itemView) {
            super(itemView);

            kutu     = itemView.findViewById(R.id.uygkutulayout);
            uygismi  = itemView.findViewById(R.id.uygkutuisim);
            uygresim = itemView.findViewById(R.id.uygkutusimge);
            kutu . setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            ApplicationInfo packageName = applist.get(position);
            String StringpackageName = Stringapplist.get(position);
                ArrayList<String> secilenuyglar = new ArrayList<>(newAct1List.GetNewAct1Applist());
                if (secilenuyglar.contains(StringpackageName)) {
                    newAct1List.RemoveStringNewAct1AppList(StringpackageName);
                    view.setBackgroundColor(Color.RED);
                } else {
                    newAct1List.AddStringNewAct1AppList(StringpackageName);
                    view.setBackgroundColor(Color.GREEN);
                }
        }
    }

    public void burdadasil (String burdakisilincek)
    {
        for(String s : Stringapplist)
        {
            if(s.equals(burdakisilincek))
            {
                int x = Stringapplist.indexOf(burdakisilincek);
                applist.remove(x);
                Stringapplist.remove(x);
                break;
            }
        }
    }
}
