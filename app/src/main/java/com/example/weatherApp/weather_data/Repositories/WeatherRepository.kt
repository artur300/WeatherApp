@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherApp.weather_data.api_weather_dataBase.WeatherRemoteDataSource
import com.example.weatherApp.weather_data.api_weather_dataBase.WeatherService
import com.example.weatherApp.weather_data.local_weather_dataBase.WeatherDataDao
import com.example.weatherApp.weather_data.weather_models.LocationData
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val weatherService: WeatherService,
    private val localDataSource: WeatherDataDao
) {
    private val _weatherData = MutableLiveData<WeatherRoomEntity?>()
    val weatherData: LiveData<WeatherRoomEntity?> get() = _weatherData

    /**
     * פונקציה חדשה לחיפוש ערים - מחזירה תוצאה ישירות ולא דרך `onResult`
     */
    suspend fun searchLocations(query: String): List<LocationData>? {
        return try {
            val response = weatherService.searchCity("f3b82ad32cb74471b8e71237252501", query)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("WeatherRepository", "API Error: ${response.message()} - ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Exception occurred: ${e.localizedMessage}", e)
            null
        }
    }

    
    fun fetchWeather(city: String, country: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val remoteData = remoteDataSource.getWeatherByLocation(city, country).getOrNull()
            if (remoteData != null) {
                localDataSource.insertWeatherData(remoteData)
                _weatherData.postValue(remoteData)
            } else {
                Log.e("WeatherRepository", "Failed to fetch data from API")
            }
        }
    }
}


/**
 * סיכום המחלקה:

 * WeatherRepository
 * - אחראי לניהול נתוני מזג האוויר על ידי שילוב בין מקור הנתונים המקומי (ROOM) לבין ה-API.
 * - מספק ממשק לאחזור, חיפוש ועדכון הנתונים עבור ה-ViewModel.

 * משתנים:

 * _weatherData
 * - משתנה פרטי שמחזיק את נתוני מזג האוויר מהמקור המקומי.
 * - MutableLiveData שמאפשר עדכון בזמן אמת.

 * weatherData
 * - מספק גישה לקריאה בלבד לנתוני מזג האוויר שנשמרו.

 * פונקציות:

 * searchLocations
 * - שולחת בקשה ל-API לחיפוש ערים תואמות לפי שאילתת המשתמש.
 * - מחזירה רשימה של ערים שנמצאו או רשימה ריקה במקרה של כישלון.

 * fetchWeather
 * - מביאה נתוני מזג אוויר מה-API לעיר ולמדינה מסוימות.
 * - אם הנתונים התקבלו בהצלחה, הם נשמרים במסד הנתונים המקומי ומעודכנים ב-LiveData.
 * - אם הבקשה נכשלת, הודעת שגיאה תופיע בלוג.

 * מחלקה זו מאפשרת סנכרון בין הנתונים המקומיים לנתונים מרוחקים ומוודאת שהאפליקציה תמיד תוכל להציג מידע, גם ללא חיבור לרשת.
 */
