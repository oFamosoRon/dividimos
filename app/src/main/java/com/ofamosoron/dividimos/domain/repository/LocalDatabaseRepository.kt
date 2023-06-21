package com.ofamosoron.dividimos.domain.repository

import com.ofamosoron.dividimos.domain.models.Check
import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.models.Guest

interface LocalDatabaseRepository {
    suspend fun getAllDishes(): List<Dish>
    suspend fun getAllGuests(): List<Guest>
    suspend fun addDishToDatabase(dish: Dish)
    suspend fun getDishById(uuid: String): Dish
    suspend fun getGuestById(uuids: List<String>): List<Guest>
    suspend fun addGuestToDatabase(guest: Guest)
    suspend fun updateStoredDish(dish: Dish)
    suspend fun storeNewCheck(check: Check)
    suspend fun getCheckById(uuids: List<String>): List<Check?>
    suspend fun updateStoredCheck(check: Check)
    suspend fun updateStoredGuest(guest: Guest)
    suspend fun clearDatabase()
}