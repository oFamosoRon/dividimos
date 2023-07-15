package com.ofamosoron.dividimos.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checks_table")
data class CheckEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val total: Long,
    val uuid: String,
    val dishName: String
)
