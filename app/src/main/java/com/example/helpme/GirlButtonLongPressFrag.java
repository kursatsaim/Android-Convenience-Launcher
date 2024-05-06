package com.example.helpme;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GirlButtonLongPressFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GirlButtonLongPressFrag extends Fragment{


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button button;
    private String mParam1;
    private String mParam2;
    AdapterChooseAppsGirl adapterChooseAppsGirl;

    public GirlButtonLongPressFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GirlButtonLongPressFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static GirlButtonLongPressFrag newInstance(String param1, String param2) {
        GirlButtonLongPressFrag fragment = new GirlButtonLongPressFrag();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl_button_long_press, container, false);
        button = view.findViewById(R.id.button);
        adapterChooseAppsGirl = new AdapterChooseAppsGirl();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MomChooseAppsLayout.class);
                startActivity(intent);
                if (getActivity() != null) {
                    getFragmentManager().popBackStackImmediate();
                }
            }
        });
        return view;
    }

    private void goGirlAppsEditPage() {


    }
}