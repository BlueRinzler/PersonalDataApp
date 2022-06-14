package com.sambarnett.personaldata



import androidx.room.Room
import com.sambarnett.personaldata.data.*
import com.sambarnett.personaldata.personAddView.PersonAddViewModel
import com.sambarnett.personaldata.personDetailsView.PersonDetailsViewModel
import com.sambarnett.personaldata.personListView.PersonListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module



val dataBaseModule = module {

    single { get<PersonRoomDataBase>().personDao() }

    single {
        Room.databaseBuilder(
            androidApplication(),
            PersonRoomDataBase::class.java,
            "person_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}


val repoModule = module {

    single { PersonRepositoryImpl(get()) }

}

val viewModels = module {

    viewModel { PersonListViewModel(get()) }
    viewModel { PersonAddViewModel(get()) }
    viewModel { PersonDetailsViewModel(get()) }
}



