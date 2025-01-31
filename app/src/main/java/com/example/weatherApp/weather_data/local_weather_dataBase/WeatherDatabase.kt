@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_data.local_weather_dataBase


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity

@Database(entities = [WeatherRoomEntity::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDataDao(): WeatherDataDao

    companion object {
        @Volatile
        private var instance: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}


/**
 * סיכום המחלקה:

 * WeatherDatabase
 * - מחלקת מסד נתונים מקומית המבוססת על Room.
 * - מאפשרת שמירת נתוני מזג האוויר במכשיר לטובת שימוש לא מקוון.

 * רכיבים:

 * @Database
 * - מגדיר את מסד הנתונים, כולל הישויות שהוא תומך בהן.
 * - בגרסה הנוכחית, הוא מכיל את הישות WeatherRoomEntity.

 * פונקציות:

 * weatherDataDao
 * - מספק גישה לפונקציות הנתונים במסד הנתונים באמצעות מחלקת ה-DAO.

 * getDatabase
 * - פונקציה סטטית שמחזירה מופע של מסד הנתונים.
 * - משתמשת ב-Singleton כך שקיים מופע אחד בלבד במהלך חיי האפליקציה.
 * - משתמשת ב-fallbackToDestructiveMigration לטיפול בשדרוגי גרסאות של מסד הנתונים.

 * מטרת המחלקה היא לספק גישה קלה וניהול נכון של נתוני מזג האוויר השמורים מקומית.
 */
