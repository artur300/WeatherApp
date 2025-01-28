package com.example.weatherApp.weather_user_interface

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherApp.helper_classes.DataStatus
import com.example.weatherApp.weather_data.repositories.WeatherRepository
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    // LiveData לקבלת נתוני מזג האוויר
    fun getWeatherByLocation(city: String, country: String): LiveData<DataStatus<WeatherRoomEntity>> {
        Log.d("WeatherViewModel", "📌 Fetching weather data for: $city, $country")
        val weatherData = repository.getWeatherByLocation(city, country)
        Log.d("WeatherViewModel", "✅ Data fetched from repository, LiveData ready to be observed")
        return weatherData
    }
}

