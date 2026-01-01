package com.kabi.retrofitproject.domain

import com.kabi.retrofitproject.domain.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeatherData(city: String): Result<WeatherResponse, DataError.Network>
}