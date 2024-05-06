package com.example.helpme;

import static com.example.helpme.MomChooseAppsLayout.KEY_STRING_REAL_GIRL_APP_LIST;
import static com.example.helpme.MomChooseAppsLayout.SHARED_PREF;
import static com.example.helpme.MomChooseAppsForBoy.SHARED_PREF_BOY;
import static com.example.helpme.MomChooseAppsForBoy.KEY_STRING_REAL_BOY_APP_LIST;
import static com.example.helpme.ChooseNewAct1Layout.KEY_STRING_REAL_NEWACT1_APP_LIST;
import static com.example.helpme.ChooseNewAct1Layout.SHARED_PREF_ACT1;
import static com.example.helpme.ChooseNewAct2Layout.KEY_STRING_REAL_NEWACT2_APP_LIST;
import static com.example.helpme.ChooseNewAct2Layout.SHARED_PREF_ACT2;
import static com.example.helpme.ChooseNewAct3Layout.KEY_STRING_REAL_NEWACT3_APP_LIST;
import static com.example.helpme.ChooseNewAct3Layout.SHARED_PREF_ACT3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
    BoyAppsList boyAppsList;
    NewAct1List newAct1List;
    NewAct2List newAct2List;
    NewAct3List newAct3List;
    ChildBoy childBoy = new ChildBoy();
    ChildGirl childGirl = new ChildGirl();
    New_Added_Act1 newAddedAct1 = new New_Added_Act1();
    NewAct2 newAct2 = new NewAct2();
    NewAct3 newAct3 = new NewAct3();
    int a = 0,CurrentPosition,ViewAmount;

    public RAdapter(Context context, List<PicsAndAppNames> picsAndAppNames) {
        this.context = context;
        this.picsAndAppNames = picsAndAppNames;
        boyAppsList = new BoyAppsList(context);
        girlAppsList = new GirlAppsList(context);
        newAct1List = new NewAct1List(context);
        newAct2List = new NewAct2List(context);
        newAct3List = new NewAct3List(context);

        PackageManager pm = context.getPackageManager();

        LoadSharedPrefss(context.getClass());
        
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
        else{}
        a = a + 2;
        CurrentPosition = position;

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
            img.setOnClickListener(this);
            img2.setOnClickListener(this);
            img3.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int AdapterPosition = getAdapterPosition();
            if(view == img)
            {
                int FirstApp = (AdapterPosition * 3);
                Context context1 = view.getContext();
                Intent basla = context1.getPackageManager().getLaunchIntentForPackage(applist.get(FirstApp));
                context1.startActivity(basla);
            }
            else if(view == img2)
            {
                int SecondApp = (AdapterPosition * 3) + 1;
                if(SecondApp < applist.size()) {
                    Context context1 = view.getContext();
                    Intent basla = context1.getPackageManager().getLaunchIntentForPackage(applist.get(SecondApp));
                    context1.startActivity(basla);
                }
            }
            else if(view == img3)
                {
                    int ThirdApp = (AdapterPosition * 3) + 2;
                    if(ThirdApp < applist.size()) {
                        Context context1 = view.getContext();
                        Intent basla = context1.getPackageManager().getLaunchIntentForPackage(applist.get(ThirdApp));
                        context1.startActivity(basla);
                    }
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
    private void LoadSharedPrefss(Class<? extends Context> aClass) {

        if(aClass == childBoy.getContextBoy().getClass())
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_BOY,Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(KEY_STRING_REAL_BOY_APP_LIST,null);
            Type type = new TypeToken<ArrayList<String>>() {}.getType();

            applist = gson.fromJson(json,type);

            if(applist == null)
            {
                applist = new ArrayList<String>();
            }
        }
        else if (aClass == childGirl.getContextGirl().getClass())
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
        else if (aClass == newAddedAct1.getContextAct1().getClass())
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_ACT1,Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(KEY_STRING_REAL_NEWACT1_APP_LIST,null);
            Type type = new TypeToken<ArrayList<String>>() {}.getType();

            applist = gson.fromJson(json,type);

            if(applist == null)
            {
                applist = new ArrayList<String>();
            }
        }
        else if (aClass == newAct2.getContextAct2().getClass())
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_ACT2,Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(KEY_STRING_REAL_NEWACT2_APP_LIST,null);
            Type type = new TypeToken<ArrayList<String>>() {}.getType();

            applist = gson.fromJson(json,type);

            if(applist == null)
            {
                applist = new ArrayList<String>();
            }
        }
        else if (aClass == newAct3.getContextAct3().getClass())
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_ACT3,Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(KEY_STRING_REAL_NEWACT3_APP_LIST,null);
            Type type = new TypeToken<ArrayList<String>>() {}.getType();

            applist = gson.fromJson(json,type);

            if(applist == null)
            {
                applist = new ArrayList<String>();
            }
        }

    }


}
