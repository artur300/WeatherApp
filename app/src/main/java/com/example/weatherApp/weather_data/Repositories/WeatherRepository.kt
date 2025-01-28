package com.example.weatherApp.weather_data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherApp.helper_classes.DataStatus
import com.example.weatherApp.weather_data.api_weather_dataBase.WeatherRemoteDataSource
import com.example.weatherApp.weather_data.local_weather_dataBase.WeatherDataDao
import com.example.weatherApp.helper_classes.fetchAndSyncData
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

    fun getWeatherByLocation(city: String, country: String): LiveData<DataStatus<WeatherRoomEntity>> {
        Log.d("WeatherRepository", "🌍 Requesting weather data for: $city, $country")

        return fetchAndSyncData(
            getLocalData = {
                val liveData = localDataSource.getWeatherDataByLocation(city, country)

                val liveDataResult = MutableLiveData<WeatherRoomEntity>()
                CoroutineScope(Dispatchers.Main).launch {
                    liveData.observeForever { data ->
                        liveDataResult.postValue(data)
                    }
                }

                Log.d("WeatherRepository", "💾 Retrieved local weather data: $liveData")
                liveDataResult
            },
            fetchRemoteData = {
                Log.d("WeatherRepository", "📡 Fetching remote weather data for: $city, $country")
                val remoteData = remoteDataSource.getWeatherByLocation(city, country)
                Log.d("WeatherRepository", "📩 Remote weather data received: $remoteData")
                remoteData
            },
            saveRemoteDataLocally = { weatherData ->
                Log.d("WeatherRepository", "💾 Saving weather data to Room database: $weatherData")

                CoroutineScope(Dispatchers.IO).launch {
                    localDataSource.insertWeatherData(weatherData)

                    // ✅ בדיקה אם הנתונים באמת נשמרו ב-Room
                    val savedData = localDataSource.getWeatherDataByLocation(city, country)
                    CoroutineScope(Dispatchers.Main).launch {
                        savedData.observeForever { data ->
                            Log.d("WeatherRepository", "🔍 Data from Room after save: $data")
                        }
                    }
                }
            }
        )
    }


}



