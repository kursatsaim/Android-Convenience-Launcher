package com.example.helpme;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.StrictMode;
import android.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*public class WeatherDatar {
    private String weather_url1 = "";
    private String api_id1 = "5c3bfa42d9f14a848463d7bbefa9b8bf";
    private String desc = "kotlin";
    public String weatherDescription;
    public String WeatherInfo = "Dolmadı",loca,anan = "ben";
    private String icon = "";
    MainActivity mainActivity;

    public WeatherDatar(FusedLocationProviderClient fusedLocationProviderClient) {
        obtainLocation(fusedLocationProviderClient);
    }

    @SuppressLint("MissingPermission")
    public void obtainLocation(FusedLocationProviderClient fusedLocationProviderClient) {
        Log.e("lat", "function");
        // get the last location
        //zzbi@12099
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    Double lat = location.getLatitude();
                    Double lon = location.getLongitude();
                    String apiUrl = "https://api.weatherapi.com/v1/current.json?key=578847f6544d4d9a968122116241803&q=" + lat + "," + lon + "&aqi=yes&lang=tr";
                    getTemp(apiUrl);
                });
    }

    public void getTemp(String apiUrl) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                String response = stringBuilder.toString();

                JSONObject json = new JSONObject(response);
                loca = json.getJSONObject("location").getString("name");
                weatherDescription = json.getJSONObject("current").getJSONObject("condition").getString("text");
                String weatherIcon = json.getJSONObject("current").getJSONObject("condition").getString("icon");
                WeatherInfo = loca + "\n" + weatherDescription;
                mainActivity = new MainActivity();
                mainActivity.setWeatherDescription(WeatherInfo);

                icon = "https:" + weatherIcon;
            } finally {
                urlConnection.disconnect();
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void RecieveDesc(String nbr) {
        desc = nbr;
    }
    public String anna() {
        anan = "nbr";
        return anan;
    }

    public String gettDesc() {
        WeatherInfo = loca + "\n" + weatherDescription;
        return WeatherInfo;
    }
}*/
