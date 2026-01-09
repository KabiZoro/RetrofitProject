package com.kabi.retrofitproject.app

import android.app.Application
import com.kabi.retrofitproject.app.di.appModule
import com.kabi.retrofitproject.weather.di.weatherModule
import com.kabi.retrofitproject.weather.data.di.networkModule
import io.kotzilla.sdk.analytics.koin.analytics
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApp)
            modules(
                appModule,
                networkModule,
                weatherModule
            )
            analytics()
        }
    }
}