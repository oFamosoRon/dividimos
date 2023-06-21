package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Check
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.GetStoredCheckByIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetStoredCheckByIdUseCaseImpl(
    private val localDatabaseRepository: LocalDatabaseRepository
) : GetStoredCheckByIdUseCase {
    override fun invoke(checkIds: List<String>): Flow<List<Check?>> = flow {
        try {
            val result = localDatabaseRepository.getCheckById(uuids = checkIds)
            emit(result)
        } catch (e: java.lang.Exception) {
            println(e.stackTrace)
            emit(emptyList())
        }
    }
}