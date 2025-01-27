package com.example.weatherApp.weather_data.weather_models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherRoomEntity(
    @PrimaryKey val locationName: String, // שם העיר
    val tempC: Double, // טמפרטורה
    val feelsLikeC: Double, // טמפרטורה מורגשת
    val windKph: Double, // מהירות רוח
    val windDir: String?, // כיוון רוח
    val humidity: Int, // לחות
    val conditionText: String // תיאור מזג האוויר
)
