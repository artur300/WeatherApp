package com.example.weatherApp.weather_data.repositories


import androidx.lifecycle.LiveData
import com.example.weatherApp.helper_classes.DataStatus
import com.example.weatherApp.weather_data.api_weather_dataBase.WeatherRemoteDataSource
import com.example.weatherApp.weather_data.local_weather_dataBase.WeatherDataDao
import com.example.weatherApp.helper_classes.fetchAndSyncData
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherDataDao
) {

    fun getWeatherByLocation(location: String): LiveData<DataStatus<WeatherRoomEntity>> {
        return fetchAndSyncData(
            getLocalData = { localDataSource.getWeatherDataByLocation(location) },
            fetchRemoteData = { remoteDataSource.getWeatherByLocation(location) },
            saveRemoteDataLocally = { localDataSource.insertWeatherData(it) }
        )
    }
}


