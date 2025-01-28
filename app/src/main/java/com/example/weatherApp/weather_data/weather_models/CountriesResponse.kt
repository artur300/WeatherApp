package com.example.weatherApp.weather_data.weather_models

data class CountriesResponse(
    val data: List<CountryData>
)

data class CountryData(
    val country: String,
    val cities: List<String>
)
