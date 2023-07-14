package com.ofamosoron.dividimos.data.repository

import com.ofamosoron.dividimos.data.database.DividimosDao
import com.ofamosoron.dividimos.data.entities.CheckEntity
import com.ofamosoron.dividimos.data.entities.DishesEntity
import com.ofamosoron.dividimos.data.entities.GuestsEntity
import com.ofamosoron.dividimos.domain.models.Check
import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.util.capitalizeFirst
import com.ofamosoron.dividimos.util.toMoney
import kotlinx.datetime.toInstant
import javax.inject.Inject

class LocalDatabaseRepositoryImpl @Inject constructor(
    private val dao: DividimosDao
) : LocalDatabaseRepository {

    override suspend fun addDishToDatabase(dish: Dish) =
        dao.addNewDish(dish.toDatabaseEntity())

    override suspend fun getDishById(uuid: String): Dish =
        dao.getDishById(uuid = uuid).toDomain()

    override suspend fun getGuestById(uuids: List<String>): List<Guest> =
        dao.getGuestById(uuids = uuids).map { it.toDomain() }

    override suspend fun addGuestToDatabase(guest: Guest) =
        dao.addNewGuest(guest.toDatabaseEntity())

    override suspend fun updateStoredDish(dish: Dish) =
        dao.updateStoredDish(dish = dish.toDatabaseEntity())

    override suspend fun getAllDishes(): List<Dish> = dao.getAllDishes().map { it.toDomain() }

    override suspend fun getAllGuests(): List<Guest> = dao.getAllGuests().map { it.toDomain() }

    override suspend fun getCheckById(uuids: List<String>): List<Check?> =
        dao.getCheckById(uuids = uuids).map { it.toDomain() }

    override suspend fun updateStoredCheck(check: Check) {
       dao.updateCheck(check = check.toEntity())
    }

    override suspend fun updateStoredGuest(guest: Guest) {
        dao.updateGuest(guest = guest.toDatabaseEntity())
    }

    override suspend fun storeNewCheck(check: Check) = dao.storeNewCheck(check.toEntity())

    override suspend fun clearDatabase() {
        dao.clearGuestsTable()
        dao.clearDishesTable()
        dao.clearChecksTable()
    }

    private fun CheckEntity.toDomain(): Check =
        Check(
            id = this.id,
            total = this.total.toMoney(),
            uuid = this.uuid,
            dishName = this.dishName.capitalizeFirst(),
        )

    private fun Check.toEntity(): CheckEntity =
        CheckEntity(
            id = this.id,
            total = this.total.cents,
            uuid = this.uuid,
            dishName = this.dishName.capitalizeFirst(),
        )

    private fun DishesEntity.toDomain(): Dish =
        Dish(
            id = this.id,
            qnt = this.qnt,
            uuid = this.uuid,
            name = this.name.capitalizeFirst(),
            price = this.price.toMoney(),
            guests = this.guests,
            createdAt = this.createdAt.toInstant(),
            checkId = this.checkId
        )

    private fun GuestsEntity.toDomain(): Guest =
        Guest(
            id = this.id,
            uuid = this.uuid,
            name = this.name.capitalizeFirst(),
            email = this.email,
            phone = this.phone,
            checkIds = this.checkIds.filter { it.isNotBlank()}
        )

    private fun Dish.toDatabaseEntity(): DishesEntity =
        DishesEntity(
            id = this.id,
            qnt = this.qnt,
            uuid = this.uuid,
            name = this.name.capitalizeFirst(),
            price = this.price.cents,
            guests = this.guests,
            createdAt = this.createdAt.toString(),
            checkId = this.checkId
        )

    private fun Guest.toDatabaseEntity(): GuestsEntity =
        GuestsEntity(
            id = this.id,
            uuid = this.uuid,
            name = this.name.capitalizeFirst(),
            email = this.email,
            phone = this.phone,
            leftAt = this.leftAt.toString(),
            checkIds = this.checkIds,
            arrivedAt = this.arrivedAt.toString(),
        )
}
