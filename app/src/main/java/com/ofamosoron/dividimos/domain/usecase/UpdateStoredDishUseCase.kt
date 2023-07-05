package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Dish
import kotlinx.coroutines.flow.Flow

interface UpdateStoredDishUseCase {
    suspend operator fun invoke(dish: Dish): Flow<Boolean>
}