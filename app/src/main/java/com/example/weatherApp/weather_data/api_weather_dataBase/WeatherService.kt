@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_data.api_weather_dataBase

import com.example.weatherApp.weather_data.weather_models.LocationData
import com.example.weatherApp.weather_data.weather_models.WeatherApiData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("search.json")
    suspend fun searchCity(
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): Response<List<LocationData>>

    @GET("current.json")
    suspend fun getWeatherByLocation(
        @Query("key") apiKey: String,
        @Query("q") location: String
    ): Response<WeatherApiData>
}


/**
 * סיכום המחלקה:

 * WeatherService
 * - ממשק שאחראי לקריאות רשת מול ה-API של מזג האוויר.
 * - משתמש ב- Retrofit כדי לבצע בקשות GET ולהחזיר נתוני מזג אוויר או תוצאות חיפוש ערים.

 * פונקציות:

 * searchCity
 * - מבצעת קריאה ל-API כדי לחפש ערים על פי מחרוזת חיפוש.
 * - מקבלת מפתח API ומחרוזת חיפוש כפרמטרים.
 * - מחזירה רשימה של אובייקטים מסוג LocationData, שמכילים מידע על ערים תואמות.

 * getWeatherByLocation
 * - מבצעת קריאה ל-API כדי לקבל את מזג האוויר הנוכחי עבור עיר מסוימת.
 * - מקבלת מפתח API ושם העיר כפרמטרים.
 * - מחזירה מידע על מזג האוויר העדכני של העיר בצורה של אובייקט מסוג WeatherApiData.

 * מטרת הממשק היא לספק נקודת גישה אחידה לקריאות רשת הקשורות למזג האוויר ולחיפוש ערים.
 */
