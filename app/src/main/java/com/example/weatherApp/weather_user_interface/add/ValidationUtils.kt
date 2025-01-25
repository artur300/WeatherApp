@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.utils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.example.weatherApp.R
import com.example.weatherApp.databinding.AddRecipeBinding

object ValidationUtils {

    fun validateFields(context: Context, binding: AddRecipeBinding, imageUri: Uri?): Boolean {
        return if (binding.foodNameInput.text?.isNotEmpty() == true &&
            binding.authorNameInput.text?.isNotEmpty() == true &&
            binding.foodDescriptionInput.text?.isNotEmpty() == true &&
            binding.ingredientsDescriptionInput.text?.isNotEmpty() == true &&
            imageUri != null
        ) {
            true
        } else {

            Toast.makeText(
                context,
                context.getString(R.string.fill_all_fields_message),
                Toast.LENGTH_SHORT
            ).show()
            false
        }
    }
}

