package com.sambarnett.personaldata.personListView

import androidx.lifecycle.*
import com.sambarnett.personaldata.data.Person
import com.sambarnett.personaldata.data.PersonRepositoryImpl

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch




/**
 * for UI State
 */
sealed class PersonListUIState {
    data class State(val persons: List<Person>) : PersonListUIState()
}


/**
 * ViewModel to show to List of Persons
 */

class PersonListViewModel(private val personRepositoryImpl: PersonRepositoryImpl) : ViewModel() {

    private val _uiState = MutableStateFlow(PersonListUIState.State(emptyList()))
    val uiState: StateFlow<PersonListUIState> = _uiState

    /**
     * Function to pull in people from repo
     */
    fun allPersons(): Flow<List<Person>> = personRepositoryImpl.getPersonsStream()

    init {
        viewModelScope.launch {
            personRepositoryImpl.getPersonsStream().collectLatest {
                persons -> _uiState.value = PersonListUIState.State(persons)
            }
        }
    }
}
