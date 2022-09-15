package com.sambarnett.personaldata.data

import kotlinx.coroutines.flow.Flow


/**
 * This repo pulls from the LocalDataSource
 */
class PersonRepositoryImpl(private val personDao: PersonDao) :
    PersonRepository {


    /**
     * Below 2 functions get List and individual from the LocalDataSource people as a flow
     */
    override suspend fun getPersonsStream(): Flow<List<Person>> {
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
