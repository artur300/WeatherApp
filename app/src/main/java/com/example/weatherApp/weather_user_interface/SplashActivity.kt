package com.example.weatherApp.weather_user_interface

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherApp.R
import com.example.weatherApp.databinding.SplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: SplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.logo.startAnimation(fadeIn)
        binding.appTitle.startAnimation(fadeIn)

        val sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("is_first_launch", true)

        if (isFirstLaunch) {
            deleteDatabase("items_database")

            sharedPreferences.edit().clear().apply()
            filesDir.deleteRecursively()
            sharedPreferences.edit().putBoolean("is_first_launch", false).apply()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, WeatherMainActivity::class.java))
            finish()
        }, 2000)
    }
}

