@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.weatherApp.weather_user_interface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherApp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}



/**
-------------------------------------------
https://www.weatherapi.com/docs/
https://jsonformatter.curiousconcept.com/#
http://api.weatherapi.com/v1/current.json?key=f3b82ad32cb74471b8e71237252501&q=London
-------------------------------------------
נתונים שניתן לקבל מה-API

נתוני מיקום
name: String // שם העיר
region: String // האזור או המחוז
country: String // המדינה
lat: Double // קו רוחב
lon: Double // קו אורך
tz_id: String // מזהה אזור זמן
localtime_epoch: Long // זמן מקומי כערך Epoch
localtime: String // זמן מקומי בפורמט "yyyy-MM-dd HH:mm"

נתונים נוכחיים
last_updated_epoch: Long // זמן עדכון אחרון כערך Epoch
last_updated: String // זמן עדכון אחרון בפורמט "yyyy-MM-dd HH:mm"
temp_c: Double // טמפרטורה במעלות צלזיוס
temp_f: Double // טמפרטורה במעלות פרנהייט
is_day: Int // אינדיקציה אם זה יום (1) או לילה (0)
condition_text: String // תיאור מצב מזג האוויר (לדוגמה: "גשם קל")
condition_icon: String // קישור לאייקון המייצג את מצב מזג האוויר
condition_code: Int // קוד המייצג את מצב מזג האוויר
wind_mph: Double // מהירות הרוח במייל לשעה
wind_kph: Double // מהירות הרוח בקילומטר לשעה
wind_degree: Int // זווית כיוון הרוח במעלות
wind_dir: String // כיוון הרוח (לדוגמה: "W" למערב)
pressure_mb: Double // לחץ אטמוספרי במיליבר
pressure_in: Double // לחץ אטמוספרי באינצ'ים
precip_mm: Double // כמות משקעים במילימטרים
precip_in: Double // כמות משקעים באינצ'ים
humidity: Int // אחוז לחות
cloud: Int // אחוז כיסוי עננים
feelslike_c: Double // טמפרטורה מורגשת במעלות צלזיוס
feelslike_f: Double // טמפרטורה מורגשת במעלות פרנהייט
windchill_c: Double // טמפרטורת רוח במעלות צלזיוס
windchill_f: Double // טמפרטורת רוח במעלות פרנהייט
heatindex_c: Double // מדד חום במעלות צלזיוס
heatindex_f: Double // מדד חום במעלות פרנהייט
dewpoint_c: Double // נקודת הטל במעלות צלזיוס
dewpoint_f: Double // נקודת הטל במעלות פרנהייט
vis_km: Double // ראות בקילומטרים
vis_miles: Double // ראות במיילים
uv: Double // מדד קרינת UV
gust_mph: Double // משב רוח במייל לשעה
gust_kph: Double // משב רוח בקילומטר לשעה

 */



