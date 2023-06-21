package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.UpdateStoredDishUseCase
import javax.inject.Inject

class UpdateStoredDishUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository
) : UpdateStoredDishUseCase {

    override suspend fun invoke(dish: Dish) {
        try {
            localDatabaseRepository.updateStoredDish(dish = dish)
        } catch (e: java.lang.Exception) {
            println(e.stackTrace)
        }
    }
}