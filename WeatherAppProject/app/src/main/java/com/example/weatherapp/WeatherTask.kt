package com.example.weatherapp

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object WeatherTask {
    fun fetch(city: String, apiKey: String): String {
        var connection: HttpURLConnection? = null
        return try {
            val encodedCity = URLEncoder.encode(city, "UTF-8")
            val url = URL("https://api.openweathermap.org/data/2.5/weather?q=$encodedCity&appid=$apiKey&units=metric")
            connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 10000
                readTimeout = 10000
            }

            val status = connection.responseCode
            val stream = if (status in 200..299) connection.inputStream else connection.errorStream
            val response = BufferedReader(InputStreamReader(stream)).use { it.readText() }
            val json = JSONObject(response)

            if (status !in 200..299) {
                val message = json.optString("message", "Unable to retrieve weather.")
                return when (status) {
                    401 -> "API key not accepted. Check your OpenWeather API key and try again."
                    404 -> "City not found. Check the spelling and try again."
                    else -> "Weather request failed ($status): $message"
                }
            }

            val cityName = json.optString("name", city)
            val main = json.getJSONObject("main")
            val weather = json.getJSONArray("weather").getJSONObject(0)
            val wind = json.optJSONObject("wind")
            val temperature = main.getDouble("temp")
            val humidity = main.getInt("humidity")
            val description = weather.getString("description").replaceFirstChar { it.uppercase() }
            val windSpeed = wind?.optDouble("speed", 0.0) ?: 0.0

            "City: $cityName\n\nTemperature: ${"%.1f".format(temperature)} °C\n\nHumidity: $humidity %\n\nDescription: $description\n\nWind Speed: ${"%.1f".format(windSpeed)} m/s"
        } catch (e: Exception) {
            "Could not load weather. Check your internet connection and API key, then try again.\n\nDetails: ${e.localizedMessage ?: "Unknown error"}"
        } finally {
            connection?.disconnect()
        }
    }
}
