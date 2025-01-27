package com.example.weatherApp.weather_data.api_weather_dataBase

import android.util.Log
import com.example.weatherApp.helper_classes.DataStatus
import com.example.weatherApp.weather_data.weather_models.WeatherApiData
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRemoteDataSource @Inject constructor(
    private val weatherService: WeatherService
) {

    suspend fun getWeatherByLocation(location: String): DataStatus<WeatherRoomEntity> {
        return try {
            // קבלת נתונים מה-API
            val response = weatherService.getWeatherByLocation(
                apiKey = "f3b82ad32cb74471b8e71237252501",
                location = location
            )

            if (response.isSuccessful) {
                val apiData = response.body() // מתקבל WeatherApiData
                Log.d("WeatherRemoteDataSource", "API Response: $apiData") // בדיקת כל הנתונים

                apiData?.let { data ->
                    // בדיקות Log על כל שדה חשוב
                    Log.d("WeatherRemoteDataSource", "Location Name: ${data.location.name}")
                    Log.d("WeatherRemoteDataSource", "Temperature (C): ${data.current.tempC}")
                    Log.d("WeatherRemoteDataSource", "Feels Like (C): ${data.current.feelslikeC}")
                    Log.d("WeatherRemoteDataSource", "Wind Speed (Kph): ${data.current.windKph}")
                    Log.d("WeatherRemoteDataSource", "Wind Direction: ${data.current.windDir}")
                    Log.d("WeatherRemoteDataSource", "Humidity: ${data.current.humidity}")
                    Log.d("WeatherRemoteDataSource", "Condition Text: ${data.current.condition.text}")

                    // יצירת אובייקט RoomEntity
                    val roomEntity = WeatherRoomEntity(
                        locationName = data.location.name,
                        tempC = data.current.tempC,
                        feelsLikeC = data.current.feelslikeC,
                        windKph = data.current.windKph,
                        windDir = data.current.windDir ?: "N/A",
                        humidity = data.current.humidity,
                        conditionText = data.current.condition.text ?: "No condition available"
                    )
                    DataStatus.success(roomEntity)
                } ?: DataStatus.error("API returned null data")
            } else {
                Log.e("WeatherRemoteDataSource", "API Error: ${response.message()}")
                DataStatus.error("API error: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("WeatherRemoteDataSource", "Exception: ${e.localizedMessage}")
            DataStatus.error("Exception occurred: ${e.localizedMessage}")
        }
    }
}


