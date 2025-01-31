package com.example.weatherApp.weather_data.api_weather_dataBase

import com.example.weatherApp.weather_data.weather_models.LocationData
import com.example.weatherApp.weather_data.weather_models.WeatherApiData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("search.json")
    suspend fun searchCity(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): Response<List<LocationData>>

    @GET("current.json")
    suspend fun getWeatherByLocation(
        @Query("key") apiKey: String,
        @Query("q") location: String
    ): Response<WeatherApiData>
}


