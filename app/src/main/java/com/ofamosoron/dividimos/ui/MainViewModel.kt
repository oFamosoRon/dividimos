package com.ofamosoron.dividimos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ofamosoron.dividimos.domain.delegate.DialogDelegate
import com.ofamosoron.dividimos.domain.models.Check
import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.usecase.GetAllDishesUseCase
import com.ofamosoron.dividimos.domain.usecase.GetDishByIdUseCase
import com.ofamosoron.dividimos.domain.usecase.GetGuestByIdUseCase
import com.ofamosoron.dividimos.domain.usecase.GetAllGuestsUseCase
import com.ofamosoron.dividimos.domain.usecase.ClearDatabaseUseCase
import com.ofamosoron.dividimos.domain.usecase.UpdateGuestUseCase
import com.ofamosoron.dividimos.domain.usecase.UpdateStoredDishUseCase
import com.ofamosoron.dividimos.domain.usecase.UpdateStoredCheckUseCase
import com.ofamosoron.dividimos.domain.usecase.GetStoredCheckByIdUseCase
import com.ofamosoron.dividimos.domain.usecase.SharedPreferencesUseCase
import com.ofamosoron.dividimos.ui.composables.home.DialogType
import com.ofamosoron.dividimos.ui.composables.home.HomeScreenEvent
import com.ofamosoron.dividimos.util.SharedPreferencesHelper.SERVICE_FEE
import com.ofamosoron.dividimos.util.dishToGuests
import com.ofamosoron.dividimos.util.formatMoney
import com.ofamosoron.dividimos.util.toMoney
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@SuppressWarnings("LongParameterList")
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDishByIdUseCase: GetDishByIdUseCase,
    private val getAllDishesUseCase: GetAllDishesUseCase,
    private val getGuestByIdUseCase: GetGuestByIdUseCase,
    private val getAllGuestsUseCase: GetAllGuestsUseCase,
    private val clearDatabaseUseCase: ClearDatabaseUseCase,
    private val updateStoredGuestUseCase: UpdateGuestUseCase,
    private val updateStoredDishUseCase: UpdateStoredDishUseCase,
    private val sharedPreferencesUseCase: SharedPreferencesUseCase,
    private val updateStoredCheckUseCase: UpdateStoredCheckUseCase,
    private val getStoredCheckByIdUseCase: GetStoredCheckByIdUseCase,
    private val dialogDelegate: DialogDelegate,
) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    init {
        updateState()
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.CloseDialog -> handleCloseDialogEvent(event.dialogType)
            is HomeScreenEvent.OpenDialog -> handleOpenDialogEvent(event.dialogType)
            is HomeScreenEvent.ClearDatabase -> handleClearDatabaseEvent()
            is HomeScreenEvent.OnDrop -> handleOnDropEvent(
                guestUuid = event.guestUuid,
                dishUuid = event.dishUuid
            )
            is HomeScreenEvent.DecreaseDishQuantity -> handleDishQuantity(
                dishUuid = event.dishUuid,
                increase = false
            )
            is HomeScreenEvent.IncreaseDishQuantity -> handleDishQuantity(
                dishUuid = event.dishUuid,
                increase = true
            )
            is HomeScreenEvent.ClearAlert -> handleClearAlert()
        }
    }

    private fun handleClearAlert() {
        _mainState.value = _mainState.value.copy(showAlert = false)
    }

    private fun handleDishQuantity(dishUuid: String, increase: Boolean) = viewModelScope.launch {
        val dishToBeUpdated = _mainState.value.dishes.find { it.uuid == dishUuid }
        dishToBeUpdated?.let {
            val currentQnt = it.qnt
            val updatedDish = when {
                increase -> {
                    it.copy(qnt = it.qnt + 1)
                }
                !increase && (currentQnt > 0) -> {
                    it.copy(qnt = it.qnt - 1)
                }
                else -> it.copy(qnt = 0)
            }

            updateStoredDishUseCase(dish = updatedDish).flatMapLatest {
                updateStoredCheck(updatedDish)
            }.collectLatest {
                updateState()
            }
        }
    }

    private fun handleCloseDialogEvent(type: DialogType) = viewModelScope.launch {
        val dialogState = dialogDelegate.closeDialog(type)
        _mainState.value = _mainState.value.copy(openDialog = dialogState)

        //todo think of a better way
        updateState()
    }

    private fun handleOpenDialogEvent(type: DialogType) {
        _mainState.value =
            _mainState.value.copy(openDialog = dialogDelegate.openDialog(dialogType = type))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun handleOnDropEvent(guestUuid: String, dishUuid: String) = viewModelScope.launch {
        combine(
            getDishByIdUseCase(uuid = dishUuid),
            getGuestByIdUseCase(guestIds = listOf(guestUuid))
        ) { dish, guest ->
            dish to guest.firstOrNull()
        }.collect { (dish, guest) ->
            val guestsList = dish?.guests?.filter { it.isNotBlank() } as MutableList
            val checkIdList = guest?.checkIds?.filter { it.isNotBlank() } as MutableList

            if (guestsList.contains(guestUuid)) {
                _mainState.value = _mainState.value.copy(showAlert = true)
                return@collect
            }

            guestsList.add(guestUuid)
            val updatedDish = dish.copy(guests = guestsList)

            checkIdList.add(dish.checkId)
            val updatedGuest = guest.copy(checkIds = checkIdList)

            updateStoredDishUseCase(updatedDish)
                .flatMapLatest { updateStoredGuestUseCase(updatedGuest) }
                .flatMapLatest { updateStoredCheck(updatedDish) }
                .collectLatest {
                    updateState()
                }
        }
    }

    private fun handleClearDatabaseEvent() = viewModelScope.launch {
        sharedPreferencesUseCase.clear()
        clearDatabaseUseCase()
        updateState()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun updateStoredCheck(dish: Dish) =
        getStoredCheckByIdUseCase(listOf(dish.checkId)).flatMapLatest { check ->
            val updatedCheck = check.firstNotNullOfOrNull {
                it?.copy(
                    total = ((dish.price.cents * dish.qnt) / dish.guests.size.toFloat())
                        .formatMoney()
                        .toMoney(),
                )
            }
            updateStoredCheckUseCase(updatedCheck ?: Check())
        }

    private fun updateState() = viewModelScope.launch {
        combine(getAllGuestsUseCase(), getAllDishesUseCase()) { guests, dishes ->
            guests to dishes
        }.collectLatest { (guests, dishes) ->
            val serviceFee = sharedPreferencesUseCase.read(SERVICE_FEE, Float::class) ?: 0F
            _mainState.value = _mainState.value.copy(
                dishes = dishes,
                guests = guests,
                dishesToGuests = dishes.map { dishToGuests(it, guests) },
                serviceFee = serviceFee
            )
        }
    }
}

