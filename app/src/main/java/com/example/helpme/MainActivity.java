package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;



import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import kotlin.Unit;


public class MainActivity extends AppCompatActivity implements MyObserver2  {

    private Button kızbuton;
    private Button yeniprofil1buton;
    private Button erkekbuton;
    private Button bababuton;
    private Button annebuton;
    private Button faceRecogButton;
    private TextView textView;
    private ImageView imageView;
    private TextClock textClock;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView dateText;
    String WeatherIcon;
    public String WeatherDescription,DadFaceName;
    GirlAppsList girlAppsList;
    WeatherData weatherData;
    FragmentContainerView fragmentContainerViewGirl;
    Context context;
    private FaceRecognition faceRecognition;
    Intent intentDad;
    public String x;
    private String CurrentUser = "";
    private List<MyObserver2> observerz = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);
        faceRecogButton = findViewById(R.id.startFaceRecog);
        fragmentContainerViewGirl = findViewById(R.id.EditAppsButtonFrag);

       /* MyObserver observer = new MyObserver() {
            @Override
            public void onUpdate(String newName) {
                // UserName2 değişkeni değiştiğinde bu fonksiyon tetiklenir
                CurrentUser = newName;
                // UI'ınızı güncelleyin
                TextView textView = findViewById(R.id.textView);
                textView.setText(CurrentUser);
            }
        };*/


        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && isNetworkAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            weatherData = new WeatherData();

            weatherData.obtainLocation(fusedLocationProviderClient, weatherData);

            weatherData.observeData(() -> {
                textView.setText(weatherData.getDesc());
                Picasso.get().load(weatherData.getIcon()).into(imageView);
                return Unit.INSTANCE;
            });
        }


        FragmentManager karen = getSupportFragmentManager();

        karen.beginTransaction()
                .replace(R.id.EditAppsButtonFrag, Default_Frag.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

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
                OpenBoyFrag();
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

        yeniprofil1buton = (Button) findViewById(R.id.YeniProfil1);

        yeniprofil1buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenProf1Act();
            }
        });

        yeniprofil1buton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                OpenProfile1Frag();
                return false;
            }
        });

        faceRecogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFaceRecog();
            }
        });

        textClock = findViewById(R.id.textClock2);
        textClock.getFormat24Hour();

        dateText = findViewById(R.id.editTextDate);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String currentDate = simpleDateFormat.format(calendar.getTime());
        dateText.setText(currentDate);

        //faceRecognition = new FaceRecognition();
        //faceRecognition.addObserver(this);
    }
    @Override
    public void onUpdate(String name) { // "MyObserver" yerine "MyObserver2" yazın
        x = name;
    }

    public void OpenGirlAct(){
        Intent intent = new Intent(this, ChildGirl.class);
        startActivity(intent);
}
    public void OpenGirlFrag(){
        //fragmentContainerViewGirl.setSystemUiVisibility(0);
        FragmentManager karen = getSupportFragmentManager();

        karen.beginTransaction()
                .replace(R.id.EditAppsButtonFrag, GirlButtonLongPressFrag.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

}
    public void OpenBoyAct(){
        Intent intent = new Intent(this, ChildBoy.class);
        startActivity(intent);
}

    public void OpenBoyFrag(){
        FragmentManager karen = getSupportFragmentManager();

        karen.beginTransaction()
                .replace(R.id.EditAppsButtonFrag, BoyButtonLongPressFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

    }
    public void OpenProf1Act()
    {
        Intent intent = new Intent(this, New_Added_Act1.class);
        startActivity(intent);
    }
    public void OpenProfile1Frag()
    {
        FragmentManager karen = getSupportFragmentManager();

        karen.beginTransaction()
                .replace(R.id.EditAppsButtonFrag, Act1ButtonLongPressFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    public void OpenMomAct(){
        Intent intent = new Intent(this, Mom.class);
        startActivity(intent);
}

    public void OpenDadAct(){
        Intent intent = new Intent(this,Dad.class);
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
                Toast.makeText(this,"In order to access weather forcast please restart the app.",Toast.LENGTH_LONG).show();
            }
        }
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void OpenFaceRecog()
    {
        Intent intent = new Intent(this, FaceRecognition.class);

        startActivity(intent);
    }

    public void OpenFamilyActivity(String name)
    {
        intentDad = new Intent(this,Dad.class);
        if (Objects.equals(name, "baba"))
        {
            startActivity(intentDad);
        }
    }


    /*@Override
    public void update(MyObservable observable, String name) {
        if (observable instanceof FaceRecognition) {
            if (name.equals("baba")) {
                Toast.makeText(MainActivity.this, "Oldu beeee!", Toast.LENGTH_LONG).show();
            }
        }
    }*/
}