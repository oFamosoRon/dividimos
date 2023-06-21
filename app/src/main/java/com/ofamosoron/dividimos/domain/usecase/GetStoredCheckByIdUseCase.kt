package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Check
import kotlinx.coroutines.flow.Flow

interface GetStoredCheckByIdUseCase {
    operator fun invoke(checkIds: List<String>): Flow<List<Check?>>
}