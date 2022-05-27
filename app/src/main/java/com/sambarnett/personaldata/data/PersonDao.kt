package com.sambarnett.personaldata.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {


    //Query to get individual people for second fragment
    @Query("SELECT * FROM personInfo WHERE id = :id")
    fun getPerson(id: Int) : Flow<Person>
    //Query to get list of people for main fragment
    //Query to get list of people for main fragment
    @Query("SELECT * FROM personInfo")
    fun getPersons() : Flow<List<Person>>

    @Query("SELECT * FROM personInfo ORDER BY firstName ASC")
    fun getPersonsASC() : Flow<List<Person>>

    @Query("SELECT * FROM personInfo ORDER BY firstName DESC")
    fun getPersonsDESC() : Flow<List<Person>>

    @Query("SELECT * FROM personInfo ORDER BY surName ASC")
    fun getPersonsSurNameASC() : Flow<List<Person>>

    @Query("SELECT * FROM personInfo ORDER BY surName DESC")
    fun getPersonsSurNameDESC() : Flow<List<Person>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(person: Person)

    @Update
    suspend fun update(person: Person)

    @Delete
    suspend fun delete(person: Person)


}