package com.example.weatherApp.weather_data.local_weather_dataBase


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherApp.weather_data.weather_models.WeatherRoomEntity

@Database(entities = [WeatherRoomEntity::class], version = 4, exportSchema = false)
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
