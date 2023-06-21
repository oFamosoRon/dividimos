package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Dish
import kotlinx.coroutines.flow.Flow

interface GetAllDishesUseCase {
    operator fun invoke(): Flow<List<Dish>>
}