package com.example.weatherApp.weather_data.api_weather_dataBase
import retrofit2.Response
import com.example.weatherApp.helper_classes.DataStatus
abstract class NetworkResponseHandler {
    protected suspend fun <T> handleApiCall(call: suspend () -> Response<T>): DataStatus<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return DataStatus.success(body)
                }
            }
            DataStatus.error(
                message = "Network call failed: ${response.message()} ${response.code()}"
            )
        } catch (e: Exception) {
            DataStatus.error(
                message = "Network call failed: ${e.localizedMessage ?: e.toString()}"
            )
        }
    }
}
