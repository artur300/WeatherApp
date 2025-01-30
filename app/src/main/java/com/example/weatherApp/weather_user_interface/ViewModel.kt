package com.example.weatherApp.weather_user_interface

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherApp.weather_data.repositories.WeatherRepository
import com.example.weatherApp.weather_data.weather_models.SearchResponseItem
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    val weatherData: LiveData<WeatherRoomEntity?> = repository.weatherData
    val isLoading = MutableLiveData<Boolean>() // ✅ משתנה חדש למעקב אחר מצב הטעינה
    private val _searchResults = MutableLiveData<List<SearchResponseItem>>() // ✅ תוצאות החיפוש
    val searchResults: LiveData<List<SearchResponseItem>> get() = _searchResults

    fun getWeatherByLocation(city: String, country: String) {
        isLoading.value = true // ✅ הצגת ProgressBar

        viewModelScope.launch {
            repository.fetchWeather(city, country)
            isLoading.value = false // ✅ הסתרת ProgressBar
        }
    }

    // ✅ פונקציה חדשה לחיפוש ערים
    fun searchLocations(query: String, onResult: (List<SearchResponseItem>?) -> Unit) {
        viewModelScope.launch {
            repository.searchLocations(query) { results ->
                _searchResults.postValue(results ?: emptyList()) // ✅ עדכון ה-LiveData
                onResult(results)
            }
        }
    }
}



