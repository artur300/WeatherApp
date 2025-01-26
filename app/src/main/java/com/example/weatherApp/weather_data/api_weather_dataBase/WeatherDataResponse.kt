package com.example.weatherApp.weather_data.api_weather_dataBase

import com.example.weatherApp.weather_data.weather_models.WeatherDataEntity


data class WeatherDataResponse(
    val location: Location,
    val current: CurrentWeather
) {
    fun toEntity(): WeatherDataEntity {
        return WeatherDataEntity(
            locationName = location.name,
            region = location.region,
            country = location.country,
            lat = location.lat,
            lon = location.lon,
            localtime = location.localtime,
            tempC = current.temp_c,
            feelsLikeC = current.feelslike_c,
            windKph = current.wind_kph,
            windDir = current.wind_dir,
            humidity = current.humidity,
            conditionText = current.condition.text,
            conditionIcon = current.condition.icon
        )
    }
}

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    val localtime: String
)

data class CurrentWeather(
    val last_updated: String,
    val temp_c: Double,
    val feelslike_c: Double,
    val condition: WeatherCondition,
    val wind_kph: Double,
    val wind_dir: String,
    val humidity: Int,
    val cloud: Int
)

data class WeatherCondition(
    val text: String,
    val icon: String
)
