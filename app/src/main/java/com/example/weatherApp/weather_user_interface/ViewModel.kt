package com.example.weatherApp.weather_user_interface

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

    // LiveData לקבל נתוני מזג האוויר
    fun getWeatherByLocation(location: String): LiveData<DataStatus<WeatherRoomEntity>> {
        return repository.getWeatherByLocation(location)
    }
}
