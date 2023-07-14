package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Guest
import kotlinx.coroutines.flow.Flow

interface GetAllGuestsUseCase {
    operator fun invoke(): Flow<List<Guest>>
}
