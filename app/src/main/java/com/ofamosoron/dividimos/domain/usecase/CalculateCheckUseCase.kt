package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Check
import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.models.Guest

interface CalculateCheckUseCase {
    operator fun invoke(guest: Guest, dish: Dish): Check
}
