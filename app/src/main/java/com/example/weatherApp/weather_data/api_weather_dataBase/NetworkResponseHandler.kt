package com.example.weatherApp.weather_data.api_weather_dataBase

import retrofit2.Response

abstract class NetworkResponseHandler {
    protected suspend fun <T> handleApiCall(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Result.success(body)
                }
            }
            Result.failure(Exception("❌ Network call failed: ${response.message()} (Code: ${response.code()})"))
        } catch (e: Exception) {
            Result.failure(Exception("❌ Exception occurred: ${e.localizedMessage ?: e.toString()}"))
        }
    }

    // ✅ פונקציה חדשה לתמיכה ברשימות (כמו חיפוש ערים)
    protected suspend fun <T> handleListApiCall(call: suspend () -> Response<List<T>>): Result<List<T>> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (!body.isNullOrEmpty()) {
                    return Result.success(body)
                }
            }
            Result.failure(Exception("❌ List API call failed: ${response.message()} (Code: ${response.code()})"))
        } catch (e: Exception) {
            Result.failure(Exception("❌ Exception occurred in List API call: ${e.localizedMessage ?: e.toString()}"))
        }
    }
}


