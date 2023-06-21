package com.ofamosoron.dividimos.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guests_table")
data class GuestsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val uuid: String,
    val name: String,
    val email: String?,
    val phone: String?,
    val leftAt: String?,
    val arrivedAt: String?,
    val checkIds: List<String>,
)