package com.example.weatherApp.helper_classes

class DataStatus<out Data> private constructor(val status: Status<Data>) {

    companion object {
        fun <Data> success(data: Data): DataStatus<Data> = DataStatus(Success(data))
        fun <Data> error(message: String, data: Data? = null): DataStatus<Data> = DataStatus(Error(message, data))
        fun <Data> loading(data: Data? = null): DataStatus<Data> = DataStatus(Loading(data))
    }
}

sealed class Status<out Data>(val data: Data? = null)
class Success<Data>(data: Data) : Status<Data>(data)
class Error<Data>(val message: String, data: Data? = null) : Status<Data>(data)
class Loading<Data>(data: Data? = null) : Status<Data>(data)
