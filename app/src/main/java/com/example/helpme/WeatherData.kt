package com.example.helpme

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
//import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL

 class WeatherDatas {
    var weather_url1 = ""
    var api_id1 = "5c3bfa42d9f14a848463d7bbefa92b8bf"
    var desc = ""
    var icon = ""
    constructor()
    {

    }


    @SuppressLint("MissingPermission")
     fun obtainLocation(fusedLocationProviderClient: FusedLocationProviderClient) {
        Log.e("lat", "function")
        // get the last location
        //zzbi@12099
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                var lat = location?.latitude
                var lon = location?.longitude
                var apiUrl = "https://api.weatherapi.com/v1/current.json?key=578847f6544d4d9a968122116241803&q="+ lat + "," + lon + "&aqi=yes&lang=tr"
                CoroutineScope(Dispatchers.Main).launch {
                    getTemp(apiUrl)
                }
            }
    }

    suspend fun getTemp( apiUrl: String) {
        val response = withContext(Dispatchers.IO) {
            URL(apiUrl).readText()
        }

        val json = JSONObject(response)
        val loca = json.getJSONObject("location").getString("name")
        val weatherDescription = json.getJSONObject("current").getJSONObject("condition").getString("text")
        val weatherIcon = json.getJSONObject("current").getJSONObject("condition").getString("icon")
        val WeatherInfo = loca + "\n" + weatherDescription
        //desc = WeatherInfo
        RecieveDesc(WeatherInfo)
        icon = "https:" + weatherIcon

    }
     fun RecieveDesc(nbr: String) {
         desc = nbr
     }

     fun gettDesc(): String {
         return this.desc
     }


 }