package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.StoreGuestUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoreGuestUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository
) : StoreGuestUseCase {
    override fun invoke(guest: Guest): Flow<Boolean> = flow {
        try {
            localDatabaseRepository.addGuestToDatabase(guest = guest)
            emit(true)
        } catch (e: java.lang.Exception) {
            println(e.stackTrace)
            emit(false)
        }
    }
}
