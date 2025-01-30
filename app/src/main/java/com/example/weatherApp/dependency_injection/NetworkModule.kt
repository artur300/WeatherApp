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
        ).fallbackToDestructiveMigration() // טיפול בשינויים בסכמת מסד הנתונים
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
        return retrofit.create(WeatherSearchService::class.java)
    }
}
