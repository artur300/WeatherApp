package com.example.weatherApp.weather_user_interface

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherApp.weather_data.repositories.WeatherRepository
import com.example.weatherApp.weather_data.weather_models.LocationData
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    val weatherData: LiveData<WeatherRoomEntity?> = repository.weatherData
    private val _searchResults = MutableLiveData<List<LocationData>>() // ✅ מחזיר נתונים ל-Fragment
    val searchResults: LiveData<List<LocationData>> get() = _searchResults

    fun getWeatherByLocation(city: String, country: String) {
        viewModelScope.launch {
            repository.fetchWeather(city, country)
        }
    }

    // ✅ עדכון החיפוש - שימוש ב-LiveData
    fun searchLocations(query: String) {
        viewModelScope.launch {
            val results = repository.searchLocations(query)
            _searchResults.postValue(results ?: emptyList())
        }
    }
}





