# WeatherApp (Kotlin + HttpURLConnection)

A simple Android weather app that requests current weather from OpenWeather and displays the city, temperature, humidity, description, and wind speed. The initial city field is set to Johannesburg. The `design/` folder contains the requested landing-page mockup image only.

## Open in Android Studio
1. Extract `WeatherAppProject.zip`.
2. In Android Studio, choose **Open** and select the extracted `WeatherAppProject` folder (the folder containing `settings.gradle.kts`).
3. Allow Gradle to sync. Use JDK 17 if Android Studio asks.
4. Get an API key from https://openweathermap.org/api.
5. Open `app/src/main/java/com/example/weatherapp/MainActivity.kt` and replace `YOUR_API_KEY` with your key.
6. Run the app on an Android device or emulator with internet access.

## Project files
- `app/src/main/AndroidManifest.xml` — internet permission and launcher activity.
- `app/src/main/res/layout/activity_main.xml` — app interface.
- `app/src/main/java/com/example/weatherapp/MainActivity.kt` — user interaction and background execution.
- `app/src/main/java/com/example/weatherapp/WeatherTask.kt` — HTTP request and JSON parsing.
- `design/johannesburg_weather_landing_page.png` — single landing-page design image.

Note: The sample weather values in the design image are illustrative. The running app displays live data returned by OpenWeather. A valid API key is required.
