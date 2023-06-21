package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.GetGuestByIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetGuestByIdUseCaseImpl(
    private val localDatabaseRepository: LocalDatabaseRepository
) : GetGuestByIdUseCase {
    override fun invoke(guestId: List<String>): Flow<List<Guest?>> = flow {
        try {
            val result = localDatabaseRepository.getGuestById(uuids = guestId)
            emit(result)
        } catch (e: java.lang.Exception) {
            println(e.stackTrace)
            emit(emptyList())
        }
    }
}