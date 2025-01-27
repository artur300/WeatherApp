package com.example.weatherApp.weather_user_interface

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherApp.R
import com.example.weatherApp.helper_classes.Loading
import com.example.weatherApp.helper_classes.Success
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

        // בעת לחיצה על כפתור, בקש נתוני מזג אוויר
        fetchButton.setOnClickListener {
            val cityName = cityInput.text.toString()
            if (cityName.isNotEmpty()) {
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
                        }
                        is Loading -> {
                            weatherResult.text = "Loading weather data..."
                        }
                        is Error -> {
                            weatherResult.text = "Error: ${dataStatus.status.message}"
                        }
                        else -> {
                            weatherResult.text = "Unexpected state"
                        }
                    }

                }
            } else {
                weatherResult.text = "Please enter a city name."
            }
        }

    }
}
