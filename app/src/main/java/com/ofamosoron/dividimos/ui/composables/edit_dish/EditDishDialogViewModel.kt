package com.ofamosoron.dividimos.ui.composables.edit_dish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ofamosoron.dividimos.domain.usecase.GetDishByIdUseCase
import com.ofamosoron.dividimos.domain.usecase.GetGuestByIdUseCase
import com.ofamosoron.dividimos.domain.usecase.UpdateGuestUseCase
import com.ofamosoron.dividimos.domain.usecase.UpdateStoredDishUseCase
import com.ofamosoron.dividimos.util.toMoney
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditDishDialogViewModel @Inject constructor(
    private val getDishByIdUseCase: GetDishByIdUseCase,
    private val getGuestByIdUseCase: GetGuestByIdUseCase,
    private val updateStoredDishUseCase: UpdateStoredDishUseCase,
    private val updateGuestUseCase: UpdateGuestUseCase
) : ViewModel(),
    GetDishByIdUseCase by getDishByIdUseCase {

    private val _state = MutableStateFlow(EditDishState())
    val state = _state.asStateFlow()

    fun clearState() {
        _state.value = EditDishState()
    }

    fun getState(dishUuid: String) = viewModelScope.launch {

        getDishByIdUseCase(uuid = dishUuid).flatMapLatest { dish ->
            val guestsIds = dish?.guests ?: emptyList()
            dish?.let { _state.value = _state.value.copy(dish = dish) }
            getGuestByIdUseCase(guestIds = guestsIds)
        }.collectLatest {
            val guests = it.filterNotNull()
            _state.value = _state.value.copy(guests = guests)
        }
    }

    fun saveChanges() = viewModelScope.launch {
        updateStoredDishUseCase(dish = _state.value.dish)
        _state.value.removedGuests.map {
            updateGuestUseCase(guest = it)
        }
    }

    fun onEvent(event: EditEvent) {
        when (event) {
            is EditEvent.Price -> {
                val updatedDish = _state.value.dish.copy(price = event.price.toMoney())
                _state.value = _state.value.copy(dish = updatedDish)
            }
            is EditEvent.Quantity -> {
                val updatedDish = _state.value.dish.copy(qnt = event.quantity)
                _state.value = _state.value.copy(dish = updatedDish)
            }
            is EditEvent.RemoveGuest -> {
                val removedGuest = _state.value.guests.find { it.uuid == event.guestUuid }

                val updatedList = _state.value.dish.guests.filter { it != event.guestUuid }
                val updatedDish = _state.value.dish.copy(guests = updatedList)
                val updatedGuestsList = _state.value.guests.filter { it.uuid != event.guestUuid }
                val removeGuestsList = removedGuest?.let { guest ->
                    val checksList = guest.checkIds.filter { it != _state.value.dish.checkId }
                    _state.value.removedGuests + guest.copy(checkIds = checksList)
                } ?: _state.value.removedGuests

                _state.value = _state.value.copy(
                    dish = updatedDish,
                    guests = updatedGuestsList,
                    removedGuests = removeGuestsList
                )
            }
        }
    }
}