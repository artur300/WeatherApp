package com.example.weatherApp.weather_user_interface

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherApp.R
import com.example.weatherApp.helper_classes.Loading
import com.example.weatherApp.helper_classes.Success
import com.example.weatherApp.helper_classes.Error
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.layout_weather_fragment) {

    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // קישורים לרכיבי ה-UI
        val cityInput = view.findViewById<EditText>(R.id.et_city_name)
        val fetchButton = view.findViewById<Button>(R.id.btn_fetch_weather)
        val weatherResult = view.findViewById<TextView>(R.id.tv_weather_result)

        // טיפול בלחיצה על הכפתור
        fetchButton.setOnClickListener {
            val cityName = cityInput.text.toString().trim()
            if (cityName.isNotEmpty()) {
                weatherResult.text = "Fetching weather data..."
                Log.d("WeatherFragment", "Fetching weather data for city: $cityName")

                // רישום Observer לנתונים
                weatherViewModel.getWeatherByLocation(cityName).observe(viewLifecycleOwner) { dataStatus ->
                    when (dataStatus.status) {
                        is Success -> {
                            val weatherData = dataStatus.status.data
                            weatherResult.text = """
                                Country: ${weatherData?.country}
                                City: ${weatherData?.locationName}
                                Temperature: ${weatherData?.tempC}°C
                                Feels Like: ${weatherData?.feelsLikeC}°C
                                Condition: ${weatherData?.conditionText}
                                Wind: ${weatherData?.windKph} kph ${weatherData?.windDir}
                                Humidity: ${weatherData?.humidity}%
                            """.trimIndent()
                            Log.d("WeatherFragment", "Weather data fetched successfully for $cityName")
                        }
                        is Loading -> {
                            weatherResult.text = "Loading weather data..."
                            Log.d("WeatherFragment", "Loading weather data for $cityName")
                        }
                        is Error -> {
                            weatherResult.text = "Error: ${dataStatus.status.message}"
                            Log.e("WeatherFragment", "Error fetching weather data: ${dataStatus.status.message}")
                        }
                        else -> {
                            weatherResult.text = "Unexpected state."
                            Log.w("WeatherFragment", "Unexpected state for weather data.")
                        }
                    }
                }
            } else {
                weatherResult.text = "Please enter a city name."
                Log.w("WeatherFragment", "City name is empty.")
            }
        }
    }
}

