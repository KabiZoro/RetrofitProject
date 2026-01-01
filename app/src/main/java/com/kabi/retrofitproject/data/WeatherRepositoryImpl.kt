package com.kabi.retrofitproject.data

import android.net.http.HttpException
import com.google.gson.JsonSyntaxException
import com.kabi.retrofitproject.BuildConfig
import com.kabi.retrofitproject.domain.DataError
import com.kabi.retrofitproject.domain.Result
import com.kabi.retrofitproject.domain.WeatherRepository
import com.kabi.retrofitproject.domain.model.WeatherResponse
import java.io.IOException

class WeatherRepositoryImpl(
    private val weatherApiService: WeatherApiService
): WeatherRepository {
    override suspend fun getWeatherData(city: String): Result<WeatherResponse, DataError.Network> {
        return try {
            val response = weatherApiService.getWeather(
                apikey = BuildConfig.WEATHER_API_KEY,
                city = city
            )
            Result.Success(response)
        } catch (e: Exception) {
            val networkError = when (e) {
                is HttpException -> when (e.hashCode()) {
                    401 -> DataError.Network.UNAUTHORISED
                    408 -> DataError.Network.REQUEST_TIMEOUT
                    413 -> DataError.Network.PAYLOAD_TOO_LARGE
                    429 -> DataError.Network.TOO_MANY_REQUESTS
                    in 500..599 -> DataError.Network.SERVER_ERROR
                    else -> DataError.Network.UNKNOWN
                }
                is IOException -> DataError.Network.NO_INTERNET
                is JsonSyntaxException -> DataError.Network.SERIALIZATION
                else -> DataError.Network.UNKNOWN
            }
            Result.Error(networkError)
        }
    }
}