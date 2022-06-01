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
class PersonRepository(
    private val personsLocalDataSource: PersonsLocalDataSource
) {

    /**
     * Below 2 functions get List and individual people from the LocalDataSource not as a flow
     */
    suspend fun getPersons(): List<Person> {
        return personsLocalDataSource.getPersons()
    }

    suspend fun getPerson(id: Int): Person {
        return personsLocalDataSource.getPerson(id)
    }

    /**
     * Below 2 functions get List and individual from the LocalDataSource people as a flow
     */
    fun getPersonsStream(): Flow<List<Person>> {
        return personsLocalDataSource.getPersonsStream()
    }

    fun getPersonStream(id: Int): Flow<Person> {
        return personsLocalDataSource.getPersonStream(id)
    }


    suspend fun savePerson(person: Person) {
        personsLocalDataSource.savePerson(person)
    }

    suspend fun updatePerson(person: Person) {
        personsLocalDataSource.updatePerson(person)
    }

    suspend fun deletePerson(person: Person) {
        personsLocalDataSource.deletePerson(person)

    }


    /**
     * Function used by addNewPerson, retrieves user inputted data and returns a person object
     */

    fun getPersonEntry(
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
    fun getUpdatedPerson(
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
