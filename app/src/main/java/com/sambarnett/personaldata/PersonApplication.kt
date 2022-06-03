package com.sambarnett.personaldata

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin


class PersonApplication: Application() {
//    val database: PersonRoomDataBase by lazy { PersonRoomDataBase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PersonApplication)
            modules(listOf(dataBaseModule, repoModule, viewModels))
        }
    }



}

