
@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.dependency_injection

import android.content.Context
import androidx.room.Room
import com.example.weatherApp.helper_classes.BaseUrlProvider
import com.example.weatherApp.weather_data.api_weather_dataBase.WeatherSearchService
import com.example.weatherApp.weather_data.api_weather_dataBase.WeatherService
import com.example.weatherApp.weather_data.local_weather_dataBase.WeatherDataDao
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.weatherApp.weather_data.local_weather_dataBase.WeatherDatabase

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrlProvider.BASE_URL) // כתובת ה-Base URL מסופקת ממחלקת ה-BaseUrlProvider
            .addConverterFactory(GsonConverterFactory.create(gson)) // שימוש ב-Gson להמרת נתונים
            .build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create() // יצירת Gson עבור המרת JSON
    }

    @Provides
    @Singleton
    fun provideWeatherService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): WeatherDatabase {
        return Room.databaseBuilder(
            appContext,
            WeatherDatabase::class.java,
            "weather_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDataDao(database: WeatherDatabase): WeatherDataDao {
        return database.weatherDataDao()
    }


    @Provides
    @Singleton
    fun provideWeatherSearchService(retrofit: Retrofit): WeatherSearchService {
        return retrofit.create(WeatherSearchService::class.java)// יצירת מופע של WeatherSearchService בעזרת Retrofit
    }
}

/**
 *  סיכום הפונקציות:

 *  provideRetrofit
 *    - יוצר ומגדיר חיבור לשרת בעזרת רטרופיט כדי לבצע בקשות ולהביא נתונים מהרשת.

 *  provideGson
 *    - מספק כלי להמרת נתונים בפורמט ג'יסון לאובייקטים ולהפך.

 *  provideWeatherService
 *    - מחזיר מופע שמאפשר גישה לשירותי מזג האוויר דרך השרת.

 *  provideLocalDatabase
 *    - יוצר את מסד הנתונים המקומי של האפליקציה כדי לאחסן נתונים גם כשאין חיבור לרשת.

 *  provideWeatherDataDao
 *    - מספק גישה לפונקציות שמנהלות את הנתונים של מזג האוויר בתוך מסד הנתונים.

 *  provideWeatherSearchService
 *    - מאפשר חיפוש ערים בשרת כדי להביא מידע על מיקומים זמינים.

 * 💡 קובץ זה מאפשר לכל חלקי האפליקציה להשתמש ברכיבים הללו בלי צורך ליצור אותם מחדש בכל פעם.
 */
