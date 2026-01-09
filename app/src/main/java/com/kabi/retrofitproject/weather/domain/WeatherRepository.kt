package com.kabi.retrofitproject.weather.domain

import com.kabi.retrofitproject.weather.domain.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeatherData(city: String): Result<WeatherResponse, DataError.Network>
}