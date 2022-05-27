package com.sambarnett.personaldata.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData

class PersonRepository(private val personDao: PersonDao) {

    val allPersons: LiveData<List<Person>> = personDao.getPersons().asLiveData()

    suspend fun insertPerson(person: Person) {
        personDao.insert(person)

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

    suspend fun updatePerson(person: Person) {
        personDao.update(person)
    }

    suspend fun deletePerson(person: Person) {
        personDao.delete(person)
    }

    fun getPerson(id: Int): LiveData<Person> {
       return personDao.getPerson(id).asLiveData()
    }
}
