package com.example.weatherApp.weather_data.weather_models

import com.google.gson.annotations.SerializedName

data class WeatherApiData(
    val location: Location,
    val current: Current
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    @SerializedName("localtime") val localtime: String
)

data class Current(
    @SerializedName("temp_c") val tempC: Double, // טמפרטורה
    @SerializedName("feelslike_c") val feelsLikeC: Double, // טמפרטורה מורגשת
    @SerializedName("wind_kph") val windKph: Double, // מהירות רוח
    @SerializedName("wind_dir") val windDir: String?, // כיוון רוח
    val humidity: Int, // לחות
    val condition: Condition // תיאור מזג האוויר
)

data class Condition(
    val text: String,
    val icon: String
)


