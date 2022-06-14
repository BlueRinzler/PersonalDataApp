package com.sambarnett.personaldata.data


import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class PersonRoomDataBase : RoomDatabase() {

    abstract fun personDao(): PersonDao

}