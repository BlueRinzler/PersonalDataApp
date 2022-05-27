package com.sambarnett.personaldata.PersonListView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sambarnett.personaldata.data.Person
import com.sambarnett.personaldata.data.PersonDao
import com.sambarnett.personaldata.data.PersonRepository
import java.lang.IllegalArgumentException


/**
 * ViewModel to show to List of Persons
 */

class PersonListViewModel(private val personRepository: PersonRepository) : ViewModel() {


    val allPersons: LiveData<List<Person>> = personRepository.allPersons
}

data class PersonViewState(val persons: List<Person>)

val viewState: LiveData<PersonViewState> = MutableLiveData()


enum class SortOrder {
    DESC, ASC,
}


//    fun onSortOrderChanged(sortOrder: SortOrder) {
//        when (sortOrder) {
//            is DESC -> {
//                viewState.value = viewState.value.copy(
//                    persons = PersonRepository.allPersonsDESC()
//                )
//            }
//
//        }
//    }


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