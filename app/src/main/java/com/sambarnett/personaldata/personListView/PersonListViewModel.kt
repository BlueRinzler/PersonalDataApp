package com.sambarnett.personaldata.personListView

import androidx.core.app.NotificationCompat.getPeople
import androidx.lifecycle.*
import com.sambarnett.personaldata.data.Person
import com.sambarnett.personaldata.data.PersonRepositoryImpl

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


/**
 * ViewModel to show to List of Persons
 */

class PersonListViewModel(private val personRepositoryImpl: PersonRepositoryImpl) : ViewModel() {

    private val _uiState = MutableStateFlow(PersonListUIState())
    val uiState: StateFlow<PersonListUIState> = _uiState.asStateFlow()

    init {
        getPeople()
    }

    private fun getPeople() {
        viewModelScope.launch {
            personRepositoryImpl.getPersonsStream()
                .collect { result ->
                    _uiState.update {
                        it.copy(people = result)
                    }
                }
        }
    }
}

data class PersonListUIState(
    val people: List<Person> = emptyList()
)
