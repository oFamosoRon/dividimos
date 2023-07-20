package com.ofamosoron.dividimos.ui.composables.check

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.usecase.GetGuestByIdUseCase
import com.ofamosoron.dividimos.domain.usecase.GetStoredCheckByIdUseCase
import com.ofamosoron.dividimos.domain.usecase.SharedPreferencesUseCase
import com.ofamosoron.dividimos.util.SharedPreferencesHelper.SERVICE_FEE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckViewModel @Inject constructor(
    private val getGuestByIdUseCase: GetGuestByIdUseCase,
    private val getStoredCheckByIdUseCase: GetStoredCheckByIdUseCase,
    private val sharedPreferencesUseCase: SharedPreferencesUseCase
) : ViewModel() {

    private val _checkState = MutableStateFlow(CheckState())
    val checkState = _checkState.asStateFlow()

    init {
        sharedPreferencesUseCase.read(SERVICE_FEE, Float::class)?.let {
            _checkState.value = _checkState.value.copy(serviceFee = it)
        }
    }

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

            val checkIds = if (guests.filterNotNull().isEmpty()) {
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
