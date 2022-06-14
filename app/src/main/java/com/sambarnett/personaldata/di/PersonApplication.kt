package com.sambarnett.personaldata.di

import android.app.Application
import com.sambarnett.personaldata.dataBaseModule
import com.sambarnett.personaldata.repoModule
import com.sambarnett.personaldata.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin


class PersonApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PersonApplication)
            modules(dataBaseModule, repoModule, viewModels)        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

}

