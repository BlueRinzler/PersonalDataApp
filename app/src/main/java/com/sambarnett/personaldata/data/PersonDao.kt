package com.sambarnett.personaldata.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {


    //Query to get individual people as Flow
    @Query("SELECT * FROM personInfo WHERE id = :id")
    fun observePersonByID(id: Int) : Flow<Person>

    //Query to get list of people Flow
    @Query("SELECT * FROM personInfo")
    fun observePersons() : Flow<List<Person>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(person: Person)

    @Update
    suspend fun update(person: Person)

    @Delete
    suspend fun delete(person: Person)

}