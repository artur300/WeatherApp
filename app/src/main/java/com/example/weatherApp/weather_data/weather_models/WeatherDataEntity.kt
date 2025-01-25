package com.example.weatherApp.weather_data.weather_models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherDataEntity(
    @PrimaryKey val locationName: String, // שם העיר (מפתח ראשי)
    val region: String, // אזור
    val country: String, // מדינה
    val lat: Double, // קו רוחב
    val lon: Double, // קו אורך
    val localtime: String, // זמן מקומי
    val tempC: Double, // טמפרטורה במעלות צלזיוס
    val feelsLikeC: Double, // טמפרטורה מורגשת
    val windKph: Double, // מהירות רוח בקמ"ש
    val windDir: String, // כיוון הרוח
    val humidity: Int, // לחות
    val conditionText: String, // תיאור מזג האוויר
    val conditionIcon: String // URL לאייקון מזג האוויר
)

