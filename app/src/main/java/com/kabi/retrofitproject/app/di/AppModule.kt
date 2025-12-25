package com.kabi.retrofitproject.app.di

import com.kabi.retrofitproject.presentation.NewsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel{ NewsViewModel(get()) }
}