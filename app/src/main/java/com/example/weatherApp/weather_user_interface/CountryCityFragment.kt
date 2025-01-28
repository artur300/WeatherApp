package com.example.weatherApp.weather_user_interface

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.weatherApp.R
import com.example.weatherApp.databinding.LayoutWeatherFragmentBinding
import com.example.weatherApp.weather_data.api_weather_dataBase.CountriesApiService
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class CountryCityFragment : Fragment(R.layout.layout_weather_fragment) {

    private var binding by autoCleared<LayoutWeatherFragmentBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LayoutWeatherFragmentBinding.bind(view)

        fetchCountries()
    }

    private fun fetchCountries() {
        lifecycleScope.launch {
            // קוד לשליפת מדינות נשאר זהה...
        }
    }
}

