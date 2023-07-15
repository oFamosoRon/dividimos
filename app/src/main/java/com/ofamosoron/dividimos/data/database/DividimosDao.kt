package com.ofamosoron.dividimos.data.database

import androidx.room.Insert
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Query
import com.ofamosoron.dividimos.data.entities.CheckEntity
import com.ofamosoron.dividimos.data.entities.DishesEntity
import com.ofamosoron.dividimos.data.entities.GuestsEntity

@Dao
interface DividimosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewGuest(guest: GuestsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewDish(dish: DishesEntity)

    @Update
    suspend fun updateStoredDish(dish: DishesEntity)

    @Update
    suspend fun updateCheck(check: CheckEntity)

    @Update
    suspend fun updateGuest(guest: GuestsEntity)

    @Query("SELECT * FROM dishes_table ORDER BY name ASC")
    suspend fun getAllDishes(): List<DishesEntity>

    @Query("SELECT * FROM guests_table")
    suspend fun getAllGuests(): List<GuestsEntity>

    @Query("SELECT * FROM dishes_table WHERE uuid LIKE :uuid")
    suspend fun getDishById(uuid: String): DishesEntity

    @Query("SELECT * FROM guests_table WHERE uuid IN (:uuids)")
    suspend fun getGuestById(uuids: List<String>): List<GuestsEntity>

    @Query("SELECT * FROM checks_table WHERE uuid IN (:uuids)")
    suspend fun getCheckById(uuids: List<String>): List<CheckEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeNewCheck(check: CheckEntity)

    @Query("DELETE FROM checks_table")
    suspend fun clearChecksTable()

    @Query("DELETE FROM guests_table")
    suspend fun clearGuestsTable()

    @Query("DELETE FROM dishes_table")
    suspend fun clearDishesTable()
}
