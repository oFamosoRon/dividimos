package com.ofamosoron.dividimos.ui.composables.editDish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ofamosoron.dividimos.domain.models.Check
import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.usecase.GetDishByIdUseCase
import com.ofamosoron.dividimos.domain.usecase.GetGuestByIdUseCase
import com.ofamosoron.dividimos.domain.usecase.GetStoredCheckByIdUseCase
import com.ofamosoron.dividimos.domain.usecase.RemoveGuestsFromDishUseCase
import com.ofamosoron.dividimos.domain.usecase.UpdateStoredCheckUseCase
import com.ofamosoron.dividimos.domain.usecase.UpdateStoredDishUseCase
import com.ofamosoron.dividimos.util.formatMoney
import com.ofamosoron.dividimos.util.toMoney
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@SuppressWarnings("LongParameterList")
class EditDishViewModel @AssistedInject constructor(
    @Assisted private val dishUuid: String,
    private val getDishByIdUseCase: GetDishByIdUseCase,
    private val getGuestByIdUseCase: GetGuestByIdUseCase,
    private val updateStoredDishUseCase: UpdateStoredDishUseCase,
    private val updateStoredCheckUseCase: UpdateStoredCheckUseCase,
    private val getStoredCheckByIdUseCase: GetStoredCheckByIdUseCase,
    private val removeGuestsFromDishUseCase: RemoveGuestsFromDishUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(EditDishState())
    val state = _state.asStateFlow()

    init {
        getState()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getState() = viewModelScope.launch {

        getDishByIdUseCase(uuid = dishUuid).flatMapLatest { dish ->
            val guestsIds = dish?.guests ?: emptyList()
            dish?.let { _state.value = _state.value.copy(dish = dish) }
            getGuestByIdUseCase(guestIds = guestsIds)
        }.collectLatest {
            val guests = it.filterNotNull()
            _state.value = _state.value.copy(guests = guests)
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
            EditEvent.ClearState -> handleClearStateEvent()
            EditEvent.Dismiss -> handleDismissEvent()
            EditEvent.SaveChanges -> handleSaveChangesEvent()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun handleSaveChangesEvent() = viewModelScope.launch {
        removeGuestsFromDishUseCase(_state.value.removedGuests)
        .flatMapLatest {
            updateStoredCheck(dish = _state.value.dish)
        }.flatMapLatest {
            updateStoredDishUseCase(dish = _state.value.dish)
        }.collectLatest {
            /* navigate back */
            _state.value = _state.value.copy(isEdited = true)
        }
    }

    private fun handleDismissEvent() {
        _state.value = _state.value.copy(isEdited = true)
    }

    private fun handleClearStateEvent() {
        _state.value = EditDishState()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun updateStoredCheck(dish: Dish) =
        getStoredCheckByIdUseCase(listOf(dish.checkId)).flatMapLatest { check ->
            val updatedCheck = check.firstNotNullOfOrNull {
                //prevent division by zero
                val divider = if (dish.guests.isEmpty()) 1 else dish.guests.size
                it?.copy(
                    total = ((dish.price.cents * dish.qnt) / divider.toFloat())
                        .formatMoney()
                        .toMoney(),
                )
            }
            updateStoredCheckUseCase(updatedCheck ?: Check())
        }


    @AssistedFactory
    interface Factory {
        fun create(dishUuid: String): EditDishViewModel
    }

    companion object {
        fun provideEditDishViewModelFactory(
            factory: Factory,
            dishUuid: String
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(dishUuid = dishUuid) as T
                }
            }
        }
    }
}
