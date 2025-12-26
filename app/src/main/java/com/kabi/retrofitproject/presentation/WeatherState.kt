package com.kabi.retrofitproject.presentation

import com.kabi.retrofitproject.domain.DataError
import com.kabi.retrofitproject.domain.Result
import com.kabi.retrofitproject.domain.model.WeatherResponse

data class WeatherState(
    val searchQuery: String = "",
    val weatherResult: Result<WeatherResponse, DataError.Network>? = null
)