package com.example.myapplication.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val ssn: String,
    @Embedded val name: Name,
    @Embedded val location: Location,
    val phone: String,
    val picture: String,
    val dateOfBirth: Long
)