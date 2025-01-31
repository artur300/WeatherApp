@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_data.api_weather_dataBase

import android.util.Log
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRemoteDataSource @Inject constructor(
    private val weatherService: WeatherService
) : NetworkResponseHandler() { // ✅ הרחבת המחלקה כדי להשתמש ב-handleApiCall

    suspend fun getWeatherByLocation(city: String, country: String): Result<WeatherRoomEntity> {
        val query = "$city,$country"
        return handleApiCall {
            weatherService.getWeatherByLocation(
                apiKey = "f3b82ad32cb74471b8e71237252501",
                location = query
            )
        }.mapCatching { data ->
            WeatherRoomEntity(
                name = data.location.name,
                tempC = data.current.tempC,
                feelsLikeC = data.current.feelsLikeC,
                windKph = data.current.windKph,
                windDir = data.current.windDir ?: "N/A",
                humidity = data.current.humidity,
                conditionText = data.current.condition.text,
                conditionIcon = "https:" + data.current.condition.icon,
                country = data.location.country
            )
        }.onFailure {
            Log.e("WeatherRemoteDataSource", " Failed to fetch weather: ${it.localizedMessage}", it)
        }
    }
}


/**
 * סיכום המחלקה:

 * WeatherRemoteDataSource
 * - מחלקה האחראית על שליפת נתוני מזג האוויר מה-API.
 * - משתמשת ב-WeatherService כדי לבצע קריאות API.
 * - מרחיבה את המחלקה NetworkResponseHandler כדי להשתמש בפונקציה handleApiCall לניהול קריאות רשת.

 * פונקציות:
 * getWeatherByLocation
 * - מקבלת עיר ומדינה ומבצעת קריאה ל-API כדי לקבל את נתוני מזג האוויר.
 * - משתמשת ב-handleApiCall כדי לנהל את הקריאה ולוודא שהתשובה תקינה.
 * - ממירה את הנתונים שהתקבלו לאובייקט מסוג WeatherRoomEntity שמתאים לאחסון מקומי במסד נתונים.
 * - במקרה של כישלון, רושמת הודעת שגיאה ללוג.

 * מטרת המחלקה היא לאפשר גישה פשוטה ובטוחה לנתוני מזג האוויר מרחוק תוך שימוש בטכניקות של ניהול שגיאות וניקוי קוד.
 */
