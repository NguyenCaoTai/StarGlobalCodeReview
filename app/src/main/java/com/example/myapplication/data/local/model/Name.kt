package com.example.myapplication.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Name(
    val first: String,
    val last: String,
    val title: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "nameId")
    var id: Long = 0
}