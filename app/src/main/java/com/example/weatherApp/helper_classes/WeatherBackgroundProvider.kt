@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ
package com.example.weatherApp.helper_classes

import com.example.weatherApp.R

object WeatherBackgroundProvider {

    fun getBackgroundForCondition(conditionText: String): Int {
        return when (conditionText.lowercase()) {
            "sunny", "clear" -> R.drawable.sunny
            "partly cloudy", "cloudy", "overcast" -> R.drawable.cloudy
            "mist", "fog", "freezing fog" -> R.drawable.fog
            "light drizzle", "patchy light drizzle", "light rain", "patchy light rain" -> R.drawable.rain
            "moderate rain", "heavy rain", "torrential rain shower" -> R.drawable.rain
            "light snow", "patchy light snow", "moderate snow", "patchy heavy snow", "heavy snow", "blizzard" -> R.drawable.snow
            "thundery outbreaks possible", "moderate or heavy rain with thunder", "moderate or heavy snow with thunder" -> R.drawable.thunder
            else -> R.drawable.def
        }
    }
}

/**
 * סיכום המחלקה:

 * WeatherBackgroundProvider
 * - מחלקה שמספקת רקעים שונים בהתאמה למצב מזג האוויר שהתקבל מהשרת.

 * פונקציה:
 * getBackgroundForCondition
 * - מקבלת מחרוזת שמתארת את מצב מזג האוויר ומחזירה את המשאב הגרפי (drawable) המתאים.

 * פירוט הפעולות:
 * - המחרוזת שהתקבלה מומרת לאותיות קטנות כדי להימנע מבעיות רישיות.
 * - מבוצעת בדיקה לפי טווחים מוגדרים מראש, וכל מצב מזג אוויר מתאים לרקע מסוים.
 * - אם אין התאמה לרשימת המצבים, מוחזר הרקע ברירת מחדל (def).

 * מטרת המחלקה היא לספק חוויית משתמש משופרת על ידי הצגת רקע מתאים לכל סוג מזג אוויר.
 */
