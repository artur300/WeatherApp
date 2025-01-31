@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.helper_classes

class BaseUrlProvider  {
    companion object {
        const val BASE_URL = "https://api.weatherapi.com/v1/"
    }
}

/**
 *  סיכום הפונקציה:

 BaseUrlProvider
 *    - מחלקה שמספקת את כתובת הבסיס של השרת ממנו מתקבלים נתוני מזג האוויר.
 *    - כתובת זו משמשת את רטרופיט לשליחת בקשות ל-API.

  BASE_URL
 *    - משתנה קבוע שמכיל את כתובת ה-API של שירות מזג האוויר.

 * 💡 קובץ זה נועד לרכז את כתובת ה-API במקום אחד כדי לאפשר תחזוקה נוחה ושינויים בעת הצורך.
 */
