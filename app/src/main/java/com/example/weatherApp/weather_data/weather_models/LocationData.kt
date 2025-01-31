@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_data.weather_models

import com.google.gson.annotations.SerializedName

data class LocationData(
    @SerializedName("name") val name: String,       // שם העיר
    @SerializedName("country") val country: String // שם המדינה
)

/**
 * סיכום המחלקה:

 * LocationData
 * - מחלקת נתונים שמייצגת מידע על מיקום (עיר ומדינה).
 * - משמשת לקבלת נתוני מיקום מה-API לאחר חיפוש ערים.

 * משתנים:

 * name
 * - מחזיק את שם העיר כפי שהתקבל מה-API.

 * country
 * - מחזיק את שם המדינה בה נמצאת העיר.

 * מחלקה זו מאפשרת לנו לשמור ולנהל את המידע שהתקבל על מיקומים שנמצאו על ידי המשתמש בחיפוש.
 */
