package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Dish

interface UpdateChecksUseCase {
    suspend operator fun invoke(dish: Dish)
}
