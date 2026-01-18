package com.kabi.retrofitproject.weather.data.util

import com.kabi.retrofitproject.weather.domain.DataError
import retrofit2.HttpException
import java.io.IOException

fun Exception.toDataError(): DataError.Network {
    return when (this) {
        is HttpException -> when (this.code()) {
            400 -> DataError.Network.CITY_NOT_FOUND
            401 -> DataError.Network.UNAUTHORISED
            429 -> DataError.Network.TOO_MANY_REQUESTS
            else -> DataError.Network.UNKNOWN
        }
        is IOException -> DataError.Network.NO_INTERNET
        else -> DataError.Network.UNKNOWN
    }
}
