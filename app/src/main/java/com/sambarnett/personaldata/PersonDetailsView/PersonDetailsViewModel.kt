package com.sambarnett.personaldata.PersonDetailsView

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sambarnett.personaldata.data.Person
import com.sambarnett.personaldata.data.PersonDao
import com.sambarnett.personaldata.data.PersonRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PersonDetailsViewModel(private val personRepository: PersonRepository): ViewModel() {


    /**
     * Function is used by PersonDetailsFragment to give all the proper info for a single Person
     */
    fun retrievePerson(id: Int): LiveData<Person> {
        return personRepository.getPerson(id)

    }
    /**
     * Function is by PersonDetailsFragment. Deletes current selected person
     */
    fun deletePerson(person: Person) {
        viewModelScope.launch {
            personRepository.deletePerson(person)
        }
    }

}

/**
 * Boilerplate code for ViewModelFactory
 */
class PersonDetailsViewModelFactory(private val personDao: PersonDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonDetailsViewModel(personRepository = PersonRepository(personDao)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}