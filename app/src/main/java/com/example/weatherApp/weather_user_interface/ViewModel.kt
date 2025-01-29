package com.example.weatherApp.weather_user_interface

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherApp.weather_data.repositories.WeatherRepository
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    val weatherData: LiveData<WeatherRoomEntity?> = repository.weatherData

    fun getWeatherByLocation(city: String, country: String) {
        Log.d("WeatherViewModel", "📌 Fetching weather data for: $city, $country")

        viewModelScope.launch {
            repository.fetchWeather(city, country)
        }
    }
}



