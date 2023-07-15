package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.ClearDatabaseUseCase

class ClearDatabaseUseCaseImpl(
    private val localDatabaseRepository: LocalDatabaseRepository
): ClearDatabaseUseCase {
    override suspend fun invoke() =
        try {
            localDatabaseRepository.clearDatabase()
        } catch (e: java.lang.Exception) {
            println(e.stackTrace)
        }
}
