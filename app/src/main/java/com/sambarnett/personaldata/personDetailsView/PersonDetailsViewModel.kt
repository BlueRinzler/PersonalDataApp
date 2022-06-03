package com.sambarnett.personaldata.personDetailsView

import androidx.lifecycle.*
import com.sambarnett.personaldata.data.Person
import com.sambarnett.personaldata.data.PersonRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PersonDetailsViewModel(private val personRepositoryImpl: PersonRepositoryImpl): ViewModel() {




    /**
     * Function is used by PersonDetailsFragment to give all the proper info for a single Person
     */
    fun retrievePerson(id: Int): Flow<Person> {
        return personRepositoryImpl.getPersonStream(id)

    }
    /**
     * Function is by PersonDetailsFragment. Deletes current selected person
     */
    fun deletePerson(person: Person) {
        viewModelScope.launch {
            personRepositoryImpl.deletePerson(person)
        }
    }

}

/**
 * Boilerplate code for ViewModelFactory
 */
class PersonDetailsViewModelFactory(private val personRepositoryImpl: PersonRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonDetailsViewModel(personRepositoryImpl) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}