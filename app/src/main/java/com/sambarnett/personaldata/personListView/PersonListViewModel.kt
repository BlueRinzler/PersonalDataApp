package com.sambarnett.personaldata.personListView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sambarnett.personaldata.data.Person
import com.sambarnett.personaldata.data.PersonDao
import com.sambarnett.personaldata.data.PersonDao_Impl
import com.sambarnett.personaldata.data.PersonRepository
import java.lang.IllegalArgumentException


/**
 * ViewModel to show to List of Persons
 */

class PersonListViewModel(private val personRepository: PersonRepository) : ViewModel() {

    val allPersons: LiveData<List<Person>> = personRepository.allPersons
    val allPersonsDESC: LiveData<List<Person>> = personRepository.allPersonsDESC

}

/**
 * Boilerplate code for ViewModelFactory
 */
class PersonListViewModelFactory(private val personDao: PersonDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PersonListViewModel(personRepository = PersonRepository(personDao)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}