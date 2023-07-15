package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Check
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.StoreCheckUseCase

class StoreCheckUseCaseImpl(
    private val localDatabaseRepository: LocalDatabaseRepository
): StoreCheckUseCase {
    override suspend fun invoke(check: Check) {
        try {
            localDatabaseRepository.storeNewCheck(check = check)
        } catch (e: java.lang.Exception) {
            println(e.stackTrace)
        }
    }
}
