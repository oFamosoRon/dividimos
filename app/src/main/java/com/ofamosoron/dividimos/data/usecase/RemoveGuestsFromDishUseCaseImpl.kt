package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.RemoveGuestsFromDishUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveGuestsFromDishUseCaseImpl(
    private val localDatabaseRepository: LocalDatabaseRepository
) : RemoveGuestsFromDishUseCase {
    override fun invoke(guestList: List<Guest>): Flow<Boolean> = flow {
        try {
            guestList.forEach { guest ->
                localDatabaseRepository.updateStoredGuest(guest = guest)
            }
            emit(true)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            emit(false)
        }
    }
}