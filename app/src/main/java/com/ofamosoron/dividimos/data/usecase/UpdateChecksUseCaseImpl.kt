package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.UpdateChecksUseCase

class UpdateChecksUseCaseImpl(
    private val localDatabaseRepository: LocalDatabaseRepository
) : UpdateChecksUseCase {
    override suspend operator fun invoke(dish: Dish) {
        val guests = localDatabaseRepository.getGuestById(dish.guests)
    }
}
