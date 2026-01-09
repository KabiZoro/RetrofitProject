package com.kabi.retrofitproject.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabi.retrofitproject.weather.domain.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.kabi.retrofitproject.weather.domain.WeatherRepository

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(WeatherState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
//                onAction(WeatherAction.LoadWeather("Tokyo"))
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
                _state.update {
                    it.copy(
                        searchQuery = action.query
                    )
                }
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
            val result = weatherRepository.getWeatherData(city)
            _state.update {
                it.copy(
                    weatherResult = result
                )
            }
        }
    }
}
