package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Guest
import kotlinx.coroutines.flow.Flow

interface StoreGuestUseCase {
    operator fun invoke(guest: Guest): Flow<Boolean>
}