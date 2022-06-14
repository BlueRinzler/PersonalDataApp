package com.sambarnett.personaldata.personAddView

import androidx.lifecycle.*
import com.sambarnett.personaldata.data.Person
import com.sambarnett.personaldata.data.PersonRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


private val TAG: String = PersonAddViewModel::class.java.simpleName

class PersonAddViewModel(private val personRepositoryImpl: PersonRepositoryImpl) : ViewModel() {

    /**
     * Function is used by PersonDetailsFragment to give all the proper info for a single Person
     */
    fun retrievePerson(id: Int): Flow<Person> {
        return personRepositoryImpl.getPersonStream(id)

    }


    /**
     * Using the personRepository to insert a person the database via launch,private because this is called via addPerson function
     */
    private fun insertPerson(person: Person) {
        viewModelScope.launch {
            personRepositoryImpl.savePerson(person)
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
        val newPerson = personRepositoryImpl.getPersonEntry(
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
            personRepositoryImpl.updatePerson(person)
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
        val updatedPerson = personRepositoryImpl.getUpdatedPerson(
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


