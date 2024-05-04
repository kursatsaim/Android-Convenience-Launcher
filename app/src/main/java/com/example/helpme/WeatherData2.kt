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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
//import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL
import java.util.Observer

class WeatherData {
    var weather_url1 = ""
    var api_id1 = "KEY"
    var desc = "aaa" // Initial value (can be removed if not needed)
    var icon = ""
    var temp: Int =  4353435
    private val _dataObservers = ArrayList<() -> Unit>() // List of lambdas for updates

    constructor(desc: String, icon: String) {
        this.desc = desc
        this.icon = icon
    }

    constructor() {

    }

    constructor(desc: String) {
        this.desc = desc
    }

    fun observeData(observer: () -> Unit) { // Lambda with no argument
        _dataObservers.add(observer)
    }


    @SuppressLint("MissingPermission")
    fun obtainLocation(fusedLocationProviderClient: FusedLocationProviderClient, weatherData: WeatherData) {
        Log.e("lat", "function")
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                var lat = location?.latitude
                var lon = location?.longitude
                var apiUrl = "https://api.weatherapi.com/v1/current.json?key=578847f6544d4d9a968122116241803&q="+ lat + "," + lon + "&aqi=yes&lang=tr"
                CoroutineScope(Dispatchers.Main).launch {
                    weatherData.getTemp(apiUrl) // Call getTemp on the passed instance
                }
            }
    }

    suspend fun getTemp(apiUrl: String) {
        val response = withContext(Dispatchers.IO) {
            URL(apiUrl).readText()
        }

        val json = JSONObject(response)
        val loca = json.getJSONObject("location").getString("name")
        val weatherDescription = json.getJSONObject("current").getJSONObject("condition").getString("text")
        val weatherIcon = json.getJSONObject("current").getJSONObject("condition").getString("icon")
        val weatherTemp = json.getJSONObject("current").getInt("temp_c")
        val WeatherInfo = loca + "\n" + weatherDescription + "\n" + weatherTemp + "° Derece"
        desc = WeatherInfo
        icon = "https:" + weatherIcon
        temp = weatherTemp

        
        _dataObservers.forEach { it() }
    }
}
