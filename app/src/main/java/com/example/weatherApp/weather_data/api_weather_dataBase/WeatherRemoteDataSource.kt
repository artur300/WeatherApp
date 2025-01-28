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

    suspend fun getWeatherByLocation(city: String, country: String): DataStatus<WeatherRoomEntity> {
        Log.d("WeatherRemoteDataSource", "🔹 getWeatherByLocation called with city: $city, country: $country")

        return try {
            val query = "$city,$country"
            Log.d("WeatherRemoteDataSource", "✅ Query constructed: $query")

            val response = weatherService.getWeatherByLocation(
                apiKey = "f3b82ad32cb74471b8e71237252501",
                location = query
            )

            Log.d("WeatherRemoteDataSource", "🌍 API Request URL: https://api.weatherapi.com/v1/current.json?key=API_KEY&q=$query")
            Log.d("WeatherRemoteDataSource", "📩 Response received: ${response.code()} - ${response.message()}")

            if (response.isSuccessful) {
                val apiData = response.body()
                Log.d("WeatherRemoteDataSource", "📄 API Response Body: $apiData")

                apiData?.let { data ->
                    Log.d("WeatherRemoteDataSource", "✅ Parsing API response into Room entity")

                    val roomEntity = WeatherRoomEntity(
                        name = data.location.name,  // ✅ שינוי ל-"name" בהתאם לנתונים מה-API
                        tempC = data.current.tempC,
                        feelsLikeC = data.current.feelsLikeC,
                        windKph = data.current.windKph,
                        windDir = data.current.windDir ?: "N/A",
                        humidity = data.current.humidity,
                        conditionText = data.current.condition.text,
                        country = data.location.country
                    )

                    Log.d("WeatherRemoteDataSource", "✅ Room Entity created: $roomEntity")
                    DataStatus.success(roomEntity)
                } ?: run {
                    Log.e("WeatherRemoteDataSource", "❌ API returned null data")
                    DataStatus.error("API returned null data")
                }
            } else {
                Log.e("WeatherRemoteDataSource", "❌ API Error: ${response.message()} - ${response.code()}")
                DataStatus.error("API error: ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("WeatherRemoteDataSource", "❌ Exception occurred: ${e.localizedMessage}", e)
            DataStatus.error("Exception occurred: ${e.localizedMessage}")
        }
    }
}

