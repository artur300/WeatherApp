package com.example.weatherApp.weather_data.api_weather_dataBase

import com.example.weatherApp.weather_data.weather_models.CountriesResponse
import retrofit2.Response
import retrofit2.http.GET

interface CountriesApiService {
    @GET("countries")
    suspend fun getCountries(): Response<CountriesResponse> // ✅ שימוש נכון ב-Response<T>
}

