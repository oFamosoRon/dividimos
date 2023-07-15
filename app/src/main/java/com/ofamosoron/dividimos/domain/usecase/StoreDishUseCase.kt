package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Dish

interface StoreDishUseCase {
    suspend operator fun invoke(dish: Dish)
}
