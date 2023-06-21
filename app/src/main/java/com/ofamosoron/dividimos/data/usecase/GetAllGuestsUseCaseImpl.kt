package com.ofamosoron.dividimos.data.usecase

import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.GetAllGuestsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllGuestsUseCaseImpl @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository
) : GetAllGuestsUseCase {
    override fun invoke(): Flow<List<Guest>> = flow {
        try {
            val result = localDatabaseRepository.getAllGuests()
            emit(result)
        } catch (e: java.lang.Exception) {
            println(e.stackTrace)
            emit(emptyList())
        }
    }

}
