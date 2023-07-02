package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.UpdateGuestUseCase
import kotlinx.coroutines.flow.flow

class UpdateGuestUseCaseImpl(
    private val localDatabaseRepository: LocalDatabaseRepository
) : UpdateGuestUseCase {
    override fun invoke(guest: Guest) = flow {
        try {
            localDatabaseRepository.updateStoredGuest(guest = guest)
            emit(true)
        } catch (e: java.lang.Exception) {
            print(e.stackTrace)
            emit(false)
        }
    }
}