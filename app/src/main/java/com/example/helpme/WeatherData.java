package com.example.helpme;

/*import android.annotation.SuppressLint;
import android.location.Location;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONObject;

import java.net.URL;

import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
//import kotlinx.coroutines.launch;

public class WeatherDatar {
    String weather_url1 = "";
    String api_id1 = "5c3bfa42d9f14a848463d7bbefa9b8bf";
    String desc = "";
    String icon = "";
    private TextView textView;
    private Button btVar1;
    private FusedLocationProviderClient fusedLocationClient;
    private ImageView imageView;

    public WeatherDatar(String desc, String icon) {
        this.desc = desc;
        this.icon = icon;
    }

    public WeatherDatar() {
        obtainLocation();
    }

    @SuppressLint("MissingPermission")
    public void obtainLocation() {
        System.out.println("lat function");
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    Double lat = location.getLatitude();
                    Double lon = location.getLongitude();
                    String apiUrl = "https://api.weatherapi.com/v1/current.json?key=578847f6544d4d9a968122116241803&q=" + lat + "," + lon + "&aqi=yes&lang=tr";
                    CoroutineScope scope = new CoroutineScope() {
                        @Override
                        public CoroutineContext getCoroutineContext() {
                            return Dispatchers.getMain();
                        }
                    };
                    scope.launch(() -> getTemp(apiUrl));
                });
    }

    public void getTemp(String apiUrl) throws Exception {
        String response = new URL(apiUrl).openStream().readAllBytes().toString();
        JSONObject json = new JSONObject(response);
        String loca = json.getJSONObject("location").getString("name");
        String weatherDescription = json.getJSONObject("current").getJSONObject("condition").getString("text");
        String weatherIcon = json.getJSONObject("current").getJSONObject("condition").getString("icon");
        String WeatherInfo = loca + "\n" + weatherDescription;
        desc = WeatherInfo;
        icon = weatherIcon;
    }
}
*/
