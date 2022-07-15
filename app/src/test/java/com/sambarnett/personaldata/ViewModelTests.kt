package com.sambarnett.personaldata

import org.junit.Test
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sambarnett.personaldata.data.PersonRepositoryImpl
import com.sambarnett.personaldata.personAddView.PersonAddViewModel

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(AndroidJUnit4::class)
class ViewModelTests {

    lateinit var personRepositoryImpl: PersonRepositoryImpl

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun addPerson() {
        val viewModel = PersonAddViewModel(personRepositoryImpl)
        viewModel.addNewPerson("Sam", "Barnett", "22", "177",
                                "200", "Blue")
    }

}