package com.kabi.retrofitproject.weather.di

import com.kabi.retrofitproject.weather.data.WeatherRepositoryImpl
import com.kabi.retrofitproject.weather.domain.WeatherRepository
import com.kabi.retrofitproject.weather.presentation.WeatherViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val weatherModule = module {
    singleOf(::WeatherRepositoryImpl) { bind<WeatherRepository>() }
    viewModelOf(::WeatherViewModel)
}