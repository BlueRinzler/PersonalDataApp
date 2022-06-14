package com.sambarnett.personaldata

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules


@RunWith(AndroidJUnit4::class)

class PersonKoinTest: KoinTest {

    @Test
    fun verifyKoin() {
        koinApplication {
            modules(dataBaseModule, repoModule, viewModels)
            checkModules()
        }
    }


}