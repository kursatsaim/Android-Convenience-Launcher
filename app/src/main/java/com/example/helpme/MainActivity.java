package com.example.helpme;

import static com.example.helpme.SetBackgroundsAct.KEY_SHARED_PREF_NEWBACK_DAD;
import static com.example.helpme.SetBackgroundsAct.KEY_SHARED_PREF_NEWBACK_MAIN;
import static com.example.helpme.SetBackgroundsAct.SHARED_PREF_NEWBACK_DAD;
import static com.example.helpme.SetBackgroundsAct.SHARED_PREF_NEWBACK_MAIN;

import androidx.appcompat.app.AppCompatActivity;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.provider.Settings;
import android.view.MotionEvent;
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

import kotlin.Unit;


public class MainActivity extends AppCompatActivity implements MyObserver2  {

    //commitlemek için rastgele ekledim bu yazıyı silebilirsin
    private Button kızbuton;
    private Button profil1buton;
    private Button profil2buton;
    private Button profil3buton;
    private Button erkekbuton;
    private Button bababuton;
    private Button annebuton;
    private Button faceRecogButton;
    private Button ayarlarbuton;
    private Button profil1yarat, profil2yarat, profil3yarat;
    private TextView textView;
    private ImageView imageView, darkBackground,backgroundpic;
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
    int Prof1Vis, Prof2Vis, Prof3Vis;
    OptionsListFragment optionsListFragment;
    MainActivity mainActivity;
    ConstraintLayout LayoutMain;
    private Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);
        faceRecogButton = findViewById(R.id.startFaceRecog);
        fragmentContainerViewGirl = findViewById(R.id.EditAppsButtonFrag);
        profil1yarat = findViewById(R.id.CreateProfile1);
        profil2yarat = findViewById(R.id.CreateProfile2);
        profil3yarat = findViewById(R.id.CreateProfile3);
        darkBackground = findViewById(R.id.imageView3);
        backgroundpic = findViewById(R.id.backgroundImageview);

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
                FragmentManager karen = getSupportFragmentManager();

                karen.beginTransaction()
                        .replace(R.id.EditAppsButtonFrag, Default_Frag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            OpenGirlAct();

            }
        });

        erkekbuton = (Button) findViewById(R.id.goChildBoy);

        erkekbuton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                OpenBoyFrag();
                fragmentContainerViewGirl.bringToFront();
                return true;
            }
        });

        erkekbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager karen = getSupportFragmentManager();

                karen.beginTransaction()
                        .replace(R.id.EditAppsButtonFrag, Default_Frag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

                OpenBoyAct();

            }
        });

        annebuton = (Button) findViewById(R.id.goMother);
        annebuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager karen = getSupportFragmentManager();

                karen.beginTransaction()
                        .replace(R.id.EditAppsButtonFrag, Default_Frag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

                OpenMomAct();

            }
        });

        bababuton = (Button) findViewById(R.id.goDad);

        bababuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager karen = getSupportFragmentManager();

                karen.beginTransaction()
                        .replace(R.id.EditAppsButtonFrag, Default_Frag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

                OpenDadAct();


            }
        });

        profil1buton = (Button) findViewById(R.id.YeniProfil1);
        Prof1Vis = profil1buton.getVisibility();

        profil1buton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager karen = getSupportFragmentManager();

                karen.beginTransaction()
                        .replace(R.id.EditAppsButtonFrag, Default_Frag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

                OpenProf1Act();
            }
        });

        profil1buton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                OpenProfile1Frag();
                return true;
            }
        });



        profil2buton = (Button) findViewById(R.id.YeniProfil2);

        profil2buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager karen = getSupportFragmentManager();

                karen.beginTransaction()
                        .replace(R.id.EditAppsButtonFrag, Default_Frag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

                OpenProf2Act();
            }
        });

        profil2buton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                OpenProfile2Frag();
                return true;
            }
        });

        profil3buton = (Button) findViewById(R.id.YeniProfil3);

        profil3buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager karen = getSupportFragmentManager();

                karen.beginTransaction()
                        .replace(R.id.EditAppsButtonFrag, Default_Frag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

                OpenProf3Act();
            }
        });

        profil3buton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                OpenProfile3Frag();
                return true;
            }
        });

        faceRecogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager karen = getSupportFragmentManager();

                karen.beginTransaction()
                        .replace(R.id.EditAppsButtonFrag, Default_Frag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

                OpenFaceRecog();
            }
        });

        profil1yarat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profil1buton.getVisibility() == View.INVISIBLE)
                    profil1buton.setVisibility(View.VISIBLE);
                else
                    profil1buton.setVisibility(View.INVISIBLE);
            }
        });

        profil2yarat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profil2buton.getVisibility() == View.INVISIBLE)
                    profil2buton.setVisibility(View.VISIBLE);
                else
                    profil2buton.setVisibility(View.INVISIBLE);
            }
        });

        profil3yarat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profil3buton.getVisibility() == View.INVISIBLE)
                    profil3buton.setVisibility(View.VISIBLE);
                else
                    profil3buton.setVisibility(View.INVISIBLE);
            }
        });

        textClock = findViewById(R.id.textClock2);
        textClock.getFormat24Hour();

        dateText = findViewById(R.id.editTextDate);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String currentDate = simpleDateFormat.format(calendar.getTime());
        dateText.setText(currentDate);

        ayarlarbuton = findViewById(R.id.quickie);

        ayarlarbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OpenOptionsFrag();
            }
        });



        //faceRecognition = new FaceRecognition();
        //faceRecognition.addObserver(this);

        LayoutMain = findViewById(R.id.mainlayout);

        LayoutMain.setOnTouchListener(new View.OnTouchListener() {
            int d = 1;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FragmentManager karen = getSupportFragmentManager();

                karen.beginTransaction()
                        .replace(R.id.EditAppsButtonFrag, Default_Frag.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
                return false;
            }
        });

    }

    @Override
    public void onUpdate(String name) {
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
                .setReorderingAllowed(false)
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
                .setReorderingAllowed(false)
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
                .setReorderingAllowed(false)
                .addToBackStack(null)
                .commit();
    }

    public void OpenProf2Act()
    {
        Intent intent = new Intent(this, NewAct2.class);
        startActivity(intent);
    }
    public void OpenProfile2Frag()
    {
        FragmentManager karen = getSupportFragmentManager();

        karen.beginTransaction()
                .replace(R.id.EditAppsButtonFrag, Act2ButtonLongPressFragment.class, null)
                .setReorderingAllowed(false)
                .addToBackStack(null)
                .commit();
    }

    public void OpenProf3Act()
    {
        Intent intent = new Intent(this, NewAct3.class);
        startActivity(intent);
    }
    public void OpenProfile3Frag()
    {
        FragmentManager karen = getSupportFragmentManager();

        karen.beginTransaction()
                .replace(R.id.EditAppsButtonFrag, Act3ButtonLongPressFragment.class, null)
                .setReorderingAllowed(false)
                .addToBackStack(null)
                .commit();
    }

    public void OpenOptionsFrag()
    {
        FragmentManager karen = getSupportFragmentManager();

        karen.beginTransaction()
                .replace(R.id.EditAppsButtonFrag, OptionsListFragment.class, null)
                .setReorderingAllowed(false)
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


    /*@Override
    public void update(MyObservable observable, String name) {
        if (observable instanceof FaceRecognition) {
            if (name.equals("baba")) {
                Toast.makeText(MainActivity.this, "Oldu beeee!", Toast.LENGTH_LONG).show();
            }
        }
    }*/

    public void SetProfile1Visibility()
    {
        if(profil1buton.getVisibility() == View.INVISIBLE)
            profil1buton.setVisibility(View.VISIBLE);
        else
            profil1buton.setVisibility(View.INVISIBLE);
    }
    public void SetProfile2Visibility()
    {
        if(profil2buton.getVisibility() == View.INVISIBLE)
            profil2buton.setVisibility(View.VISIBLE);
        else
            profil2buton.setVisibility(View.INVISIBLE);
    }
    public void SetProfile3Visibility()
    {
        if(profil3buton.getVisibility() == View.INVISIBLE)
            profil3buton.setVisibility(View.VISIBLE);
        else
            profil3buton.setVisibility(View.INVISIBLE);
    }
    private void LoadSharePrefs()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NEWBACK_MAIN, Context.MODE_PRIVATE);
        String imagePath = sharedPreferences.getString(KEY_SHARED_PREF_NEWBACK_MAIN, "");
        if (!imagePath.isEmpty()) {
            imageUri = Uri.parse(imagePath);
        }
    }

}