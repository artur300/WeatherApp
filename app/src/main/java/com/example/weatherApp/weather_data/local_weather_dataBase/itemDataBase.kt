
@file:Suppress("SpellCheckingInspection")

package com.example.weatherApp.weather_data.local_weather_dataBase
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherApp.weather_data.weather_models.Item


@Database(entities = arrayOf(Item::class), version = 1, exportSchema = false)
abstract class itemDataBase : RoomDatabase() {

    abstract fun ItemsDao(): itemsDao

    companion object {
        @Volatile
        private var instance: itemDataBase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(lock = this) {
            Room.databaseBuilder(
                context.applicationContext,
                itemDataBase::class.java,
                "items_database"
            )
                .build().also { instance = it }
        }
    }
}


