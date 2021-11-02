package com.dts.gym_manager

import android.app.Application
import com.dts.gym_manager.di.app
import com.dts.gym_manager.di.storage
import com.dts.gym_manager.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class GMApp: Application() {

    override fun onCreate() {
        super.onCreate()
        setupDI()
        Timber.plant(Timber.DebugTree())
    }

    private fun setupDI() {
        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@GMApp)
            modules(app, storage, viewModels)
        }
    }
}
