package com.example.weatherApp.weather_data.local_weather_dataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity

@Dao
interface WeatherDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: WeatherRoomEntity)

    @Query("SELECT * FROM weather_data WHERE locationName = :locationName")
    fun getWeatherDataByLocation(locationName: String): LiveData<WeatherRoomEntity>

    @Query("DELETE FROM weather_data WHERE locationName = :locationName")
    suspend fun deleteWeatherData(locationName: String)

    @Query("SELECT * FROM weather_data")
    fun getAllWeatherData(): LiveData<List<WeatherRoomEntity>>
}