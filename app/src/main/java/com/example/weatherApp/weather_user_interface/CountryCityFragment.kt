package com.example.weatherApp.weather_user_interface

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.weatherApp.R
import com.example.weatherApp.weather_data.api_weather_dataBase.CountriesApiService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class CountryCityFragment : Fragment(R.layout.layout_country_city_selector) {

    private lateinit var countrySpinner: Spinner
    private lateinit var citySpinner: Spinner
    private lateinit var selectedCityTextView: TextView

    private val countriesApiService: CountriesApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://countriesnow.space/api/v0.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApiService::class.java)
    }

    private var countryCityMap: MutableMap<String, List<String>> = mutableMapOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countrySpinner = view.findViewById(R.id.spinner_country)
        citySpinner = view.findViewById(R.id.spinner_city)
        selectedCityTextView = view.findViewById(R.id.tv_selected_city)

        fetchCountries()
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
                    Toast.makeText(requireContext(), "Failed to fetch countries", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupCountrySpinner(countries: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, countries)
        countrySpinner.adapter = adapter

        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCountry = countries[position]
                setupCitySpinner(countryCityMap[selectedCountry] ?: listOf())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupCitySpinner(cities: List<String>) {
        if (cities.isNotEmpty()) {
            citySpinner.visibility = View.VISIBLE // ✅ מציג את רשימת הערים
        } else {
            citySpinner.visibility = View.GONE  // ❌ מסתיר אותו אם אין ערים למדינה
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, cities)
        citySpinner.adapter = adapter

        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCityTextView.text = "בחרת: ${cities[position]}"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

}
