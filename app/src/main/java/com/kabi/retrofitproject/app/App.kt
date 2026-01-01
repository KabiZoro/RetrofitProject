package com.kabi.retrofitproject.app

import android.app.Application
import com.kabi.retrofitproject.app.di.appModule
import com.kabi.retrofitproject.data.di.networkModule
import io.kotzilla.sdk.analytics.koin.analytics
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                appModule
            )
            analytics()
        }
    }
}