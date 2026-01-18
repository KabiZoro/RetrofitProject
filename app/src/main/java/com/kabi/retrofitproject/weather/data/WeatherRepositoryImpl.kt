package com.kabi.retrofitproject.weather.data

import android.os.Build
import androidx.annotation.RequiresExtension
import com.kabi.retrofitproject.BuildConfig
import com.kabi.retrofitproject.weather.data.util.toDataError
import com.kabi.retrofitproject.weather.domain.DataError
import com.kabi.retrofitproject.weather.domain.Result
import com.kabi.retrofitproject.weather.domain.WeatherRepository
import com.kabi.retrofitproject.weather.domain.model.WeatherResponse

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
            Result.Error(e.toDataError())
        }
    }
}