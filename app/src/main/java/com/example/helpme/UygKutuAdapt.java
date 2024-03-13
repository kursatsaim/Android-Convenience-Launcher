package com.example.helpme;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UygKutuAdapt extends RecyclerView.Adapter<UygKutuAdapt.Holderz>{

    ArrayList<ApplicationInfo> applist;
    ArrayList<String> Stringapplist;
    Context context;
    List<PicsAndAppNames> picsAndAppNames;
    GirlAppsList girlAppsList;


    public UygKutuAdapt(Context context, List<PicsAndAppNames> picsAndAppNames, GirlAppsList girlAppsList) {
        this.context = context;
        this.picsAndAppNames = picsAndAppNames;
        this.girlAppsList = girlAppsList;


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
        public ConstraintLayout kutu;
        public TextView uygismi,uygisimler;
        public ImageView uygresim;

        public Holderz(@NonNull View itemView) {
            super(itemView);

            kutu     = itemView.findViewById(R.id.uygkutulayout);
            uygismi  = itemView.findViewById(R.id.uygkutuisim);
            uygresim = itemView.findViewById(R.id.uygkutusimge);
            uygresim . setOnClickListener(this);
            //uygisimler = itemView.findViewById(R.id.uygisimliste);

        }

        @Override
        public void onClick(View view) {

                int position = getAdapterPosition();

                ApplicationInfo packageName = applist.get(position);
                ArrayList<String> secilenuyglar = new ArrayList<>(girlAppsList.GetGirlApplist());
                String StringpackageName = Stringapplist.get(position);


                if(secilenuyglar.contains(StringpackageName)) {
                    girlAppsList.RemoveStringRealGirlAppList(StringpackageName);
                    //girlAppsList.KızUygÇıkar(packageName);
                    //girlAppsList.RemoveStringRealGirlAppList(StringpackageName);

                }
                else

                {

                    //girlAppsList.AddGirlApps(packageName);
                    girlAppsList.AddStringRealGirlAppList(StringpackageName);
                    //nbr

                }

                /*ArrayList<String> Stringsecilenuyglarr = new ArrayList<>(girlAppsList.getStringRealGirlAppList());
                String isimler = String.join("\n", Stringsecilenuyglarr);
                Toast.makeText(context, isimler, Toast.LENGTH_SHORT).show();*/



        }
    }
}
