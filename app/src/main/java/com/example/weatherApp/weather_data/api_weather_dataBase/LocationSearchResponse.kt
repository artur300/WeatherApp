package com.example.weatherApp.weather_data.api_weather_dataBase

import com.example.weatherApp.weather_data.weather_models.WeatherDataEntity


data class LocationSearchResponse(
    val id: Int,
    val name: String,
    val region: String,
    val country: String
) {
    fun toEntity(): WeatherDataEntity {
        return WeatherDataEntity(
            locationName = name,
            region = region,
            country = country,
            lat = 0.0, // ערך ברירת מחדל אם אין מידע
            lon = 0.0, // ערך ברירת מחדל אם אין מידע
            localtime = "", // ערך ברירת מחדל אם אין מידע
            tempC = 0.0, // ערך ברירת מחדל
            feelsLikeC = 0.0, // ערך ברירת מחדל
            windKph = 0.0, // ערך ברירת מחדל
            windDir = "", // ערך ברירת מחדל
            humidity = 0, // ערך ברירת מחדל
            conditionText = "", // ערך ברירת מחדל
            conditionIcon = "" // ערך ברירת מחדל
        )
    }
}

