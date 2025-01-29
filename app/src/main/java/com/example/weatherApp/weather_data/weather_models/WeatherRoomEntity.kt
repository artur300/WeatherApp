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

