package com.ofamosoron.dividimos.domain.usecase

import com.ofamosoron.dividimos.domain.models.Guest

interface UpdateGuestUseCase {
    suspend operator fun invoke(guest: Guest)
}