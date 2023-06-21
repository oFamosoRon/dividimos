package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.StoreDishUseCase
import javax.inject.Inject

class StoreDishUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository
) : StoreDishUseCase {

    override suspend fun invoke(dish: Dish) {
        try {
            localDatabaseRepository.addDishToDatabase(dish = dish)
        } catch (e: java.lang.Exception) {
            println(e.stackTrace)
        }
    }
}