package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Guest
import kotlinx.coroutines.flow.Flow

interface GetGuestByIdUseCase {
    operator fun invoke(guestIds: List<String>): Flow<List<Guest?>>
}