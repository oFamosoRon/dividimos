package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Check
import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.usecase.CalculateCheckUseCase
import com.ofamosoron.dividimos.util.capitalizeFirst
import com.ofamosoron.dividimos.util.toMoney
import java.util.UUID

class CalculateCheckUseCaseImpl: CalculateCheckUseCase {
    override fun invoke(guest: Guest, dish: Dish): Check {
        val dishTotalPrice = dish.price.cents * dish.qnt
        val dishPartitionPrice = dishTotalPrice / dish.guests.size

        return Check(
            dishName = dish.name.capitalizeFirst(),
            total = dishPartitionPrice.toMoney(),
            uuid = UUID.randomUUID().toString(),
        )
    }
}
