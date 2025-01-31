@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_data.weather_models

import com.google.gson.annotations.SerializedName

data class WeatherApiData(
    val location: Location,
    val current: Current
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    @SerializedName("localtime") val localtime: String
)

data class Current(
    @SerializedName("temp_c") val tempC: Double, // טמפרטורה
    @SerializedName("feelslike_c") val feelsLikeC: Double, // טמפרטורה מורגשת
    @SerializedName("wind_kph") val windKph: Double, // מהירות רוח
    @SerializedName("wind_dir") val windDir: String?, // כיוון רוח
    val humidity: Int, // לחות
    val condition: Condition // תיאור מזג האוויר
)

data class Condition(
    val text: String,
    val icon: String
)


/**
 * סיכום המחלקה:

 * WeatherApiData
 * - מחלקת נתונים המייצגת את המידע שמתקבל מה-API בנוגע למזג האוויר.
 * - כוללת מידע על מיקום נוכחי ומצב מזג האוויר הנוכחי.

 * מחלקות פנימיות:

 * Location
 * - מחזיקה מידע על המיקום כולל שם העיר, האזור, המדינה, קואורדינטות (רוחב ואורך), ושעה מקומית.

 * Current
 * - מחזיקה מידע על מזג האוויר הנוכחי כולל:
 *   - טמפרטורה (tempC).
 *   - טמפרטורה מורגשת (feelsLikeC).
 *   - מהירות רוח (windKph).
 *   - כיוון רוח (windDir).
 *   - אחוזי לחות (humidity).
 *   - אובייקט Condition המכיל תיאור מילולי של מזג האוויר ואייקון מתאים.

 * Condition
 * - מחזיקה תיאור מילולי של מזג האוויר (text) ואייקון מתאים להצגת המצב הגרפי (icon).

 * מחלקות אלו נבנו בהתאם למבנה הנתונים שה-API מחזיר ומאפשרות לנו לשמור ולהשתמש במידע זה באפליקציה.
 */
