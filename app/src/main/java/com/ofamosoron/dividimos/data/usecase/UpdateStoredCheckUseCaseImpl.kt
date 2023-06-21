package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Check
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.UpdateStoredCheckUseCase

class UpdateStoredCheckUseCaseImpl(
    private val localDatabaseRepository: LocalDatabaseRepository
): UpdateStoredCheckUseCase {
    override suspend fun invoke(check: Check) =
        try {
            localDatabaseRepository.updateStoredCheck(check = check)
        } catch (e: java.lang.Exception){
            println(e.stackTrace)
        }
}