package com.kabi.retrofitproject.weather.presentation

sealed interface WeatherAction {
    data class OnSearchQueryChange(val query: String) : WeatherAction
    data object OnSearchClick : WeatherAction
    data class LoadWeather(val city: String) : WeatherAction
}