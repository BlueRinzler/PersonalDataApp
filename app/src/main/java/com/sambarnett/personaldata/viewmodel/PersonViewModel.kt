package com.sambarnett.personaldata.viewmodel

import androidx.lifecycle.*
import com.sambarnett.personaldata.data.Person
import com.sambarnett.personaldata.data.PersonDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PersonViewModel(private val personDao: PersonDao) : ViewModel() {


    val allPersonsASC: LiveData<List<Person>> = personDao.getPersonsASC().asLiveData()

    val allPersonsDESC: LiveData<List<Person>> = personDao.getPersonsDESC().asLiveData()


    /**
     * Using the Dao to insert a person the database via launch,private because this is called via addPerson function
     */
    private fun insertPerson(person: Person) {
        viewModelScope.launch {
            personDao.insert(person)
        }
    }

    /**
     * Function used by addNewPerson, retrieves user inputted data and returns a person object
     */
    private fun getPersonEntry(
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
     * Function used by AddPersonFragment to add a new person to the database, retrieves a person object from getPersonEntry
     * and uses insertPerson to inject into the database
     */
    fun addNewPerson(
        personFirstName: String, personSurName: String, personAge: String, personHeight: String,
        personWeight: String, personEyeColor: String
    ) {
        //calling getPersonEntry
        val newPerson = getPersonEntry(
            personFirstName,
            personSurName,
            personAge,
            personHeight,
            personWeight,
            personEyeColor
        )
        //calling insertPerson
        insertPerson(newPerson)
    }

    /**
     * Function used by AddPersonFragment via addNewPerson. To see if user input is blank, returns true or false
     */
    fun isPersonValid(
        personFirstName: String, personSurName: String, personAge: String, personWeight: String,
        personEyeColor: String, personHeight: String
    ): Boolean {
        if (personFirstName.isBlank() || personSurName.isBlank() || personAge.isBlank() ||
            personHeight.isBlank() || personWeight.isBlank() || personEyeColor.isBlank()
        ) {
            return false
        }
        return true
    }

    /**
     * Using the Dao to update a persons details via launch, private because updatePerson calls it
     */
    private fun updatePerson(person: Person) {
        viewModelScope.launch {
            personDao.update(person)
        }
    }


    /**
     * Function called by updatePerson, gets the new details inputted from the user input and returns a person objects
     */
    private fun getUpdatedPerson(
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

    /**
     * Function is by AddPersonFragment. retrieves a person object from getPersonEntry
     * and uses update to update said object into the database
     */
    fun updatePerson(
        personId: Int, personFirstName: String, personSurName: String,
        personAge: String, personHeight: String,
        personWeight: String, personEyeColor: String
    ) {
        val updatedPerson = getUpdatedPerson(
            personId,
            personFirstName,
            personSurName,
            personAge,
            personHeight,
            personWeight,
            personEyeColor
        )
        updatePerson(updatedPerson)
    }

    /**
     * Function is by PersonDetailsFragment. Deletes current selected person
     */
    fun deletePerson(person: Person) {
        viewModelScope.launch {
            personDao.delete(person)
        }
    }

    /**
     * Function is used by PersonDetailsFragment to give all the proper info for a single Person
     */

    fun retrievePerson(id: Int): LiveData<Person> {
        return personDao.getPerson(id).asLiveData()

    }

}

/**
 * Boilerplate code for ViewModelFactory
 */
class PersonViewModelFactory(private val personDao: PersonDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonViewModel(personDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}