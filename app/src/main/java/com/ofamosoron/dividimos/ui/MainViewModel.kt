package com.ofamosoron.dividimos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.usecase.*
import com.ofamosoron.dividimos.util.formatMoney
import com.ofamosoron.dividimos.util.toMoney
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val storeGuestUseCase: StoreGuestUseCase,
    private val getDishByIdUseCase: GetDishByIdUseCase,
    private val getAllDishesUseCase: GetAllDishesUseCase,
    private val getGuestByIdUseCase: GetGuestByIdUseCase,
    private val getAllGuestsUseCase: GetAllGuestsUseCase,
    private val clearDatabaseUseCase: ClearDatabaseUseCase,
    private val updateStoredGuestUseCase: UpdateGuestUseCase,
    private val calculateCheckUseCase: CalculateCheckUseCase,
    private val updateStoredDishUseCase: UpdateStoredDishUseCase,
    private val updateStoredCheckUseCase: UpdateStoredCheckUseCase,
    private val getStoredCheckByIdUseCase: GetStoredCheckByIdUseCase,
) : ViewModel(),
    StoreGuestUseCase by storeGuestUseCase,
    CalculateCheckUseCase by calculateCheckUseCase,
    UpdateStoredDishUseCase by updateStoredDishUseCase,
    GetStoredCheckByIdUseCase by getStoredCheckByIdUseCase {

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    init {
        updateState()
    }

    fun addNewGuest(guest: Guest) = viewModelScope.launch {
        storeGuestUseCase(guest).collectLatest { success ->
            updateState()
        }
    }

    fun dismissDialog() = viewModelScope.launch {
        when (_mainState.value.openDialog) {
            is DialogType.DishDialog -> _mainState.value =
                _mainState.value.copy(openDialog = DialogType.DishDialog(false))

            is DialogType.GuestDialog -> _mainState.value =
                _mainState.value.copy(openDialog = DialogType.GuestDialog(false))

            is DialogType.CheckDialog -> _mainState.value =
                _mainState.value.copy(openDialog = DialogType.CheckDialog(false, ""))

            is DialogType.EditDishDialog -> _mainState.value =
                _mainState.value.copy(openDialog = DialogType.EditDishDialog(false, ""))
        }
        //todo think of a better way
        updateState()
    }

    fun openDialog(type: DialogType) {
        when (type) {
            is DialogType.DishDialog -> _mainState.value =
                _mainState.value.copy(openDialog = DialogType.DishDialog(true))

            is DialogType.GuestDialog -> _mainState.value =
                _mainState.value.copy(openDialog = DialogType.GuestDialog(true))

            is DialogType.CheckDialog -> _mainState.value =
                _mainState.value.copy(openDialog = type.copy(isOpen = true))

            is DialogType.EditDishDialog -> _mainState.value =
                _mainState.value.copy(openDialog = type.copy(isOpen = true))
        }
    }

    fun addGuestToDish(guestUuid: String, dishUuid: String) = viewModelScope.launch {
        combine(
            getDishByIdUseCase(uuid = dishUuid),
            getGuestByIdUseCase(guestIds = listOf(guestUuid))
        ) { dish, guest ->
            dish to guest.firstOrNull()
        }.collect { (dish, guest) ->
            val guestsList = dish?.guests?.filter { it.isNotBlank() } as MutableList
            val checkIdList = guest?.checkIds?.filter { it.isNotBlank() } as MutableList

            if (guestsList.contains(guestUuid)) {
                return@collect // TODO show alert saying that this guest is already in the list
            }

            guestsList.add(guestUuid)
            val updatedDish = dish.copy(guests = guestsList)

            checkIdList.add(dish.checkId)
            val updatedGuest = guest.copy(checkIds = checkIdList)

            updateStoredDishUseCase(updatedDish)
            updateStoredGuestUseCase(updatedGuest)
            updateStoredCheck(updatedDish)
            updateState()
        }
    }

    fun dishPlusOne(dishId: Int) = viewModelScope.launch {
        val dishToBeUpdated = _mainState.value.dishes.find { it.id == dishId }

        dishToBeUpdated?.let {
            val updatedDish = it.copy(qnt = it.qnt + 1)

            updateStoredDishUseCase(dish = updatedDish)
            updateStoredCheck(updatedDish)
            updateState()
        }
    }

    fun clearDatabase() = viewModelScope.launch {
        clearDatabaseUseCase()
        updateState()
    }

    private suspend fun updateStoredCheck(dish: Dish) {

        getStoredCheckByIdUseCase(listOf(dish.checkId)).collect { check ->
            val updatedCheck = check.firstNotNullOfOrNull {
                it?.copy(
                    total = ((dish.price.cents * dish.qnt) / dish.guests.size.toFloat())
                        .formatMoney()
                        .toMoney(),
                )
            }

            updatedCheck?.let {
                updateStoredCheckUseCase(it)
            }
        }
    }


    private fun updateState() = viewModelScope.launch {
        combine(getAllGuestsUseCase(), getAllDishesUseCase()) { guests, dishes ->
            guests to dishes
        }.collectLatest { (guests, dishes) ->
            _mainState.value = _mainState.value.copy(
                dishes = dishes,
                guests = guests,
            )
        }
    }

    sealed class DialogType(open val isOpen: Boolean) {
        data class DishDialog(override val isOpen: Boolean = false) : DialogType(isOpen = isOpen)
        data class GuestDialog(override val isOpen: Boolean = false) : DialogType(isOpen = isOpen)
        data class CheckDialog(override val isOpen: Boolean = false, val guestId: String) :
            DialogType(isOpen = isOpen)

        data class EditDishDialog(override val isOpen: Boolean = false, val dishUuid: String) :
            DialogType(isOpen = isOpen)
    }
}