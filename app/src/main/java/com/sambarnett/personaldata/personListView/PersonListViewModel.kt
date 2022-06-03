package com.sambarnett.personaldata.personListView

import androidx.lifecycle.*
import com.sambarnett.personaldata.data.Person
import com.sambarnett.personaldata.data.PersonRepositoryImpl

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException



/**
 * for UI State
 */
//data class PersonListUIState(val personState: PersonUIState)


/**
 * ViewModel to show to List of Persons
 */

class PersonListViewModel(private val personRepositoryImpl: PersonRepositoryImpl) : ViewModel() {

    /**
     * Function to pull in people from repo
     */
    fun allPersons(): Flow<List<Person>> = personRepositoryImpl.getPersonsStream()

    init {
        viewModelScope.launch {
            personRepositoryImpl.getPersonsStream().collectLatest {


            }
        }

    }



}

/**
 * Boilerplate code for ViewModelFactory
 */
class PersonListViewModelFactory(private val personRepositoryImpl: PersonRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonListViewModel(personRepositoryImpl) as T

        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}