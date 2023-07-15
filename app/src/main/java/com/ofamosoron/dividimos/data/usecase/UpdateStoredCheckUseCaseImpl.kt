package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Check
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.UpdateStoredCheckUseCase
import kotlinx.coroutines.flow.flow

class UpdateStoredCheckUseCaseImpl(
    private val localDatabaseRepository: LocalDatabaseRepository
): UpdateStoredCheckUseCase {
    override fun invoke(check: Check) = flow {
        try {
            localDatabaseRepository.updateStoredCheck(check = check)
            emit(true)
        } catch (e: java.lang.Exception){
            println(e.stackTrace)
            emit(false)
        }
    }
}
