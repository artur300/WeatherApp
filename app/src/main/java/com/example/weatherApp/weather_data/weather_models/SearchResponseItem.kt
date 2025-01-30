package com.example.weatherApp.weather_data.weather_models

import com.google.gson.annotations.SerializedName

data class SearchResponseItem(
    @SerializedName("name") val name: String,       // שם העיר
    @SerializedName("country") val country: String // שם המדינה
)

