package com.sambarnett.personaldata.personAddView

import androidx.lifecycle.*
import com.sambarnett.personaldata.data.Person
import com.sambarnett.personaldata.data.PersonDao
import com.sambarnett.personaldata.data.PersonRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PersonAddViewModel(private val personRepository: PersonRepository) : ViewModel() {

    private val INPUT_FIRST_NAME = "Enter a First Name"


    /**
     * Function is used by PersonDetailsFragment to give all the proper info for a single Person
     */
    fun retrievePerson(id: Int): LiveData<Person> {
        return personRepository.getPerson(id)

    }


    /**
     * Using the personRepository to insert a person the database via launch,private because this is called via addPerson function
     */
    private fun insertPerson(person: Person) {
        viewModelScope.launch {
            personRepository.insertPerson(person)
        }
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
        val newPerson = personRepository.getPersonEntry(
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
        return false
    }

    fun firstNameValid(personFirstName: String): String {
        if (personFirstName.isBlank()) {
            return "Please Enter a First Name"
        }
        return personFirstName
    }


    /**
     * Using the Dao to update a persons details via launch, private because updatePerson calls it
     */
    private fun updatePerson(person: Person) {
        viewModelScope.launch {
            personRepository.updatePerson(person)
        }
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
        val updatedPerson = personRepository.getUpdatedPerson(
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

}


/**
 * Boilerplate code for ViewModelFactory
 */
class PersonAddViewModelFactory(private val personDao: PersonDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonAddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonAddViewModel(personRepository = PersonRepository(personDao)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
