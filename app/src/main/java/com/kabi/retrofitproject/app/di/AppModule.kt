package com.kabi.retrofitproject.app.di

import com.kabi.retrofitproject.data.WeatherRepositoryImpl
import com.kabi.retrofitproject.domain.WeatherRepository
import com.kabi.retrofitproject.presentation.WeatherViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::WeatherRepositoryImpl) { bind<WeatherRepository>() }
    viewModelOf(::WeatherViewModel)
}