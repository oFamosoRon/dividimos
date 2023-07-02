package com.ofamosoron.dividimos.ui.composables.dishes_dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ofamosoron.dividimos.domain.models.Check
import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.usecase.StoreCheckUseCase
import com.ofamosoron.dividimos.domain.usecase.StoreDishUseCase
import com.ofamosoron.dividimos.util.toMoney
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DishDialogViewModel @Inject constructor(
    private val storeDishUseCase: StoreDishUseCase,
    private val storeCheckUseCase: StoreCheckUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(DishDialogState())
    val state = _state.asStateFlow()

    fun onEvent(event: FormValidationEvent) {
        when (event) {
            is FormValidationEvent.NameChanged -> {
                if (event.name.isBlank()) {
                    _state.value = _state.value.copy(
                        dishName = event.name,
                        dishNameError = "Campo obrigatório"
                    )
                } else {
                    _state.value = _state.value.copy(dishName = event.name, dishNameError = null)
                }
            }
            is FormValidationEvent.PriceChanged -> {
                if (event.price.isBlank() || (event.price.isNotBlank() && event.price.toFloat() == 0F)) {
                    _state.value = _state.value.copy(
                        dishPrice = event.price,
                        dishPriceError = "Preço inválido"
                    )
                } else {
                    _state.value = _state.value.copy(dishPrice = event.price, dishPriceError = null)
                }
            }
            is FormValidationEvent.QuantityChanged -> {
                _state.value = _state.value.copy(dishQuantity = event.quantity)
            }
            is FormValidationEvent.SubmitButtonClicked -> {
                val hasError = when {
                    event.name.isBlank() -> {
                        _state.value = _state.value.copy(
                            dishPrice = event.price,
                            dishName = event.name,
                            dishNameError = "Campo obrigatório"
                        )
                        true
                    }
                    event.price.isBlank() -> {
                        _state.value = _state.value.copy(
                            dishPrice = event.price,
                            dishName = event.name,
                            dishPriceError = "Preço inválido"
                        )
                        true
                    }
                    (event.price.isNotBlank() && event.price.toFloat() == 0F) -> {
                        _state.value = _state.value.copy(
                            dishPrice = event.price,
                            dishName = event.name,
                            dishPriceError = "Preço inválido"
                        )
                        true
                    }
                    else -> {
                        _state.value = _state.value.copy(
                            dishPrice = event.price,
                            dishPriceError = null
                        )
                        _state.value = _state.value.copy(
                            dishName = event.name,
                            dishNameError = null
                        )
                        false
                    }
                }

                if (!hasError) {
                    addNewDishAndCheck(
                        _state.value.dishName,
                        _state.value.dishPrice,
                        _state.value.dishQuantity
                    )
                    _state.value = _state.value.copy(dismiss = true)
                }
            }
        }
    }

    fun clearState() {
        _state.value = DishDialogState()
    }

    private fun addNewDishAndCheck(name: String, price: String, qnt: Int) = viewModelScope.launch {
        createNewDishAndCheck(name, price, qnt)?.let { (dish, check) ->
            storeDishUseCase(dish = dish)
            storeCheckUseCase(check = check)
        }
    }

    private fun createNewDishAndCheck(name: String, price: String, qnt: Int): Pair<Dish, Check>? {
        if (name.isEmpty() && price.isEmpty())
            return null

        val check = Check(
            total = price.toMoney(),
            uuid = UUID.randomUUID().toString(),
            dishName = name
        )

        val dish = Dish(
            name = name,
            qnt = qnt,
            price = price.toMoney(),
            createdAt = Clock.System.now(),
            uuid = UUID.randomUUID().toString(),
            guests = emptyList(),
            checkId = check.uuid
        )

        return Pair<Dish, Check>(dish, check)
    }
}