@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ
package com.example.weatherApp.weather_data.Repositories

import android.app.Application
import com.example.weatherApp.weather_data.local_weather_dataBase.itemDataBase
import com.example.weatherApp.weather_data.local_weather_dataBase.itemsDao
import com.example.weatherApp.weather_data.weather_models.Item

class ItemRepository(application: Application)  {

    private var itemDao: itemsDao?

    init {
        val db = itemDataBase.getDatabase(application.applicationContext)
        itemDao = db?.ItemsDao()
    }
    fun getItems() = itemDao?.getItems()
    fun getItem(id: Int) = itemDao?.getItem(id)
    suspend fun addItem(item: Item) {itemDao?.addItem(item)}
    suspend fun deleteItem(item: Item) { itemDao?.deleteItem(item)}

}
