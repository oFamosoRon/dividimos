package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.UpdateStoredDishUseCase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateStoredDishUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository
) : UpdateStoredDishUseCase {

    override suspend fun invoke(dish: Dish) = flow {
        try {
            localDatabaseRepository.updateStoredDish(dish = dish)
            emit(true)
        } catch (e: java.lang.Exception) {
            println(e.stackTrace)
            emit(false)
        }
    }
}