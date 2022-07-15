package com.sambarnett.personaldata.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class DataBaseTest: TestCase() {


    private lateinit var db: PersonRoomDataBase
    private lateinit var dao: PersonDao

    @Before
    fun makeDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PersonRoomDataBase::class.java).build()
        dao = db.personDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun writeAndReadPerson() = runBlocking{
        val person = Person(1, "Sam", "Barnett", 26,
            192.4, 177.0, "Blue")
        dao.insert(person)
        val persons = dao.getPersons().toList()
        assertEquals(1, persons.size)
    }
}