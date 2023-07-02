package com.ofamosoron.dividimos.ui.composables.guest_dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.usecase.StoreGuestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GuestDialogViewModel @Inject constructor(
    private val storeGuestUseCase: StoreGuestUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GuestDialogState())
    val state = _state.asStateFlow()

    fun onEvent(event: GuestDialogEvent) {
        when (event) {
            is GuestDialogEvent.AddNewGuest -> handleAddNewGuestEvent()
            is GuestDialogEvent.OnNameChanged -> handleOnNameChanged(event.name)
        }
    }

    private fun handleAddNewGuestEvent() = viewModelScope.launch {
        storeGuestUseCase(guest = _state.value.guest).collect()
    }


    private fun handleOnNameChanged(name: String) {
        val newGuest = if (_state.value.guest.uuid.isBlank()) {
            Guest(name = name, uuid = UUID.randomUUID().toString())
        } else {
            _state.value.guest.copy(name = name)
        }
        _state.value = _state.value.copy(guest = newGuest)
    }
}