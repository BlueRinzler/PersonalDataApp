package com.sambarnett.personaldata.data

import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    suspend fun getPersons(): List<Person>

    suspend fun getPerson(id: Int): Person

    fun getPersonsStream(): Flow<List<Person>>

    fun getPersonStream(id: Int): Flow<Person>

    suspend fun savePerson(person: Person)

    suspend fun updatePerson(person: Person)

    suspend fun deletePerson(person: Person)

    fun getPersonEntry(
        personFirstName: String,
        personSurName: String,
        personAge: String,
        personHeight: String,
        personWeight: String,
        personEyeColor: String
    ): Person

    /**
     * Function called by updatePerson, gets the new details inputted from the user input and returns a person objects
     */
    fun getUpdatedPerson(
        personId: Int, personFirstName: String, personSurName: String,
        personAge: String, personHeight: String,
        personWeight: String, personEyeColor: String
    ): Person


}