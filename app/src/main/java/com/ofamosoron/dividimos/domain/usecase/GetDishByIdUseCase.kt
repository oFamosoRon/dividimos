package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Dish
import kotlinx.coroutines.flow.Flow

interface GetDishByIdUseCase {
    operator fun invoke(uuid: String): Flow<Dish?>
}