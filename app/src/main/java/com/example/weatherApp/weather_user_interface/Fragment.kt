@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_user_interface

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.weatherApp.R
import com.example.weatherApp.databinding.LayoutWeatherFragmentBinding
import com.example.weatherApp.helper_classes.WeatherBackgroundProvider
import dagger.hilt.android.AndroidEntryPoint
import il.co.syntax.fullarchitectureretrofithiltkotlin.utils.autoCleared

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.layout_weather_fragment) {

    private var binding by autoCleared<LayoutWeatherFragmentBinding>()
    private val weatherViewModel: WeatherViewModel by viewModels()

    private var selectedCity: String? = null
    private var selectedCountry: String? = null
    private var userPressedSearch: Boolean = false //  משתנה שבודק אם המשתמש לחץ על חיפוש

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LayoutWeatherFragmentBinding.bind(view)

        Log.d("WeatherFragment", "onViewCreated called - Initializing UI components")

        setupSearchField()
        observeWeatherData()
        observeSearchResults()

        binding.btnFetchWeather.setOnClickListener {
            userPressedSearch = true //  המשתמש לחץ על חיפוש

            if (selectedCity.isNullOrEmpty() || selectedCountry.isNullOrEmpty()) {
                //  אם המשתמש לא הזין עיר ומדינה, להציג טוסט מתאים
                showToast(getString(R.string.select_country_city))
            } else {
                //  אם המשתמש הזין עיר ומדינה, לבדוק מזג אוויר
                binding.progressBar.visibility = View.VISIBLE
                weatherViewModel.getWeatherByLocation(selectedCity!!, selectedCountry!!)
            }
        }
    }

    /**
     *  פונקציה לטיפול בהשלמה אוטומטית של חיפוש ערים
     */
    private fun setupSearchField() {
        val autoCompleteTextView: AutoCompleteTextView = binding.etSearchCity
        val autoCompleteAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line)

        autoCompleteTextView.setAdapter(autoCompleteAdapter)

        autoCompleteTextView.addTextChangedListener { text ->
            val query = text.toString()
            if (query.length > 2) { //  חיפוש רק אם יש יותר מ-2 תווים
                userPressedSearch = false //  המשתמש רק מקליד, לא ללחוץ על חיפוש
                weatherViewModel.searchLocations(query)
            }
        }

        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selected = autoCompleteAdapter.getItem(position)?.split(", ")
            if (selected?.size == 2) {
                selectedCity = selected[0]
                selectedCountry = selected[1]
            }
        }
    }

    /**
     *  מאזין לנתוני החיפוש מה-ViewModel
     */
    private fun observeSearchResults() {
        weatherViewModel.searchResults.observe(viewLifecycleOwner) { results ->
            val adapter = binding.etSearchCity.adapter
            if (adapter is ArrayAdapter<*>) {
                val autoCompleteAdapter = adapter as ArrayAdapter<String>
                autoCompleteAdapter.clear()

                if (results.isNotEmpty()) {
                    val cityList = results.map { "${it.name}, ${it.country}" }
                    autoCompleteAdapter.addAll(cityList)
                }

                autoCompleteAdapter.notifyDataSetChanged()
            } else {
                Log.e("WeatherFragment", " Adapter is not of type ArrayAdapter<String>")
            }
        }
    }

    /**
     *  מעקב אחר נתוני מזג האוויר מה-ViewModel ועדכון ה-UI
     */
    private fun observeWeatherData() {
        weatherViewModel.weatherData.observe(viewLifecycleOwner) { weatherData ->
            binding.progressBar.visibility = View.GONE
            if (userPressedSearch) { //  רק אם המשתמש לחץ על חיפוש
                if (weatherData != null) {
                    //  אם נמצאה תוצאה, להציג הודעה שהתוצאה נמצאה
                    showToast(getString(R.string.result_found))

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
                    //  אם לא נמצאה תוצאה, להציג הודעת שגיאה
                    showToast(getString(R.string.no_matching_city))
                }
            }
        }
    }

    /**
     *  פונקציה כללית להצגת הודעות Toast
     */
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}



/**
 * סיכום המחלקה:

 * WeatherFragment
 * - מחלקת ממשק משתמש המייצגת את המסך הראשי להצגת מזג האוויר וחיפוש ערים.
 * - משתמשת ב-ViewModel כדי לקבל ולהציג נתונים ממשקיעים שונים.

 * פונקציות:

 * onViewCreated
 * - מופעלת כאשר התצוגה נטענת, מבצעת אתחול ומאזינה לאירועים של ממשק המשתמש.
 * - מגדירה את כפתור החיפוש ומוודאת אם המשתמש הזין עיר ומדינה לפני השליחה.

 * setupSearchField
 * - מטפלת בהשלמה אוטומטית של חיפוש ערים.
 * - מאזינה לקלט שהמשתמש מקליד ומבצעת חיפוש ברשת רק אם יש יותר משני תווים.

 * observeSearchResults
 * - מאזינה לנתוני החיפוש שחזרו מה-ViewModel.
 * - מעדכנת את רשימת ההצעות בערך שקיבלנו מה-API.

 * observeWeatherData
 * - מאזינה לשינויים בנתוני מזג האוויר שהתקבלו ומעדכנת את ממשק המשתמש בהתאם.
 * - אם נמצא מזג אוויר מתאים, הוא מוצג; אחרת, מופיעה הודעה שהעיר לא נמצאה.

 * showToast
 * - מציגה הודעות למשתמש באמצעות Toast.

 * מטרת המחלקה היא להפעיל את כל הפונקציונליות הקשורה לחיפוש מזג אוויר ולהציג את הנתונים למשתמש.
 */


