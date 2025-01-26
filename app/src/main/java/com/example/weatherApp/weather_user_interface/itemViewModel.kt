@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ
package com.example.weatherApp.weather_user_interface

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.weatherApp.weather_data.weather_models.Item
import com.example.weatherApp.weather_data.Repositories.ItemRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class itemViewModel(application: Application): AndroidViewModel(application) {
    private val repository = ItemRepository(application)
    val items: LiveData<List<Item>>? = repository.getItems()

    fun addItem(item: Item) {
        viewModelScope.launch {repository.addItem(item)}
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {repository.deleteItem(item)}
    }

}
