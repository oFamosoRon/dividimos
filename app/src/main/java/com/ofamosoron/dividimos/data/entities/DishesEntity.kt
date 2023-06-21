package com.ofamosoron.dividimos.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes_table")
data class DishesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val qnt: Int,
    val price: Long,
    val name: String,
    val uuid: String,
    val createdAt: String,
    val guests: List<String>, //list of guests uuid's
    val checkId: String,
)