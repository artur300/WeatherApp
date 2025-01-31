@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_data.weather_models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherRoomEntity(
    @PrimaryKey
    val name: String,  // 🔄 שינוי מ-locationName ל-name כדי להתאים ל-API
    val country: String,  // לשימוש ב-Query
    val tempC: Double,
    val feelsLikeC: Double,
    val windKph: Double,
    val windDir: String,
    val humidity: Int,
    val conditionText: String,
    val conditionIcon: String  // 🖼️ שדה חדש לאייקון מזג האוויר

)

/**
 * סיכום המחלקה:

 * WeatherRoomEntity
 * - מחלקת נתונים המייצגת את מבנה הנתונים של מזג האוויר במסד הנתונים המקומי (Room).
 * - הטבלה במסד הנתונים המקומי נקראת "weather_data".

 * שדות המחלקה:

 * name
 * - שם העיר, משמש כמפתח ראשי (PrimaryKey) בטבלה.

 * country
 * - שם המדינה, נדרש לביצוע שאילתות על פי מיקום.

 * tempC
 * - טמפרטורה במעלות צלזיוס.

 * feelsLikeC
 * - הטמפרטורה המורגשת בפועל.

 * windKph
 * - מהירות הרוח בקילומטרים לשעה.

 * windDir
 * - כיוון הרוח (כגון N, S, W, E).

 * humidity
 * - אחוזי לחות באוויר.

 * conditionText
 * - תיאור מילולי של מצב מזג האוויר (כגון "מעונן חלקית").

 * conditionIcon
 * - כתובת לאייקון גרפי המתאר את מצב מזג האוויר.

 * מחלקה זו משמשת לאחסון נתונים במסד הנתונים המקומי, כך שהאפליקציה יכולה לגשת למידע גם ללא חיבור לאינטרנט.
 */
