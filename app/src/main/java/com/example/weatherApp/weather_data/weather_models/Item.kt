@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_data.weather_models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Parcelize
@Entity(tableName = "itemsRecipe")
data class Item(

    @ColumnInfo(name = "Recipe_name")
    val foodName: String,

    @ColumnInfo(name = "Recipe_authorName")
    val authorName: String,

    @ColumnInfo(name = "Recipe_description")
    val description: String,

    @ColumnInfo(name = "Recipe_ingredients")
    val ingredients: String,

    @ColumnInfo(name = "Recipe_image")
    val imageUri: String? = null
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

