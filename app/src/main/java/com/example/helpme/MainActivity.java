package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import com.example.helpme.WeatherData;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.Observer;
import android.content.Intent;
import android.os.Bundle;
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
    private Button annebuton,btVar1;
    private TextView textView;
    private ImageView imageView;
    private FusedLocationProviderClient fusedLocationProviderClient;
    String WeatherIcon;
    public String WeatherDescription;
    GirlAppsList girlAppsList;
    WeatherData weatherData;


    public MainActivity() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        textView = (TextView) findViewById(R.id.textView);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        weatherData = new WeatherData();

        weatherData.obtainLocation(fusedLocationProviderClient, weatherData);

        // Observer implementation to update UI
        weatherData.observeData(() -> {
            textView.setText(weatherData.getDesc());
            return Unit.INSTANCE;
        });
        /*weatherData2 = new WeatherData("desc","icon");
        WeatherIcon = weatherData2.getIcon();
        WeatherDescription = weatherData2.getDesc();*/

        btVar1 = (Button) findViewById(R.id.btVar1);
        btVar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        kızbuton = (Button) findViewById(R.id.goChildGirl);

        kızbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            OpenGirlAct();

            }
        });

        erkekbuton = (Button) findViewById(R.id.goChildBoy);

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