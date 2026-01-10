package com.kabi.retrofitproject.weather.data

import android.os.Build
import androidx.annotation.RequiresExtension
import com.google.gson.JsonSyntaxException
import com.kabi.retrofitproject.BuildConfig
import com.kabi.retrofitproject.weather.domain.Result
import com.kabi.retrofitproject.weather.domain.DataError
import com.kabi.retrofitproject.weather.domain.WeatherRepository
import com.kabi.retrofitproject.weather.domain.model.WeatherResponse
import retrofit2.HttpException
import java.io.IOException

class WeatherRepositoryImpl(
    private val weatherApiService: WeatherApiService
): WeatherRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
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
                    400 -> DataError.Network.CITY_NOT_FOUND
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