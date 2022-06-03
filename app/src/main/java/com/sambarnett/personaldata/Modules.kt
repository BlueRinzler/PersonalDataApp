package com.sambarnett.personaldata


import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sambarnett.personaldata.data.*
import com.sambarnett.personaldata.personAddView.PersonAddViewModel
import com.sambarnett.personaldata.personDetailsView.PersonDetailsViewModel
import com.sambarnett.personaldata.personListView.PersonListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module
import kotlin.coroutines.coroutineContext


val dataBaseModule = module {

//    fun provideDataBase(application: Application): PersonRoomDataBase {
//        return Room.databaseBuilder(
//            application, PersonRoomDataBase::class.java, "person_database"
//        ).fallbackToDestructiveMigration().build()
//    }

//    fun provideDao(dataBase: PersonRoomDataBase): PersonDao {
//        return dataBase.personDao()
//    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            PersonRoomDataBase::class.java,
            "person_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    single { get<PersonRoomDataBase>().personDao() }
}


val repoModule = module {

    single<PersonRepository> { PersonRepositoryImpl(personDao = get()) }

}

val viewModels = module {

    viewModel { PersonListViewModel(personRepositoryImpl = get()) }
    viewModel { PersonAddViewModel(personRepositoryImpl = get()) }
    viewModel { PersonDetailsViewModel(personRepositoryImpl = get()) }
}



