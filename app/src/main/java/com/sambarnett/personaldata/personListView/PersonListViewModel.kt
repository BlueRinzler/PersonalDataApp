package com.sambarnett.personaldata.personListView

import androidx.lifecycle.*
import com.sambarnett.personaldata.data.Person
import com.sambarnett.personaldata.data.PersonDao
import com.sambarnett.personaldata.data.PersonRepository

import com.sambarnett.personaldata.data.Result
import com.sambarnett.personaldata.personAddView.PersonAddViewModel
import kotlinx.coroutines.flow.*
import java.lang.IllegalArgumentException



sealed interface PersonUIState {
    data class Success(val person: Person): PersonUIState {
        object Error: PersonUIState
    }
}

/**
 * for UI State
 */
data class PersonListUIState(val personState: PersonUIState)




/**
 * ViewModel to show to List of Persons
 */

class PersonListViewModel(private val personRepository: PersonRepository) : ViewModel() {


    /**
     * Function to pull in people from repo
     */

    fun allPersons(): Flow<List<Person>> = personRepository.getPersonsStream()







}

/**
 * Boilerplate code for ViewModelFactory
 */
class PersonListViewModelFactory(private val personRepository: PersonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonListViewModel(personRepository) as T

        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}