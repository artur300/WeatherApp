package com.example.weatherApp.weather_data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherApp.weather_data.api_weather_dataBase.WeatherRemoteDataSource
import com.example.weatherApp.weather_data.local_weather_dataBase.WeatherDataDao
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherDataDao
) {
    private val _weatherData = MutableLiveData<WeatherRoomEntity?>()
    val weatherData: LiveData<WeatherRoomEntity?> get() = _weatherData

    fun fetchWeather(city: String, country: String) {
        Log.d("WeatherRepository", "🌍 Fetching weather data for: $city, $country")

        CoroutineScope(Dispatchers.IO).launch {
            // 🚀 טוען נתונים מה-ROOM אם קיימים
            val cachedData = localDataSource.getWeatherDataByLocation(city, country)
            CoroutineScope(Dispatchers.Main).launch {
                cachedData.observeForever { data ->
                    if (data != null) {
                        Log.d("WeatherRepository", "💾 Loaded from ROOM: $data")
                        _weatherData.postValue(data)
                    }
                }
            }

            // 🚀 מושך נתונים מה-API
            val remoteData = remoteDataSource.getWeatherByLocation(city, country)
            if (remoteData != null) {
                Log.d("WeatherRepository", "📡 API Response: $remoteData")

                // שומר נתונים ב-Room
                localDataSource.insertWeatherData(remoteData)

                // מעדכן את ה-UI עם הנתונים החדשים
                CoroutineScope(Dispatchers.Main).launch {
                    _weatherData.postValue(remoteData)
                }
            } else {
                Log.e("WeatherRepository", "⚠️ Failed to fetch data from API")
            }
        }
    }
}


