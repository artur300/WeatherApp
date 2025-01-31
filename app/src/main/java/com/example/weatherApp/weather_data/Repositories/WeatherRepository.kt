package com.example.weatherApp.weather_data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherApp.weather_data.api_weather_dataBase.WeatherRemoteDataSource
import com.example.weatherApp.weather_data.api_weather_dataBase.WeatherService
import com.example.weatherApp.weather_data.local_weather_dataBase.WeatherDataDao
import com.example.weatherApp.weather_data.weather_models.LocationData
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val weatherService: WeatherService,
    private val localDataSource: WeatherDataDao
) {
    private val _weatherData = MutableLiveData<WeatherRoomEntity?>()
    val weatherData: LiveData<WeatherRoomEntity?> get() = _weatherData

    /**
     * פונקציה חדשה לחיפוש ערים - מחזירה תוצאה ישירות ולא דרך `onResult`
     */
    suspend fun searchLocations(query: String): List<LocationData>? {
        return try {
            val response = weatherService.searchCity("f3b82ad32cb74471b8e71237252501", query)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("WeatherRepository", "❌ API Error: ${response.message()} - ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("WeatherRepository", "❌ Exception occurred: ${e.localizedMessage}", e)
            null
        }
    }

    
    fun fetchWeather(city: String, country: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val remoteData = remoteDataSource.getWeatherByLocation(city, country).getOrNull()
            if (remoteData != null) {
                localDataSource.insertWeatherData(remoteData)
                _weatherData.postValue(remoteData)
            } else {
                Log.e("WeatherRepository", "⚠️ Failed to fetch data from API")
            }
        }
    }
}

