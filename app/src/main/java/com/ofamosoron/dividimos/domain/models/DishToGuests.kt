package com.ofamosoron.dividimos.domain.models

data class DishToGuests(
    val dishUuid: String,
    val dishName: String,
    val guests: List<Guest>
)

