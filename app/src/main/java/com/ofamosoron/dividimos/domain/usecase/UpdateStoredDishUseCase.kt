package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Dish

interface UpdateStoredDishUseCase {
    suspend operator fun invoke(dish: Dish)
}