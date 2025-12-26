package com.kabi.retrofitproject.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonSyntaxException
import com.kabi.retrofitproject.data.WeatherApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.kabi.retrofitproject.BuildConfig
import com.kabi.retrofitproject.domain.DataError
import com.kabi.retrofitproject.domain.Result
import retrofit2.HttpException
import java.io.IOException

class WeatherViewModel(
    private val weatherApiService: WeatherApiService
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(WeatherState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
//                onAction(WeatherAction.LoadWeather("Chennai"))
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WeatherState()
        )

    fun onAction(action: WeatherAction) {
        when (action) {
            is WeatherAction.LoadWeather -> {
                fetchWeather(action.city)
            }

            WeatherAction.OnSearchClick -> {
                onAction(WeatherAction.LoadWeather(_state.value.searchQuery))
            }

            is WeatherAction.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = action.query) }
            }
        }
    }

    private fun fetchWeather(city: String) {
        if (city.isBlank()) return
        viewModelScope.launch {
            _state.update {
                it.copy(
                    weatherResult = Result.Loading
                )
            }
            try {
                val response = weatherApiService.getWeather(
                    apikey = BuildConfig.WEATHER_API_KEY,
                    city = city
                )
                _state.update {
                    it.copy(
                        weatherResult = Result.Success(response)
                    )
                }
            } catch (e: Exception) {
                val networkError = when(e){
                    is HttpException -> {
                        when(e.code()) {
                            401 -> DataError.Network.UNAUTHORISED
                            408 -> DataError.Network.REQUEST_TIMEOUT
                            413 -> DataError.Network.PAYLOAD_TOO_LARGE
                            429 -> DataError.Network.TOO_MANY_REQUESTS
                            in 500..599 -> DataError.Network.SERVER_ERROR
                            else -> DataError.Network.UNKNOWN
                        }
                    }
                    is IOException -> DataError.Network.NO_INTERNET
                    is JsonSyntaxException -> DataError.Network.SERIALIZATION
                    else -> DataError.Network.UNKNOWN
                }
                _state.update {
                    it.copy(
                        weatherResult = Result.Error(networkError)
                    )
                }
            }
        }
    }
}
