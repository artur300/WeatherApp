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
            Result.failure(Exception("Network call failed: ${response.message()} ${response.code()}"))
        } catch (e: Exception) {
            Result.failure(Exception("Network call failed: ${e.localizedMessage ?: e.toString()}"))
        }
    }
}

