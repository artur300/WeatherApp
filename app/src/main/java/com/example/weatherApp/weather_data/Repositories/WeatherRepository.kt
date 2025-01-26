package com.example.weatherApp.weather_data.repositories


import com.example.weatherApp.weather_data.api_weather_dataBase.WeatherRemoteDataSource
import com.example.weatherApp.weather_data.local_weather_dataBase.WeatherDataDao
import com.example.weatherApp.helper_classes.fetchAndSyncData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource, // מקור הנתונים המרוחק
    private val localDataSource: WeatherDataDao // מקור הנתונים המקומי
) {

    // פונקציה שמביאה נתוני מזג אוויר על בסיס מיקום
    fun getWeatherByLocation(location: String) = fetchAndSyncData(
        getLocalData = { localDataSource.getWeatherDataByLocation(location) },
        fetchRemoteData = { remoteDataSource.getWeatherByLocation(location) },
        saveRemoteDataLocally = { localDataSource.insertWeatherData(it.toEntity()) }
    )

    // פונקציה שמביאה רשימת מיקומים תואמים לחיפוש
    fun searchLocation(query: String) = fetchAndSyncData(
        getLocalData = { localDataSource.getAllWeatherData() }, // לדוגמה: מחזיר את כל הנתונים השמורים
        fetchRemoteData = { remoteDataSource.searchLocation(query) },
        saveRemoteDataLocally = { response ->
            response.forEach {
                localDataSource.insertWeatherData(it.toEntity())
            }
        }
    )
}

