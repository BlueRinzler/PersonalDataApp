package com.sambarnett.personaldata.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class PersonRoomDataBase: RoomDatabase() {

    abstract fun personDao(): PersonDao

    companion object {

        @Volatile
        private var INSTANCE: PersonRoomDataBase? = null

        fun getDatabase(context: Context): PersonRoomDataBase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PersonRoomDataBase::class.java,
                    "person_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }

        }


    }


}