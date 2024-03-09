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

    }

    @Override
    public void onBindViewHolder(@NonNull RAdapter.Holderz holder, int position)
    {
        holder.textView.setText(picsAndAppNames.get(position).yazi);
        holder.img.setImageDrawable(picsAndAppNames.get(position).resim);
    }

    @Override
    public int getItemCount() {
        return applist.size();
    }
    public class Holderz extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ConstraintLayout row;
        public ImageView img;
        public TextView textView;
        public Holderz(@NonNull View itemView) {
            super(itemView);

            row =      itemView.findViewById(R.id.a_row);
            img =      itemView.findViewById(R.id.img);
            textView = itemView.findViewById(R.id.popoutText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int pos = getAdapterPosition();
            Context context1 = view.getContext();
            Intent basla = context1.getPackageManager().getLaunchIntentForPackage(applist.get(pos));
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
