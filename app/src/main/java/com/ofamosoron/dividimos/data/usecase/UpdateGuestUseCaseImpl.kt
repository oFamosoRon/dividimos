package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.UpdateGuestUseCase

class UpdateGuestUseCaseImpl(
    private val localDatabaseRepository: LocalDatabaseRepository
) : UpdateGuestUseCase {
    override suspend fun invoke(guest: Guest) =
        try {
            localDatabaseRepository.updateStoredGuest(guest = guest)
        } catch (e: java.lang.Exception) {
            print(e.stackTrace)
        }

}