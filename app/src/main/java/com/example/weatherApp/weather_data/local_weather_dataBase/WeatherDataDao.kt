@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_data.local_weather_dataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity

@Dao
interface WeatherDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: WeatherRoomEntity)

    @Query("SELECT * FROM weather_data WHERE name = :city AND country = :country")
    fun getWeatherDataByLocation(city: String, country: String): LiveData<WeatherRoomEntity>

    @Query("DELETE FROM weather_data WHERE name = :city AND country = :country")
    suspend fun deleteWeatherData(city: String, country: String)

    @Query("SELECT * FROM weather_data")
    fun getAllWeatherData(): LiveData<List<WeatherRoomEntity>>
}

/**
 * סיכום המחלקה:

 * WeatherDataDao
 * - ממשק שמאפשר ביצוע פעולות CRUD (יצירה, קריאה, עדכון ומחיקה) על מסד הנתונים המקומי.
 * - מנהל את נתוני מזג האוויר השמורים באפליקציה.

 * פונקציות:

 * insertWeatherData
 * - מוסיפה או מעדכנת נתוני מזג אוויר במסד הנתונים.
 * - אם הנתון כבר קיים, הוא יוחלף (אסטרטגיית REPLACE).

 * getWeatherDataByLocation
 * - מחזירה את נתוני מזג האוויר לעיר ולמדינה מסוימות.
 * - מחזירה LiveData כדי לאפשר מעקב אחרי שינויים במסד הנתונים.

 * deleteWeatherData
 * - מוחקת נתוני מזג אוויר לפי שם עיר ומדינה.

 * getAllWeatherData
 * - מחזירה את כל נתוני מזג האוויר השמורים במסד הנתונים.
 * - מחזירה LiveData כדי לאפשר עדכונים אוטומטיים כאשר הנתונים משתנים.

 * הממשק מאפשר גישה יעילה ומהירה לנתוני מזג האוויר השמורים מקומית.
 */
