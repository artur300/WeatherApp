@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

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
    private val _searchResults = MutableLiveData<List<LocationData>>() //  מחזיר נתונים ל-Fragment
    val searchResults: LiveData<List<LocationData>> get() = _searchResults

    fun getWeatherByLocation(city: String, country: String) {
        viewModelScope.launch {
            repository.fetchWeather(city, country)
        }
    }

    //  עדכון החיפוש - שימוש ב-LiveData
    fun searchLocations(query: String) {
        viewModelScope.launch {
            val results = repository.searchLocations(query)
            _searchResults.postValue(results ?: emptyList())
        }
    }
}

/**
 * סיכום המחלקה:

 * WeatherViewModel
 * - מחלקת ViewModel שאחראית על ניהול הנתונים והלוגיקה של מסך חיפוש מזג האוויר.
 * - מקבלת נתונים ממחלקת ה-Repository ומספקת אותם ל-Fragment באמצעות LiveData.

 * משתנים:

 * weatherData
 * - LiveData שמכיל את הנתונים של מזג האוויר שנשמרו במסד הנתונים המקומי.

 * _searchResults
 * - MutableLiveData שמכיל רשימת ערים תואמות שחזרו מהחיפוש.

 * searchResults
 * - LiveData לקריאה בלבד שמכיל את תוצאות החיפוש עבור ה-Fragment.

 * פונקציות:

 * getWeatherByLocation
 * - מקבלת עיר ומדינה ומפעילה את הפונקציה ב-Repository כדי להביא נתוני מזג אוויר.
 * - פועלת ב-ViewModelScope כדי לבצע את הבקשה באופן אסינכרוני.

 * searchLocations
 * - מבצעת חיפוש של ערים ב-API לפי שאילתת המשתמש.
 * - מעדכנת את LiveData עם תוצאות החיפוש שחזרו.

 * מטרת המחלקה היא להוות שכבת ביניים בין ממשק המשתמש לבין מאגר הנתונים, ולנהל את שליפת הנתונים והצגתם במסך בצורה יעילה.
 */




