package com.example.weatherApp.weather_user_interface

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weatherApp.R
import com.example.weatherApp.helper_classes.Loading
import com.example.weatherApp.helper_classes.Success
import com.example.weatherApp.weather_data.api_weather_dataBase.CountriesApiService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.layout_weather_fragment) {

    private lateinit var countrySpinner: Spinner
    private lateinit var citySpinner: Spinner
    private lateinit var fetchButton: Button
    private lateinit var weatherResult: TextView

    private val weatherViewModel: WeatherViewModel by viewModels()

    private val countriesApiService: CountriesApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://countriesnow.space/api/v0.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApiService::class.java)
    }

    private var countryCityMap: MutableMap<String, List<String>> = mutableMapOf()
    private var selectedCountry: String? = null
    private var selectedCity: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("WeatherFragment", "onViewCreated called - Initializing UI components")

        countrySpinner = view.findViewById(R.id.spinner_country)
        citySpinner = view.findViewById(R.id.spinner_city)
        fetchButton = view.findViewById(R.id.btn_fetch_weather)
        weatherResult = view.findViewById(R.id.tv_weather_result)

        fetchCountries()

        fetchButton.setOnClickListener {
            Log.d("WeatherFragment", "Fetch button clicked")
            if (!selectedCountry.isNullOrEmpty() && !selectedCity.isNullOrEmpty()) {
                Log.d("WeatherFragment", "Fetching weather for: $selectedCity, $selectedCountry")
                fetchWeather(selectedCity!!, selectedCountry!!)
            } else {
                Log.e("WeatherFragment", "❌ Error: Country or City is null or empty! Country: $selectedCountry, City: $selectedCity")
                Toast.makeText(requireContext(), "נא לבחור מדינה ועיר", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchCountries() {
        lifecycleScope.launch {
            try {
                Log.d("WeatherFragment", "Fetching countries from API...")
                val response = countriesApiService.getCountries()
                Log.d("WeatherFragment", "Response received - Code: ${response.code()}, Message: ${response.message()}")

                if (response.isSuccessful && response.body() != null) {
                    val countriesList = response.body()!!.data
                    Log.d("WeatherFragment", "✅ Countries fetched successfully - Count: ${countriesList.size}")
                    countryCityMap = countriesList.associateBy({ it.country }, { it.cities }).toMutableMap()
                    setupCountrySpinner(countryCityMap.keys.toList())
                } else {
                    Log.e("WeatherFragment", "❌ Failed to fetch countries: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("WeatherFragment", "❌ Exception while fetching countries: ${e.message}")
            }
        }
    }

    private fun setupCountrySpinner(countries: List<String>) {
        Log.d("WeatherFragment", "Setting up country spinner - Countries count: ${countries.size}")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listOf("בחר מדינה") + countries)
        countrySpinner.adapter = adapter

        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    selectedCountry = null
                    citySpinner.visibility = View.GONE
                    Log.d("WeatherFragment", "Country selection reset")
                } else {
                    selectedCountry = countries[position - 1]
                    Log.d("WeatherFragment", "✅ Selected country: $selectedCountry")
                    setupCitySpinner(countryCityMap[selectedCountry] ?: listOf())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d("WeatherFragment", "No country selected")
            }
        }
    }

    private fun setupCitySpinner(cities: List<String>) {
        Log.d("WeatherFragment", "Setting up city spinner - Cities count: ${cities.size}")
        if (cities.isNotEmpty()) {
            citySpinner.visibility = View.VISIBLE
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listOf("בחר עיר") + cities)
            citySpinner.adapter = adapter
        } else {
            citySpinner.visibility = View.GONE
        }

        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCity = if (position == 0) null else cities[position - 1]
                Log.d("WeatherFragment", "✅ Selected city: $selectedCity")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d("WeatherFragment", "No city selected")
            }
        }
    }

    private fun fetchWeather(city: String, country: String) {
        weatherResult.text = "Fetching weather data..."
        Log.d("WeatherFragment", "🔄 Fetching weather data for city: $city, country: $country")

        weatherViewModel.getWeatherByLocation(city, country).observe(viewLifecycleOwner) { dataStatus ->
            Log.d("WeatherFragment", "Weather data status update: $dataStatus")
            when (val status = dataStatus.status) {
                is Success -> {
                    val weatherData = status.data
                    if (weatherData != null) {
                        val resultText = """
                🌍 ${weatherData.locationName}, ${weatherData.country}
                🌡 טמפרטורה: ${weatherData.tempC}°C
                ❄ מרגיש כמו: ${weatherData.feelsLikeC}°C
                💨 מהירות רוח: ${weatherData.windKph} km/h (${weatherData.windDir})
                💧 לחות: ${weatherData.humidity}%
                ☁ תיאור: ${weatherData.conditionText}
            """.trimIndent()
                        weatherResult.text = resultText
                        Log.d("WeatherFragment", "✅ Weather data displayed successfully")
                    } else {
                        weatherResult.text = "❌ שגיאה: הנתונים שהתקבלו ריקים!"
                        Log.e("WeatherFragment", "❌ Error: Received empty weather data!")
                    }
                }
                is Loading -> {
                    weatherResult.text = "🔄 טוען נתונים..."
                    Log.d("WeatherFragment", "🔄 Weather data is loading...")
                }
                is Error -> {
                    weatherResult.text = "❌ שגיאה: ${status.message}"
                    Log.e("WeatherFragment", "❌ Error fetching weather: ${status.message}")
                }
                else -> {
                    weatherResult.text = "❌ שגיאה לא צפויה"
                    Log.e("WeatherFragment", "❌ Unexpected error in LiveData observer")
                }
            }
        }
    }
}


