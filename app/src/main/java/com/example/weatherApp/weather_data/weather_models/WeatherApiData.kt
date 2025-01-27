package com.example.weatherApp.weather_data.weather_models

// WeatherApiData.kt

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
    val localtime: String
)

data class Current(
    val tempC: Double,
    val feelslikeC: Double,
    val windKph: Double,
    val windDir: String,
    val humidity: Int,
    val condition: Condition
)

data class Condition(
    val text: String,
    val icon: String
)

