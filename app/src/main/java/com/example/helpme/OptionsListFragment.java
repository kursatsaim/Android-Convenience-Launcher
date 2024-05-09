package com.example.helpme;

import static com.example.helpme.ChooseNewAct1Layout.KEY_STRING_REAL_NEWACT1_APP_LIST;
import static com.example.helpme.ChooseNewAct1Layout.SHARED_PREF_ACT1;
import static com.example.helpme.ChooseNewAct2Layout.KEY_STRING_REAL_NEWACT2_APP_LIST;
import static com.example.helpme.ChooseNewAct2Layout.SHARED_PREF_ACT2;
import static com.example.helpme.ChooseNewAct3Layout.KEY_STRING_REAL_NEWACT3_APP_LIST;
import static com.example.helpme.ChooseNewAct3Layout.SHARED_PREF_ACT3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OptionsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OptionsListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button buttonUp, buttonDown, buttonRight,removeProf1Button,removeProf2Button,removeProf3Button,mainUp,mainDown,mainRight;
    int Prof1Vis, Prof2Vis, Prof3Vis;
    MainActivity mainActivity;
    public Activity activity;
    private ArrayList<String> act1uyg;
    ArrayList<String> chosenapps;
    NewAct1List newAct1List;
    String json;
    NewAct2List newAct2List;
    NewAct3List newAct3List;
    private Button goBackgroundEditor;


    public OptionsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OptionsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OptionsListFragment newInstance(String param1, String param2) {
        OptionsListFragment fragment = new OptionsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            activity = getActivity();
            act1uyg = new ArrayList<String>();

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_options_list, container, false);
        buttonUp = v.findViewById(R.id.CreateProfile1);
        buttonRight = v.findViewById(R.id.CreateProfile2);
        buttonDown = v.findViewById(R.id.CreateProfile3);
        goBackgroundEditor = v.findViewById(R.id.goBackgroundPage);
        removeProf1Button = v.findViewById(R.id.RemoveProf1);
        removeProf2Button = v.findViewById(R.id.RemoveProf2);
        removeProf3Button = v.findViewById(R.id.RemoveProf3);
        newAct1List = new NewAct1List(getActivity());
        newAct2List = new NewAct2List(getActivity());
        newAct3List = new NewAct3List(getActivity());



        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).SetProfile1Visibility();
            }
        });

        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).SetProfile2Visibility();
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).SetProfile3Visibility();
            }
        });

        removeProf1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadSharedPrefsAct1();
                WipeandSaveSharedPrefsAct1();
            }
        });
        removeProf2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadSharedPrefsAct2();
                WipeandSaveSharedPrefsAct2();
            }
        });removeProf3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadSharedPrefsAct3();
                WipeandSaveSharedPrefsAct3();
            }
        });
        goBackgroundEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetBackgroundsAct.class);
                startActivity(intent);
            }
        });

        return v;

    }

    public void LoadSharedPrefsAct1()
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_ACT1, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_STRING_REAL_NEWACT1_APP_LIST,null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        act1uyg = gson.fromJson(json,type);
        if(act1uyg == null)
        {
            act1uyg = new ArrayList<String>();
        }
    }

    public void LoadSharedPrefsAct2()
    {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_ACT2,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_STRING_REAL_NEWACT2_APP_LIST,"");
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        chosenapps = gson.fromJson(json,type);
        if(chosenapps == null)
        {
            chosenapps = new ArrayList<String>();
        }

    }
    public void LoadSharedPrefsAct3()
    {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_ACT3,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_STRING_REAL_NEWACT3_APP_LIST,"");
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        chosenapps = gson.fromJson(json,type);
        if(chosenapps == null)
        {
            chosenapps = new ArrayList<String>();
        }

    }

    public void WipeandSaveSharedPrefsAct1()
    {
        chosenapps = new ArrayList<String>();
        chosenapps = newAct1List.GetNewAct1Applist();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_ACT1,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();


            chosenapps.clear();
            Gson gson = new Gson();
            json = gson.toJson(chosenapps);
            editor.putString(KEY_STRING_REAL_NEWACT1_APP_LIST, json);
            editor.apply();
            ((MainActivity) getActivity()).SetProfile1Visibility();

    }

    public void WipeandSaveSharedPrefsAct2()
    {
        chosenapps = new ArrayList<String>();
        chosenapps = newAct2List.GetNewAct2Applist();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_ACT2,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();


        chosenapps.clear();
        Gson gson = new Gson();
        json = gson.toJson(chosenapps);
        editor.putString(KEY_STRING_REAL_NEWACT2_APP_LIST, json);
        editor.apply();
        ((MainActivity) getActivity()).SetProfile2Visibility();

    }

    public void WipeandSaveSharedPrefsAct3()
    {
        chosenapps = new ArrayList<String>();
        chosenapps = newAct3List.GetNewAct3Applist();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_ACT3,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();


        chosenapps.clear();
        Gson gson = new Gson();
        json = gson.toJson(chosenapps);
        editor.putString(KEY_STRING_REAL_NEWACT3_APP_LIST, json);
        editor.apply();
        ((MainActivity) getActivity()).SetProfile3Visibility();

    }


}