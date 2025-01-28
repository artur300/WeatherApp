package com.example.weatherApp.weather_data.local_weather_dataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity

@Dao
interface WeatherDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: WeatherRoomEntity)

    @Query("SELECT * FROM weather_data WHERE name = :city AND country = :country")
    fun getWeatherDataByLocation(city: String, country: String): LiveData<WeatherRoomEntity>

    @Query("DELETE FROM weather_data WHERE name = :city AND country = :country")
    suspend fun deleteWeatherData(city: String, country: String)

    @Query("SELECT * FROM weather_data")
    fun getAllWeatherData(): LiveData<List<WeatherRoomEntity>>
}
