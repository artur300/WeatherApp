@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_user_interface.all

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherApp.weather_data.weather_models.Item


//*******************************************
//בשביל ליצור אנימציה של מחיקת כרטיסיות
//*******************************************


class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}

