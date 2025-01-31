package com.example.weatherApp.weather_data.api_weather_dataBase

import com.example.weatherApp.weather_data.weather_models.LocationData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherSearchService {

    @GET("search.json")
    suspend fun searchLocations(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): Response<List<LocationData>>
}


