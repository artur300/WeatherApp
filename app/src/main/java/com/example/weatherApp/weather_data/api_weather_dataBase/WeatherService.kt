package com.example.weatherApp.weather_data.api_weather_dataBase

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    // חיפוש מזג אוויר לפי עיר ומדינה
    @GET("current.json")
    suspend fun getWeatherByLocation(
        @Query("key") apiKey: String,
        @Query("q") location: String
    ): Response<WeatherDataResponse>

    // פונקציה כללית לחיפוש נוסף
    @GET("search.json")
    suspend fun searchLocation(
        @Query("key") apiKey: String,
        @Query("q") query: String // חיפוש טקסט חופשי
    ): Response<List<LocationSearchResponse>>
}
