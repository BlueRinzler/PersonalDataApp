package com.sambarnett.personaldata.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.Exception


/**
 * This repo pulls from the LocalDataSource
 */
class PersonRepositoryImpl(private val personDao: PersonDao) :
    PersonRepository {

    /**
     * Below 2 functions get List and individual people from the LocalDataSource not as a flow
     */
    override suspend fun getPersons(): List<Person> {
        return personDao.getPersons()
    }

    override suspend fun getPerson(id: Int): Person {
        return personDao.getPersonByID(id)!!
    }

    /**
     * Below 2 functions get List and individual from the LocalDataSource people as a flow
     */
    override fun getPersonsStream(): Flow<List<Person>> {
        return personDao.observePersons()
    }

    override fun getPersonStream(id: Int): Flow<Person> {
        return personDao.observePersonByID(id)
    }


    override suspend fun savePerson(person: Person) {
        personDao.insert(person)
    }

    override suspend fun updatePerson(person: Person) {
        personDao.update(person)
    }

    override suspend fun deletePerson(person: Person) {
        personDao.delete(person)

    }


    /**
     * Function used by addNewPerson, retrieves user inputted data and returns a person object
     */

    override fun getPersonEntry(
        personFirstName: String, personSurName: String, personAge: String, personHeight: String,
        personWeight: String, personEyeColor: String
    ): Person {
        return Person(
            personFirstName = personFirstName,
            personSurName = personSurName,
            personAge = personAge.toInt(),
            personHeight = personHeight.toDouble(),
            personWeight = personWeight.toDouble(),
            personEyeColor = personEyeColor

        )
    }

    /**
     * Function called by updatePerson, gets the new details inputted from the user input and returns a person objects
     */
    override fun getUpdatedPerson(
        personId: Int, personFirstName: String, personSurName: String,
        personAge: String, personHeight: String,
        personWeight: String, personEyeColor: String
    ): Person {
        return Person(
            id = personId,
            personFirstName = personFirstName,
            personSurName = personSurName,
            personAge = personAge.toInt(),
            personHeight = personHeight.toDouble(),
            personWeight = personWeight.toDouble(),
            personEyeColor = personEyeColor
        )
    }

}
