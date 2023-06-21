package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Check

interface UpdateStoredCheckUseCase {
    suspend operator fun invoke(check: Check)
}