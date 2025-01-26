package com.example.weatherApp.weather_user_interface

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherApp.helper_classes.DataStatus
import com.example.weatherApp.weather_data.repositories.WeatherRepository
import com.example.weatherApp.weather_data.weather_models.WeatherDataEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    // LiveData לקבל נתוני מזג האוויר
    fun getWeatherByLocation(location: String): LiveData<DataStatus<WeatherDataEntity>> {
        return repository.getWeatherByLocation(location)
    }
}
