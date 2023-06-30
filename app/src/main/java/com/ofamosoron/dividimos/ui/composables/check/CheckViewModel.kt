package com.ofamosoron.dividimos.ui.composables.check

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.usecase.GetGuestByIdUseCase
import com.ofamosoron.dividimos.domain.usecase.GetStoredCheckByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckViewModel @Inject constructor(
    private val getGuestByIdUseCase: GetGuestByIdUseCase,
    private val getStoredCheckByIdUseCase: GetStoredCheckByIdUseCase,
) : GetGuestByIdUseCase by getGuestByIdUseCase,
    ViewModel() {

    private val _checkState = MutableStateFlow(CheckState())
    val checkState = _checkState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getState(guestId: String) = viewModelScope.launch {
        getGuestByIdUseCase(guestIds = listOf(guestId)).flatMapLatest { guests ->
            guests.filterNotNull().let {
                val updatedGuest = if (it.isEmpty()) {
                    Guest()
                } else {
                    it.first()
                }
                _checkState.value = _checkState.value.copy(guest = updatedGuest)
            }

            val checkIds = if(guests.filterNotNull().isEmpty()) {
                emptyList<String>()
            } else {
                guests.first()?.checkIds ?: emptyList()
            }

            getStoredCheckByIdUseCase(checkIds = checkIds)
        }.collectLatest { checks ->
            _checkState.value = _checkState.value.copy(checks = checks.filterNotNull())
        }
    }
}