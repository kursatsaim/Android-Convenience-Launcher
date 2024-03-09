package com.example.helpme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button kızbuton;
    private Button erkekbuton;
    private Button bababuton;
    private Button annebuton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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



}