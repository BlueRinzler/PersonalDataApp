package com.sambarnett.personaldata.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.NumberFormatException


@Entity(tableName = "personInfo")
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "firstName")
    val personFirstName: String,
    @ColumnInfo(name = "surName")
    val personSurName: String,
    @ColumnInfo(name = "age")
    val personAge: Int,
    @ColumnInfo(name = "height")
    val personHeight: Double,
    @ColumnInfo(name = "weight")
    val personWeight: Double,
    @ColumnInfo(name = "eyeColor")
    val personEyeColor: String

)
