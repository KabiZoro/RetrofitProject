package com.kabi.retrofitproject.weather.presentation

import com.kabi.retrofitproject.weather.domain.DataError
import com.kabi.retrofitproject.weather.domain.Result
import com.kabi.retrofitproject.weather.domain.model.WeatherResponse

data class WeatherState(
    val searchQuery: String = "",
    val weatherResult: Result<WeatherResponse, DataError.Network>? = null
)