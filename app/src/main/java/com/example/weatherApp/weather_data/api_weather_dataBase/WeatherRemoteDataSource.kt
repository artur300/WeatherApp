package com.example.weatherApp.weather_data.api_weather_dataBase

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRemoteDataSource @Inject constructor(
    private val weatherService: WeatherService
) : NetworkResponseHandler() {

    // פונקציה לקבלת נתוני מזג אוויר לפי מיקום
    suspend fun getWeatherByLocation(location: String) = handleApiCall {
        weatherService.getWeatherByLocation(apiKey = "f3b82ad32cb74471b8e71237252501", location = location)
    }

    // פונקציה לחיפוש מיקום
    suspend fun searchLocation(query: String) = handleApiCall {
        weatherService.searchLocation(apiKey = "f3b82ad32cb74471b8e71237252501", query = query)
    }
}

