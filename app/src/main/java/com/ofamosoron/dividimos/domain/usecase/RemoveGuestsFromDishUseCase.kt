package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Guest
import kotlinx.coroutines.flow.Flow

interface RemoveGuestsFromDishUseCase {
    operator fun invoke(guestList: List<Guest>): Flow<Boolean>
}
