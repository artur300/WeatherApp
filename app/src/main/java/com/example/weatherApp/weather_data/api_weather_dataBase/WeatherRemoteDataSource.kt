package com.example.weatherApp.weather_data.api_weather_dataBase

import android.util.Log
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRemoteDataSource @Inject constructor(
    private val weatherService: WeatherService
) {

    suspend fun getWeatherByLocation(city: String, country: String): WeatherRoomEntity? {
        return try {
            val query = "$city,$country"
            val response = weatherService.getWeatherByLocation(
                apiKey = "f3b82ad32cb74471b8e71237252501",
                location = query
            )
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    WeatherRoomEntity(
                        name = data.location.name,
                        tempC = data.current.tempC,
                        feelsLikeC = data.current.feelsLikeC,
                        windKph = data.current.windKph,
                        windDir = data.current.windDir ?: "N/A",
                        humidity = data.current.humidity,
                        conditionText = data.current.condition.text,
                        country = data.location.country
                    )
                }
            } else {
                Log.e("WeatherRemoteDataSource", "❌ API Error: ${response.message()} - ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("WeatherRemoteDataSource", "❌ Exception occurred: ${e.localizedMessage}", e)
            null
        }
    }

}


