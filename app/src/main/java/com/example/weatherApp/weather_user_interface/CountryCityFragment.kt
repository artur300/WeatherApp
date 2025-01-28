package com.example.weatherApp.weather_user_interface

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.weatherApp.R
import com.example.weatherApp.weather_data.api_weather_dataBase.CountriesApiService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class CountryCityFragment : Fragment(R.layout.layout_weather_fragment) {

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
    private var selectedCountry: String? = null
    private var selectedCity: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countrySpinner = view.findViewById(R.id.spinner_country)
        citySpinner = view.findViewById(R.id.spinner_city)

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
                    Log.e("CountryCityFragment", "Failed to fetch countries")
                    Toast.makeText(requireContext(), "Failed to fetch countries", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("CountryCityFragment", "Error fetching countries: ${e.message}")
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupCountrySpinner(countries: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listOf("בחר מדינה") + countries)
        countrySpinner.adapter = adapter

        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    selectedCountry = null
                    citySpinner.visibility = View.GONE // מסתיר את ספינר הערים עד שבוחרים מדינה
                } else {
                    selectedCountry = countries[position - 1]
                    setupCitySpinner(countryCityMap[selectedCountry] ?: listOf())
                }
                Log.d("CountryCityFragment", "Selected country: $selectedCountry")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupCitySpinner(cities: List<String>) {
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
                selectedCityTextView.text = "בחרת: ${selectedCity ?: "לא נבחר"}"
                Log.d("CountryCityFragment", "Selected city: $selectedCity")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}

