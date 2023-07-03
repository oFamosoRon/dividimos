package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Check
import kotlinx.coroutines.flow.Flow

interface UpdateStoredCheckUseCase {
    operator fun invoke(check: Check): Flow<Boolean>
}