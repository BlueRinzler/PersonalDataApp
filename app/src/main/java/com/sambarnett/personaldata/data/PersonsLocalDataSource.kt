package com.sambarnett.personaldata.data


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * This inserts, pulls and saves data to Room via Dao, only accessed via the Repo
 */
class PersonsLocalDataSource(
    private val personDao: PersonDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {


    fun getPersonsStream(): Flow<List<Person>> {
        return personDao.observePersons()
    }

    fun getPersonStream(personId: Int): Flow<Person> {
        return personDao.observePersonByID(personId)
    }


    suspend fun getPersons(): List<Person> = withContext(ioDispatcher) {
        return@withContext personDao.getPersons()
    }

    suspend fun getPerson(id: Int): Person = withContext(ioDispatcher) {
        return@withContext personDao.getPersonByID(id)!!
    }


    suspend fun savePerson(person: Person) = withContext(ioDispatcher) {
        personDao.insert(person)
    }

    suspend fun deletePerson(person: Person) = withContext(ioDispatcher) {
        personDao.delete(person)
    }

    suspend fun deletePersons() = withContext(ioDispatcher) {
        personDao.deletePersons()
    }

    suspend fun updatePerson(person: Person) = withContext(ioDispatcher) {
        personDao.update(person)
    }


}


