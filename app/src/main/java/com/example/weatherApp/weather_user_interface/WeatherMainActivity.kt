@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_user_interface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherApp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}




