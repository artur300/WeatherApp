@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.helper_classes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class weatherApplication :Application() {
}