package com.example.weatherapp

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.util.concurrent.Executors

class MainActivity : Activity() {
    private lateinit var cityInput: EditText
    private lateinit var result: TextView
    private lateinit var progress: ProgressBar
    private lateinit var weatherButton: Button
    private val executor = Executors.newSingleThreadExecutor()

    // Replace this with your OpenWeather API key from https://openweathermap.org/api
    private val apiKey = "YOUR_API_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityInput = findViewById(R.id.etCity)
        result = findViewById(R.id.txtResult)
        progress = findViewById(R.id.progressBar)
        weatherButton = findViewById(R.id.btnWeather)

        weatherButton.setOnClickListener {
            val cityName = cityInput.text.toString().trim()
            if (cityName.isEmpty()) {
                cityInput.error = "Enter a city name"
                return@setOnClickListener
            }
            if (apiKey == "YOUR_API_KEY" || apiKey.isBlank()) {
                Toast.makeText(this, "Add your OpenWeather API key in MainActivity.kt first.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            progress.visibility = View.VISIBLE
            weatherButton.isEnabled = false
            result.text = "Loading weather..."
            executor.execute {
                val weatherResult = WeatherTask.fetch(cityName, apiKey)
                runOnUiThread {
                    progress.visibility = View.GONE
                    weatherButton.isEnabled = true
                    result.text = weatherResult
                }
            }
        }
    }

    override fun onDestroy() {
        executor.shutdownNow()
        super.onDestroy()
    }
}
