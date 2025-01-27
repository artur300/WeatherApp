package com.example.weatherApp.weather_data.api_weather_dataBase

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
                apiData?.let { data ->
                    val roomEntity = WeatherRoomEntity(
                        locationName = data.location.name,
                        tempC = data.current.tempC,
                        feelsLikeC = data.current.feelslikeC,
                        windKph = data.current.windKph,
                        windDir = data.current.windDir,
                        humidity = data.current.humidity,
                        conditionText = data.current.condition.text
                    )
                    DataStatus.success(roomEntity)
                } ?: DataStatus.error("API returned null data")
            } else {
                DataStatus.error("API error: ${response.message()}")
            }
        } catch (e: Exception) {
            DataStatus.error("Exception occurred: ${e.localizedMessage}")
        }
    }
}


