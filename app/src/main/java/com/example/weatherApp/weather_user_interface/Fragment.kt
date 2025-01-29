package com.example.weatherApp.weather_user_interface

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
class WeatherFragment : Fragment(R.layout.layout_weather_fragment) {

    private var binding by autoCleared<LayoutWeatherFragmentBinding>()
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
        binding = LayoutWeatherFragmentBinding.bind(view)

        Log.d("WeatherFragment", "onViewCreated called - Initializing UI components")

        fetchCountries()

        binding.btnFetchWeather.setOnClickListener {
            if (!selectedCountry.isNullOrEmpty() && !selectedCity.isNullOrEmpty()) {
                weatherViewModel.getWeatherByLocation(selectedCity!!, selectedCountry!!)
            } else {
                Toast.makeText(requireContext(), getString(R.string.select_country_city), Toast.LENGTH_SHORT).show()
            }
        }

        observeWeatherData()
    }

    private fun fetchCountries() {
        lifecycleScope.launch {
            try {
                val response = countriesApiService.getCountries()
                if (response.isSuccessful && response.body() != null) {
                    val countriesList = response.body()!!.data
                    countryCityMap = countriesList.associateBy({ it.country }, { it.cities }).toMutableMap()
                    setupCountrySpinner(countryCityMap.keys.toList())
                } else {
                    Log.e("WeatherFragment", "Failed to fetch countries")
                }
            } catch (e: Exception) {
                Log.e("WeatherFragment", "Error fetching countries: ${e.message}")
            }
        }
    }

    private fun setupCountrySpinner(countries: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,
            listOf(getString(R.string.select_country)) + countries)
        binding.spinnerCountry.adapter = adapter

        binding.spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    selectedCountry = null
                    binding.spinnerCity.visibility = View.GONE
                } else {
                    selectedCountry = countries[position - 1]
                    setupCitySpinner(countryCityMap[selectedCountry] ?: listOf())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupCitySpinner(cities: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,
            listOf(getString(R.string.select_city)) + cities)
        binding.spinnerCity.adapter = adapter
        binding.spinnerCity.visibility = if (cities.isNotEmpty()) View.VISIBLE else View.GONE

        binding.spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCity = if (position == 0) null else cities[position - 1]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun observeWeatherData() {
        weatherViewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
            if (weatherData != null) {
                binding.tvWeatherCity.text = getString(R.string.weather_city, weatherData.name)
                binding.tvWeatherCountry.text = getString(R.string.weather_country, weatherData.country)

                binding.tvWeatherCity.visibility = View.VISIBLE
                binding.tvWeatherCountry.visibility = View.VISIBLE

                binding.tvTemperature.text = getString(R.string.temperature, weatherData.tempC)
                binding.tvFeelsLike.text = getString(R.string.feels_like, weatherData.feelsLikeC)
                binding.tvWindSpeed.text = getString(R.string.wind_speed, weatherData.windKph, weatherData.windDir)
                binding.tvHumidity.text = getString(R.string.humidity, weatherData.humidity)
                binding.tvCondition.text = getString(R.string.condition, weatherData.conditionText)
            } else {
                binding.tvWeatherCity.text = getString(R.string.error_empty_data)
                binding.tvWeatherCountry.text = ""
            }
        }
    }
}
