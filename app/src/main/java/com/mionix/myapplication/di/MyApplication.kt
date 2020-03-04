package com.mionix.myapplication.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            androidLogger(Level.DEBUG)
            // Android context
            androidContext(this@MyApplication)
            // modules
            modules(arrayListOf(retrofitModule,viewModelModule,repositoryModule))
        }
    }
}