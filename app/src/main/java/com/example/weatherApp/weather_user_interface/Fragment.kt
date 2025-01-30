package com.example.weatherApp.weather_user_interface

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.weatherApp.R
import com.example.weatherApp.databinding.LayoutWeatherFragmentBinding
import com.example.weatherApp.helper_classes.CustomDialogHelper
import com.example.weatherApp.helper_classes.WeatherBackgroundProvider
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.layout_weather_fragment) {

    private var binding by autoCleared<LayoutWeatherFragmentBinding>()
    private val weatherViewModel: WeatherViewModel by viewModels()

    private var selectedCity: String? = null
    private var selectedCountry: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LayoutWeatherFragmentBinding.bind(view)

        Log.d("WeatherFragment", "onViewCreated called - Initializing UI components")

        setupSearchField()
        observeWeatherData()

        binding.btnFetchWeather.setOnClickListener {
            if (!selectedCity.isNullOrEmpty() && !selectedCountry.isNullOrEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                weatherViewModel.getWeatherByLocation(selectedCity!!, selectedCountry!!)
            } else {
                Toast.makeText(requireContext(), getString(R.string.select_country_city), Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ✅ מחליף את הפונקציה הישנה של "שליפת מדינות" - משתמש ב-EditText לחיפוש חכם
    private fun setupSearchField() {
        binding.etSearchCity.addTextChangedListener { text ->
            val query = text.toString()
            if (query.length > 2) { // ✅ מתחילים חיפוש רק אחרי 3 תווים
                weatherViewModel.searchLocations(query) { results ->
                    if (results != null && results.isNotEmpty()) {
                        val firstResult = results.first()  // בוחר את התוצאה הראשונה אוטומטית
                        selectedCity = firstResult.name
                        selectedCountry = firstResult.country
                    } else {
                        showRetryDialog()
                    }
                }
            }
        }
    }

    // ✅ פונקציה להצגת דיאלוג כאשר החיפוש נכשל
    private fun showRetryDialog() {
        CustomDialogHelper.showRetryDialog(requireContext()) {
            setupSearchField() // חיפוש מחדש
        }
    }

    private fun observeWeatherData() {
        weatherViewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
            binding.progressBar.visibility = View.GONE
            if (weatherData != null) {
                binding.tvWeatherCity.text = getString(R.string.weather_city, weatherData.name)
                binding.tvWeatherCountry.text = getString(R.string.weather_country, weatherData.country)
                binding.tvTemperature.text = getString(R.string.temperature, weatherData.tempC)

                Glide.with(this)
                    .load(weatherData.conditionIcon)
                    .into(binding.weatherIcon)

                binding.tvFeelsLike.text = getString(R.string.feels_like, weatherData.feelsLikeC)
                binding.tvWindSpeed.text = getString(R.string.wind_speed, weatherData.windKph, weatherData.windDir)
                binding.tvHumidity.text = getString(R.string.humidity, weatherData.humidity)
                binding.tvCondition.text = getString(R.string.condition, weatherData.conditionText)

                binding.weatherBackground.setImageResource(
                    WeatherBackgroundProvider.getBackgroundForCondition(weatherData.conditionText)
                )
            } else {
                binding.tvWeatherCity.text = getString(R.string.error_empty_data)
                binding.tvWeatherCountry.text = ""
            }
        }
    }
}


