package com.example.helpme;

import static com.example.helpme.MomChooseAppsLayout.KEY_STRING_REAL_GIRL_APP_LIST;
import static com.example.helpme.MomChooseAppsLayout.SHARED_PREF;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RAdapter extends RecyclerView.Adapter<RAdapter.Holderz> {

    ArrayList<String> applist;
    Context context;
    List<PicsAndAppNames> picsAndAppNames;
    GirlAppsList girlAppsList;
    int a = 0,CurrentPosition,ViewAmount;

    public RAdapter(Context context, List<PicsAndAppNames> picsAndAppNames) {
        this.context = context;
        this.picsAndAppNames = picsAndAppNames;
        girlAppsList = new GirlAppsList(context);

        PackageManager pm = context.getPackageManager();
        LoadSharedPrefs();
        //applist = new ArrayList<String>(girlAppsList.getStringRealGirlAppList());


//        Intent i = new Intent(Intent.ACTION_MAIN, null);
//        i.addCategory(Intent.CATEGORY_LAUNCHER);
//
//        List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
//        for(ResolveInfo ri:allApps)
//        {
//            applist.add(ri.activityInfo.packageName);
//        }

        
    }


    @NonNull
    @Override
    public RAdapter.Holderz onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.row, parent, false);
            Holderz holderz = new Holderz(view);
            return holderz;
            //CTRL+ ALT + SHIFT + F7


    }

    @Override
    public void onBindViewHolder(@NonNull RAdapter.Holderz holder, int position)
    {
        position = position + a;
        if( position < picsAndAppNames.size() && position % 3 == 0) {
            holder.textView.setText(picsAndAppNames.get(position).yazi);
            holder.img.setImageDrawable(picsAndAppNames.get(position).resim);
            position++;

        }
        if( position < picsAndAppNames.size() && position % 3 == 1) {
            holder.textView2.setText(picsAndAppNames.get(position).yazi);
            holder.img2.setImageDrawable(picsAndAppNames.get(position).resim);
            position++;

        }
        if( position < picsAndAppNames.size() && position % 3 == 2) {
            holder.textView3.setText(picsAndAppNames.get(position).yazi);
            holder.img3.setImageDrawable(picsAndAppNames.get(position).resim);

        }
        else
        {

        }
        a = a + 2;
        CurrentPosition = position;
        /*String x = picsAndAppNames.get(position).yazi;
        holder.textView.setText(picsAndAppNames.get(position).yazi);
        holder.img.setImageDrawable(picsAndAppNames.get(position).resim);
        holder.textView2.setText(picsAndAppNames.get(position).yazi);
        holder.img2.setImageDrawable(picsAndAppNames.get(position).resim);
        holder.textView3.setText(picsAndAppNames.get(position).yazi);
        holder.img3.setImageDrawable(picsAndAppNames.get(position).resim);*/

    }

    @Override
    public int getItemCount() {

        if(applist.size() % 3 != 0)
            ViewAmount = applist.size() / 3 + 1;
        else
            ViewAmount =applist.size()/3;
        return ViewAmount;
    }
    public class Holderz extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ConstraintLayout row;
        public ImageView img,img2,img3;
        public TextView textView,textView2,textView3;
        public Holderz(@NonNull View itemView) {
            super(itemView);

            row =      itemView.findViewById(R.id.a_row);
            img =      itemView.findViewById(R.id.img);
            img2=      itemView.findViewById(R.id.img2);
            img3=      itemView.findViewById(R.id.img3);
            textView = itemView.findViewById(R.id.popoutText);
            textView2 = itemView.findViewById(R.id.popoutText2);
            textView3 = itemView.findViewById(R.id.popoutText3);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            Context context1 = view.getContext();
            Intent basla = context1.getPackageManager().getLaunchIntentForPackage(applist.get(CurrentPosition-1));
            context1.startActivity(basla);

        }
    }

    public void burdadasil (String burdakisilincek)
    {
        for(String s : applist)
        {
            if(s.equals(burdakisilincek))
            {
                applist.remove(s);
                break;
            }
        }
    }
    public void LoadSharedPrefs()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_STRING_REAL_GIRL_APP_LIST,null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        applist = gson.fromJson(json,type);

        if(applist == null)
        {
            applist = new ArrayList<String>();
        }
    }
}
