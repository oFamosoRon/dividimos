package com.ofamosoron.dividimos.ui.composables.guest_dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.usecase.StoreGuestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NewGuestViewModel @Inject constructor(
    private val storeGuestUseCase: StoreGuestUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NewGuestState())
    val state = _state.asStateFlow()

    fun onEvent(event: NewGuestScreenEvent) {
        when (event) {
            is NewGuestScreenEvent.AddNewGuest -> handleAddNewGuestEvent()
            is NewGuestScreenEvent.OnNameChanged -> handleOnNameChanged(event.name)
            is NewGuestScreenEvent.ClearState -> handClearStateEvent()
        }
    }

    private fun handleAddNewGuestEvent() = viewModelScope.launch {
        storeGuestUseCase(guest = _state.value.guest).collectLatest { isStored ->
            _state.value = _state.value.copy(isCreated = isStored)
        }
    }

    private fun handleOnNameChanged(name: String) {
        val newGuest = if (_state.value.guest.uuid.isBlank()) {
            Guest(name = name, uuid = UUID.randomUUID().toString())
        } else {
            _state.value.guest.copy(name = name)
        }
        _state.value = _state.value.copy(guest = newGuest)
    }

    private fun handClearStateEvent() {
        _state.value = NewGuestState()
    }
}