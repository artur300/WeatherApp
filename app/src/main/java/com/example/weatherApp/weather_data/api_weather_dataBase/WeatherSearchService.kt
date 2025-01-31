@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_data.api_weather_dataBase

import com.example.weatherApp.weather_data.weather_models.LocationData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherSearchService {

    @GET("search.json")
    suspend fun searchLocations(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): Response<List<LocationData>>
}


/**
 * סיכום המחלקה:

 * WeatherSearchService
 * - ממשק שמגדיר קריאה לרשת עבור חיפוש ערים לפי שם.
 * - משתמש ב- Retrofit כדי לבצע קריאות HTTP לשרת.

 * פונקציות:
 * searchLocations
 * - מבצעת קריאה ל-API עם מפתח ה-API ומחרוזת חיפוש כדי לקבל רשימה של ערים תואמות.
 * - משתמשת בבקשת GET לנתיב "search.json".
 * - מחזירה תשובה מסוג Response<List<LocationData>> המכילה רשימה של נתוני הערים שנמצאו.

 * מטרת הממשק היא לספק דרך פשוטה לגשת לנתוני חיפוש ערים משירות מזג האוויר.
 */
