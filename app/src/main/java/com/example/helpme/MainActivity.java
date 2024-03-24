package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import kotlin.Unit;


public class MainActivity extends AppCompatActivity {

    private Button kızbuton;
    private Button erkekbuton;
    private Button bababuton;
    private Button annebuton;
    private TextView textView;
    private ImageView imageView;
    private FusedLocationProviderClient fusedLocationProviderClient;
    String WeatherIcon;
    public String WeatherDescription;
    GirlAppsList girlAppsList;
    WeatherData weatherData;
    FragmentContainerView fragmentContainerViewGirl;
    Context context;
    UygKutuAdapt uygKutuAdapt;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);
        fragmentContainerViewGirl = findViewById(R.id.GirlEditAppsButtonFrag);
        int girlCheck = 0;




        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        weatherData = new WeatherData();

        weatherData.obtainLocation(fusedLocationProviderClient, weatherData);

        // Observer implementation to update UI
        weatherData.observeData(() -> {
            textView.setText(weatherData.getDesc());
            Picasso.get().load(weatherData.getIcon()).into(imageView);
            return Unit.INSTANCE;
        });

        FragmentManager karen = getSupportFragmentManager();

        karen.beginTransaction()
                .replace(R.id.GirlEditAppsButtonFrag, Default_Frag.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
        /*weatherData2 = new WeatherData("desc","icon");
        WeatherIcon = weatherData2.getIcon();
        WeatherDescription = weatherData2.getDesc();*/



        kızbuton = (Button) findViewById(R.id.goChildGirl);

        kızbuton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                OpenGirlFrag();
                return true;
            }
        });

        kızbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            OpenGirlAct();

            }
        });

        erkekbuton = (Button) findViewById(R.id.goChildBoy);

        erkekbuton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                uygKutuAdapt.setGirlOrBoy(1);
                return true;
            }
        });

        erkekbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenBoyAct();

            }
        });

        annebuton = (Button) findViewById(R.id.goMother);
        annebuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMomAct();

            }
        });

        bababuton = (Button) findViewById(R.id.goDad);

        bababuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDadAct();

            }
        });
    }

    public void OpenGirlAct(){
        Intent intent = new Intent(this, ChildGirl.class);
        startActivity(intent);
}
    public void OpenGirlFrag(){
        //fragmentContainerViewGirl.setSystemUiVisibility(0);
        FragmentManager karen = getSupportFragmentManager();

        karen.beginTransaction()
                .replace(R.id.GirlEditAppsButtonFrag, GirlButtonLongPressFrag.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

}
    public void OpenBoyAct(){
        Intent intent = new Intent(this, ChildBoy.class);
        startActivity(intent);
}

    public void OpenMomAct(){
        Intent intent = new Intent(this, Mom.class);
        startActivity(intent);
}

    public void OpenDadAct(){
        Intent intent = new Intent(this, passwordActivity.class);
        startActivity(intent);
}
    public void onChromeButtonClick(View v) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
        startActivity(launchIntent);
    }

    public void AyarlarAc (View view){
        Intent intent =new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    public void QuickAç(View view)
    {
        Intent intent = new Intent(this, MomChooseAppsLayout.class);
        startActivity(intent);
    }

    public void getPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }



}