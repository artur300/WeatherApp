package com.example.weatherApp.helper_classes

import com.example.weatherApp.R

object WeatherBackgroundProvider {

    fun getBackgroundForCondition(conditionText: String): Int {
        return when (conditionText.lowercase()) {
            "sunny", "clear" -> R.drawable.sunny  // ☀️ יום שמשי או בהיר בלילה
            "partly cloudy", "cloudy", "overcast" -> R.drawable.cloudy  // ☁️ עננות חלקית או מלאה
            "mist", "fog", "freezing fog" -> R.drawable.fog  // 🌫️ ערפל או ערפל קפוא
            "light drizzle", "patchy light drizzle", "light rain", "patchy light rain" -> R.drawable.rain  // 🌧️ גשם קל
            "moderate rain", "heavy rain", "torrential rain shower" -> R.drawable.rain  // 🌧️ גשם כבד
            "light snow", "patchy light snow", "moderate snow", "patchy heavy snow", "heavy snow", "blizzard" -> R.drawable.snow  // ❄️ שלג
            "thundery outbreaks possible", "moderate or heavy rain with thunder", "moderate or heavy snow with thunder" -> R.drawable.thunder  // ⛈️ סופת רעמים
            else -> R.drawable.def  // 🔄 ברירת מחדל למקרים שלא תואמים
        }
    }
}

