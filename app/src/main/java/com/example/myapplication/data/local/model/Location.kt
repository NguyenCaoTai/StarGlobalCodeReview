package com.example.myapplication.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    val city: String,
    val state: String,
    val street: String,
    val zip: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "locationId")
    var id: Long = 0
}