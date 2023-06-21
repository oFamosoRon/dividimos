package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.GetDishByIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDishByIdUseCaseImpl(
    private val localDatabaseRepository: LocalDatabaseRepository
): GetDishByIdUseCase {
    override fun invoke(uuid: String): Flow<Dish?> = flow {
        try {
            val result = localDatabaseRepository.getDishById(uuid = uuid)
            emit(result)
        } catch (e: java.lang.Exception) {
            println(e.stackTrace)
            emit(null)
        }
    }

}